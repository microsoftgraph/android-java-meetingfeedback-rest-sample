/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

import com.microsoft.aad.adal.AuthenticationResult;

public class AuthenticationResultManager {

    private static AuthenticationResultManager INSTANCE;

    private AuthenticationResult authenticationResult;

    public static synchronized AuthenticationResultManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationResultManager();
        }
        return INSTANCE;
    }

    public AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }

    public void setAuthenticationResult(AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }
}
