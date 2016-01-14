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
