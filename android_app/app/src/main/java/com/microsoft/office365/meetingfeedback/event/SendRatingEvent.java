/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.event;

import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;

public class SendRatingEvent {
    public RatingData mRatingData;

    public SendRatingEvent(RatingData ratingData) {
        mRatingData = ratingData;
    }
}
