/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook.payload;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("iCalUId")
    public String mICalUId;

    @SerializedName("subject")
    public String mSubject;

    @SerializedName("start")
    public Date mStart;

    @SerializedName("end")
    public Date mEnd;

    @SerializedName("organizer")
    public Organizer mOrganizer;

    @SerializedName("isOrganizer")
    public boolean mIsOrganizer;

    @SerializedName("attendees")
    public Attendee[] mAttendees;

    @SerializedName("bodyPreview")
    public String mBodyPreview;

}
