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
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Event;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsResponse;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.CreateRatingRequest;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;

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
        loadRatingFromWebservice(event.mICalUId, event.mOrganizer.emailAddress.mName);
    }
}
