/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

import android.webkit.CookieManager;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.AuthenticationResult.AuthenticationStatus;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.User;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.services.odata.impl.ADALDependencyResolver;

public class AuthenticationManager {

    private static final String TAG = "AuthenticationManager";
    private ADALDependencyResolver mResolver;
    private DataStore mDataStore;
    private AuthenticationContext mAuthenticationContext;
    private RatingServiceAlarmManager mAlarmManager;

    public AuthenticationManager(ADALDependencyResolver resolver, DataStore dataStore, AuthenticationContext authenticationContext,
                                 RatingServiceAlarmManager alarmManager) {
        mResolver = resolver;
        mDataStore = dataStore;
        mAuthenticationContext = authenticationContext;
        mAlarmManager = alarmManager;
    }

    /**
     * Description: Calls AuthenticationContext.acquireToken(...) once to authenticate with
     * user's credentials and avoid interactive prompt on later calls.
     */
    public void authenticate(final AuthenticationResultsCallback resultsCallback) {
        AuthenticationCallback<AuthenticationResult> authenticationCallback = new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(final AuthenticationResult authenticationResult) {
                if (authenticationResult != null && authenticationResult.getStatus() == AuthenticationStatus.Succeeded) {
                    //cache the user
                    User user = new User(authenticationResult.getUserInfo());
                    mDataStore.setUser(user);
                    resultsCallback.onAuthenticationSuccess();
                }
            }

            @Override
            public void onError(Exception exception) {
                resultsCallback.onAuthenticationFailure(exception);
            }
        };

        mAuthenticationContext.acquireToken(
                Constants.DISCOVERY_RESOURCE_ID,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                null,
                PromptBehavior.Auto,
                null,
                authenticationCallback
        );
    }

    public void signout() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        mAuthenticationContext.getCache().removeAll();
        mDataStore.logout();
        mResolver.setResourceId(Constants.DISCOVERY_RESOURCE_ID);
        mAlarmManager.cancelRatingService();
    }

}

// *********************************************************
//
// O365-Android-MeetingFeedback, https://github.com/OfficeDev/O365-Android-MeetingFeedback
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************
