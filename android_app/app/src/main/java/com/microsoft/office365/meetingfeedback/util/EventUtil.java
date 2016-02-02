/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import com.microsoft.office365.meetingfeedback.model.outlook.payload.Event;

public class EventUtil {
    public static String getEventOwner(Event event) {
        return event.mOrganizer.emailAddress.mName;
    }
}
