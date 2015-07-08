/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model.webservice.payload;

import com.google.gson.annotations.SerializedName;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;

import java.io.Serializable;
import java.util.List;

public class MeetingServiceResponseData implements Serializable {

    @SerializedName("event_id")
    public String mEventId;

    @SerializedName("owner")
    public String mOwner;

    @SerializedName("avg_rating")
    public Float mAverageRating;

    @SerializedName("has_already_rated")
    public boolean mHasAlreadyRated;

    @SerializedName("your_rating")
    public float mYourRating;

    @SerializedName("is_meeting_owner")
    public boolean mIsMeetingOwner;

    @SerializedName("ratings")
    public List<RatingData> mRatings;

    public boolean hasRatings() {
        return mRatings != null && mRatings.size() > 0;
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
