/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.AuthenticationResult.AuthenticationStatus;
import com.microsoft.aad.adal.AuthenticationSettings;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.User;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;

import java.io.UnsupportedEncodingException;

public class AuthenticationManager {

    private static final String TAG = "AuthenticationManager";
    private DataStore mDataStore;
    private AuthenticationContext mAuthenticationContext;
    private RatingServiceAlarmManager mAlarmManager;

    static{
        // Devices with API level lower than 18 must setup an encryption key.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 &&
                AuthenticationSettings.INSTANCE.getSecretKeyData() == null) {
            AuthenticationSettings.INSTANCE.setSecretKey(generateSecretKey());
        }

        // We're not using Microsoft Intune Company portal app,
        // skip the broker check so we don't get warnings about the following permissions
        // in manifest:
        // GET_ACCOUNTS
        // USE_CREDENTIALS
        // MANAGE_ACCOUNTS
        AuthenticationSettings.INSTANCE.setSkipBroker(true);
    }

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

    /**
     * Generates an encryption key for devices with API level lower than 18 using the
     * ANDROID_ID value as a seed.
     * In production scenarios, you should come up with your own implementation of this method.
     * Consider that your algorithm must return the same key so it can encrypt/decrypt values
     * successfully.
     * @return The encryption key in a 32 byte long array.
     */
    private static byte[] generateSecretKey() {
        byte[] key = new byte[32];
        byte[] android_id;

        try{
            android_id = Settings.Secure.ANDROID_ID.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){
            Log.e(TAG, "generateSecretKey - " + e.getMessage());
            throw new RuntimeException(e);
        }

        for(int i = 0; i < key.length; i++){
            key[i] = android_id[i % android_id.length];
        }

        return key;
    }
}
