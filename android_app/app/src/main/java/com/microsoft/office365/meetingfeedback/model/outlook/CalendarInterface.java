/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook;

import com.microsoft.office365.meetingfeedback.model.outlook.payload.Envelope;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface CalendarInterface {
    @GET("/me/calendarview")
    void getEvents(
            @Header("Content-type") String contentTypeHeader,
            @Header("Prefer") String preferHeader,
            @Query("startdatetime") String startDateTime,
            @Query("enddatetime") String endDateTime,
            @Query("$select") String select,
            @Query("$orderby") String orderBy,
            @Query("$top") String top,
            Callback<Envelope> callback
    );
}
