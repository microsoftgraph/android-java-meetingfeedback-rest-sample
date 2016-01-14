/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

public interface AuthenticationResultsCallback {

    void onAuthenticationSuccess();

    void onAuthenticationFailure(Exception exception);

}
