/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.webservice;

import android.util.Log;

import com.microsoft.office365.meetingfeedback.event.UserRatingAddedFailureEvent;
import com.microsoft.office365.meetingfeedback.event.UserRatingAddedSuccessEvent;
import com.microsoft.office365.meetingfeedback.event.UserRatingsLoadedFailEvent;
import com.microsoft.office365.meetingfeedback.event.UserRatingsLoadedSuccessEvent;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsResponse;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.CreateRatingRequest;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.outlookservices.Event;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RatingServiceManager {

    private static final String TAG = "RatingServiceManager";


    private final RatingService mRatingService;
    private DataStore mDataStore;

    public RatingServiceManager(RatingService ratingService, DataStore dataStore) {
        mRatingService = ratingService;
        mDataStore = dataStore;
    }

    public RatingService getRatingService() {
        return mRatingService;
    }

    public void loadRatingsFromWebservice() {
        mRatingService.getMeetingsAsync(mDataStore.getUsername(), new Callback<List<MeetingServiceResponseData>>() {
            @Override
            public void success(List<MeetingServiceResponseData> meetingServiceResponseDatas, Response response) {
                mDataStore.setMeetingServiceResponseData(meetingServiceResponseDatas);
                EventBus.getDefault().post(new UserRatingsLoadedSuccessEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "something horrible happened!", error);
                EventBus.getDefault().post(new UserRatingsLoadedFailEvent());
            }
        });
    }

    public void loadRatingFromWebservice(String eventId, String owner) {
        mRatingService.getMeetingAsync(eventId, mDataStore.getUsername(), owner, new Callback<MeetingServiceResponseData>() {
            @Override
            public void success(MeetingServiceResponseData meetingServiceResponseData, Response response) {
                mDataStore.updateMeetingServiceResponse(meetingServiceResponseData);
                EventBus.getDefault().post(new UserRatingsLoadedSuccessEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "something horrible happened!", error);
                EventBus.getDefault().post(new UserRatingsLoadedFailEvent());
            }
        });
    }

    public void loadMyMeetings(String username, Callback<MyMeetingsResponse> callback) {
        mRatingService.getMyMeetingsAsync(username, callback);
    }

    public void loadMyMeetingsAndSchedulePolling(final RatingServiceAlarmManager alarmManager) {
        mRatingService.getMyMeetingsAsync(mDataStore.getUsername(), new Callback<MyMeetingsResponse>() {
            @Override
            public void success(MyMeetingsResponse myMeetingsResponse, Response response) {
                Log.d(TAG, "fetched myMeetings. got: " + myMeetingsResponse);
                mDataStore.setMyMeetings(myMeetingsResponse.toMap());
                alarmManager.scheduleRatingService();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "horrible failure! error:" + error, error);
            }
        });
    }

    public void addRating(String eventOwner, RatingData ratingData) {
        CreateRatingRequest createRatingRequest = new CreateRatingRequest(ratingData.mEventId, eventOwner, ratingData);
        mRatingService.postRatingAsync(mDataStore.getUsername(), eventOwner, createRatingRequest, new Callback<MeetingServiceResponseData>() {
            @Override
            public void success(MeetingServiceResponseData meetingServiceResponseData, Response response) {
                mDataStore.updateMeetingServiceResponse(meetingServiceResponseData);
                EventBus.getDefault().post(new UserRatingAddedSuccessEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "something awful happened!!!!", error);
                EventBus.getDefault().post(new UserRatingAddedFailureEvent());
            }
        });

    }

    public void loadRatingFromWebservice(Event event) {
        loadRatingFromWebservice(event.getiCalUId(), EventUtil.getEventOwner(event));
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
