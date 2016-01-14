/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.webservice.payload;

import com.google.gson.annotations.SerializedName;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;

public class CreateRatingRequest {

    @SerializedName("event_id")
    public final String mEventId;

    @SerializedName("owner")
    public final String mEventOwner;

    @SerializedName("rating")
    public final RatingData mRatingData;

    public CreateRatingRequest(String eventId, String eventOwner, RatingData ratingData) {
        mEventId = eventId;
        mEventOwner = eventOwner;
        mRatingData = ratingData;
    }

}
