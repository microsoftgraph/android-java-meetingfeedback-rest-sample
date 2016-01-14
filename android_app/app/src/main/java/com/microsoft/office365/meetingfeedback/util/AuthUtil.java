/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import android.os.Build;
import android.util.Log;

import com.microsoft.aad.adal.AuthenticationSettings;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AuthUtil {

    private static final String TAG = "AuthUtil";

    public static void setupEncryptionKey() {
        // Devices with API level lower than 18 must setSecretKey an encryption key.
        if (Build.VERSION.SDK_INT >= 18) {
            return;
        }
        try {
            setSecretKey();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException e) {
            Log.e(TAG, "setup encryption key failed!");
        }
        AuthenticationSettings.INSTANCE.setSkipBroker(true);
    }

    private static void setSecretKey() throws NoSuchAlgorithmException,
            InvalidKeySpecException, UnsupportedEncodingException {
        if (AuthenticationSettings.INSTANCE.getSecretKeyData() == null) {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA256And256BitAES-CBC-BC");
            PBEKeySpec keySpec = new PBEKeySpec("O365_password".toCharArray(), "O365_salt".getBytes("UTF-8"), 100, 256);
            SecretKey tempKey = keyFactory.generateSecret(keySpec);
            SecretKey secretKey = new SecretKeySpec(tempKey.getEncoded(), "AES");
            AuthenticationSettings.INSTANCE.setSecretKey(secretKey.getEncoded());
        }
    }

}
