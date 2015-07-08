/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model.meeting;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;
import com.microsoft.office365.meetingfeedback.view.EventsRecyclerViewAdapter;
import com.microsoft.outlookservices.Event;

import java.io.Serializable;
import java.util.Locale;

public class EventDecorator implements Serializable {

    public final String mEventId;
    public final String mOrganizerName;
    public final String mSubject;
    public final String mFormattedDate;
    public final String mFormattedTime;
    public final MeetingServiceResponseData mServiceData;
    public final String mDescription;
    public final String mOrganizerAddress;
    public final boolean mIsOrganizer;

    public EventDecorator(Event event, MeetingServiceResponseData serviceData) {
        mEventId = EventUtil.hashCode(event);
        mOrganizerName = event.getOrganizer().getEmailAddress().getName();
        mOrganizerAddress = event.getOrganizer().getEmailAddress().getAddress();
        mSubject = event.getSubject();
        mDescription = event.getBodyPreview();
        mFormattedDate = FormatUtil.displayFormattedEventDate(event);
        mFormattedTime = FormatUtil.displayFormattedEventTime(event);
        mServiceData = serviceData;
        mIsOrganizer = event.getIsOrganizer();
    }

    public String formattedDateAndTime() {
        return String.format(Locale.US, "%s, %s", mFormattedDate, mFormattedTime);
    }

    public Spanned descriptionAsHtml() {
        return Html.fromHtml(mDescription.replaceAll("(?s)<!--.*?-->", ""));
    }

    public boolean hasAlreadyRated() {
        return mServiceData != null && mServiceData.mHasAlreadyRated;
    }

    public float yourRating() {
        if (mServiceData == null) {
            return 0.0f;
        }
        return mServiceData.mYourRating;
    }

    public boolean hasRatings() {
        return mServiceData != null && (mServiceData.mRatings != null && mServiceData.mRatings.size() > 0);
    }

    public boolean isOwner(String eventOwner) {
        return mIsOrganizer;
    }

    public int getRatingCount() {
        if (mServiceData == null || mServiceData.mRatings == null) {
            return 0;
        }
        return mServiceData.mRatings.size();
    }

    public Float getAvgRating() {
        if (mServiceData != null) {
            return mServiceData.mAverageRating;
        }
        return 0.0f;
    }

    public void setupEventDisplay(Context context, String currentUserName, EventsRecyclerViewAdapter.EventsViewHolder viewHolder) {
        viewHolder.mRatingsCount.setVisibility(View.GONE);
        if (hasRatings()) {
            viewHolder.mRatingsCount.setVisibility(View.VISIBLE);
            String rating = context.getResources().getQuantityString(R.plurals.rating, getRatingCount());
            viewHolder.mRatingsCount.setText(String.format("of %d %s", getRatingCount(), rating));
            viewHolder.mEventRatingBar.setVisibility(View.VISIBLE);
            viewHolder.mEventRatingBar.setRating(getAvgRating());
            viewHolder.mRatedLabel.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mEventRatingBar.setVisibility(View.GONE);
            viewHolder.mRatedLabel.setVisibility(View.GONE);
        }
        if (hasAlreadyRated()) {
            viewHolder.mEventRatingButton.setVisibility(View.GONE);
        } else {
            viewHolder.mEventRatingButton.setVisibility(View.VISIBLE);
        }
        if (isOwner(currentUserName)) {
            viewHolder.mEventRatingButton.setVisibility(View.GONE);
        }
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
