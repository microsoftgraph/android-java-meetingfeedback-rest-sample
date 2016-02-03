/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.webservice;

import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsResponse;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.CreateRatingRequest;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RatingServiceInterface {

    @GET("/meetings")
    void getMeetingsAsync(@Query("username") String username, Callback<List<MeetingServiceResponseData>> callback);

    @POST("/meetings")
    void postRatingAsync(@Query("username") String username, @Query("owner") String owner, @Body CreateRatingRequest requestData, Callback<MeetingServiceResponseData> callback);

    @GET("/meetings/{id}")
    void getMeetingAsync(@Path("id") String eventId, Callback<MeetingServiceResponseData> callback);

    @GET("/mymeetings")
    void getMyMeetingsAsync(@Query("username") String username, Callback<MyMeetingsResponse> callback);

}
