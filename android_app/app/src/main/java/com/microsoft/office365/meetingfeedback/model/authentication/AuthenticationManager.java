/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.AuthenticationResult.AuthenticationStatus;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.User;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;

public class AuthenticationManager {

    private static final String TAG = "AuthenticationManager";
    private DataStore mDataStore;
    private AuthenticationContext mAuthenticationContext;
    private RatingServiceAlarmManager mAlarmManager;

    public AuthenticationManager(DataStore dataStore, AuthenticationContext authenticationContext,
                                 RatingServiceAlarmManager alarmManager) {
        mDataStore = dataStore;
        mAuthenticationContext = authenticationContext;
        mAlarmManager = alarmManager;
    }

    /**
     * Description: Calls AuthenticationContext.acquireToken(...) once to authenticate with
     * user's credentials and avoid interactive prompt on later calls.
     */
    public void authenticate(final AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        // Since we're doing considerable work, let's get out of the main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mDataStore.isUserLoggedIn()) {
                    authenticateSilent(authenticationCallback);
                } else {
                    authenticatePrompt(authenticationCallback);
                }
            }
        }).start();
    }

    /**
     * Calls acquireTokenSilent with the user id stored in shared preferences.
     * In case of an error, it falls back to {@link AuthenticationManager#authenticatePrompt(AuthenticationCallback)}.
     * @param authenticationCallback The callback to notify when the processing is finished.
     */
    private void authenticateSilent(final AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        mAuthenticationContext.acquireTokenSilent(
                Constants.OUTLOOK_RESOURCE_ID,
                Constants.CLIENT_ID,
                mDataStore.getUserId(),
                new AuthenticationCallback<AuthenticationResult>() {
                    @Override
                    public void onSuccess(final AuthenticationResult authenticationResult) {
                        if (authenticationResult != null && authenticationResult.getStatus() == AuthenticationStatus.Succeeded) {
                            authenticationCallback.onSuccess(authenticationResult);
                        } else if (authenticationResult != null) {
                            // I could not authenticate the user silently,
                            // falling back to prompt the user for credentials.
                            authenticatePrompt(authenticationCallback);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        // I could not authenticate the user silently,
                        // falling back to prompt the user for credentials.
                        authenticatePrompt(authenticationCallback);
                    }
                }
        );
    }

    /**
     * Calls acquireToken to prompt the user for credentials.
     * @param authenticationCallback The callback to notify when the processing is finished.
     */
    private void authenticatePrompt(final AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        mAuthenticationContext.acquireToken(
                Constants.OUTLOOK_RESOURCE_ID,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                null,
                PromptBehavior.Always,
                null,
                new AuthenticationCallback<AuthenticationResult>() {
                    @Override
                    public void onSuccess(final AuthenticationResult authenticationResult) {
                        if (authenticationResult != null && authenticationResult.getStatus() == AuthenticationStatus.Succeeded) {
                            User user = new User(authenticationResult.getUserInfo());
                            mDataStore.setUser(user);
                            authenticationCallback.onSuccess(authenticationResult);
                        } else if (authenticationResult != null) {
                            // We need to make sure that there is no data stored with the failed auth
                            signout();
                            // This condition can happen if user signs in with an MSA account
                            // instead of an Office 365 account
                            authenticationCallback.onError(
                                    new AuthenticationException(
                                            ADALError.AUTH_FAILED,
                                            authenticationResult.getErrorDescription()
                                    )
                            );
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        // We need to make sure that there is no data stored with the failed auth
                        signout();
                        authenticationCallback.onError(e);
                    }
                }
        );
    }

    public void signout() {
        // Clear tokens.
        if(mAuthenticationContext.getCache() != null) {
            mAuthenticationContext.getCache().removeAll();
        }
        mDataStore.logout();
        mAlarmManager.cancelRatingService();
    }

}
