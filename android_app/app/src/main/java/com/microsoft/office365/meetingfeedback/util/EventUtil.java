/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import com.microsoft.outlookservices.Event;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EventUtil {
    public static String getEventOwner(Event event) {
        return event.getOrganizer().getEmailAddress().getAddress();
    }
}
