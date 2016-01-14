/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.meeting;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatingData implements Serializable {

    @SerializedName("event_id")
    public String mEventId;
    @SerializedName("value")
    public float mRating;
    @SerializedName("comment")
    public String mReview;
    @SerializedName("username")
    public String mUsername;

    public RatingData(String eventId, float rating, String review, String username) {
        mEventId = eventId;
        mRating = rating;
        mReview = review;
        mUsername = username;
    }

    public String getCommentString() {
        return TextUtils.isEmpty(mReview) ? "No Comment" : mReview;
    }

    @Override
    public String toString() {
        return "RatingData{" +
                "mEventId='" + mEventId + '\'' +
                ", mRating=" + mRating +
                ", mReview='" + mReview + '\'' +
                ", mUsername='" + mUsername + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingData that = (RatingData) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (!mEventId.equals(that.mEventId)) return false;
        if (!mReview.equals(that.mReview)) return false;
        return mUsername.equals(that.mUsername);

    }
}
