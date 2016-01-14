/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.webservice;

import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsResponse;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.CreateRatingRequest;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Query;

public class RatingService implements RatingServiceInterface {

    public static final String APPLICATION_JSON = "application/json";

    private final RatingServiceInterface mRatingServiceInterface;

    private RatingServiceInterface buildService() {

        RestAdapter build = new RestAdapter.Builder()
                .setEndpoint(Constants.SERVICE_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", APPLICATION_JSON);
                        request.addHeader("Accept", APPLICATION_JSON);
                    }
                })
                .build();
        return build.create(RatingServiceInterface.class);
    }

    public RatingService() {
        mRatingServiceInterface = buildService();
    }

    @Override
    public void postRatingAsync(String username, String owner, CreateRatingRequest requestData, Callback<MeetingServiceResponseData> callback) {
        mRatingServiceInterface.postRatingAsync(username, owner, requestData, callback);
    }

    @Override
    public void getMeetingsAsync(String username, Callback<List<MeetingServiceResponseData>> callback) {
        mRatingServiceInterface.getMeetingsAsync(username, callback);
    }

    @Override
    public void getMeetingAsync(String eventId, String username, String owner, Callback<MeetingServiceResponseData> callback) {
        mRatingServiceInterface.getMeetingAsync(eventId, username, owner, callback);
    }

    @Override
    public void getMyMeetingsAsync(@Query("username") String username, Callback<MyMeetingsResponse> callback) {
        mRatingServiceInterface.getMyMeetingsAsync(username, callback);
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
