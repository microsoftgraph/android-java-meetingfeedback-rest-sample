/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
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
