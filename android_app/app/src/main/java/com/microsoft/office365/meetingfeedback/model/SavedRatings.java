/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model;

import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;

import java.util.Map;

public class SavedRatings {

    Map<String, RatingData> mSavedRatings;

    public SavedRatings(Map<String, RatingData> savedRatings) {
        mSavedRatings = savedRatings;
    }

    public Map<String, RatingData> getSavedRatings() {
        return mSavedRatings;
    }

}
