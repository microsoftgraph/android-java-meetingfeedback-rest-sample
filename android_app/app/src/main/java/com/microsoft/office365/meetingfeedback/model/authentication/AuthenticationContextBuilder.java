/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.authentication;

import android.content.Context;

import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.office365.meetingfeedback.model.Constants;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class AuthenticationContextBuilder {

    public static AuthenticationContext newInstance(Context context) {
        try {
            return new AuthenticationContext(context, Constants.AUTHORITY_URL, false);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
