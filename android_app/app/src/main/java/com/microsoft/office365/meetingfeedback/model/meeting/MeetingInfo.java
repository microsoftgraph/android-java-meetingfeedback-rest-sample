/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.meeting;

import com.microsoft.services.outlook.Event;

import java.io.Serializable;
import java.util.Calendar;

public class MeetingInfo implements Serializable {

    private final String mId;
    private final String mContent;
    private final String mSubject;
    private final Calendar mStart;
    private final Calendar mEnd;

    public MeetingInfo(Event event) {
        mId = event.getICalUId();
        mSubject = event.getSubject();
        mStart = event.getStart();
        mEnd = event.getEnd();
        //description?
        mContent = event.getBodyPreview();
    }
}
