/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

public class ShowRatingDialogEvent {
    public String mEventId;

    public ShowRatingDialogEvent(String eventId) {
        mEventId = eventId;
    }
}
