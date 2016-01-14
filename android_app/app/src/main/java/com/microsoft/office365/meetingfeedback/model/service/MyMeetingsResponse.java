/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.service;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMeetingsResponse {

    @SerializedName("meeting_data")
    public List<MyMeetingData> mMyMeetingDatas;

    private class MyMeetingData {
        @SerializedName("event_id")
        public String mEventId;

        @SerializedName("num_ratings")
        public Double mNumRatings;
    }

    public Map<String, Double> toMap() {
        Map<String, Double> map = new HashMap<>();
        for (MyMeetingData myMeetingData : mMyMeetingDatas) {
            map.put(myMeetingData.mEventId, myMeetingData.mNumRatings);
        }
        return map;
    }


}
