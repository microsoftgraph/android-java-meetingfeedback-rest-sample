/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook;

import com.microsoft.office365.meetingfeedback.model.outlook.payload.EventWrapper;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface CalendarInterface {
    @GET("/me/calendarview?&$select=subject,start,end,organizer,isOrganizer,attendees,bodyPreview,iCalUID&$orderby=start%2Fdatetime%20desc&$top=150")
    void getEvents(
            @Header("Content-type") String contentTypeHeader,
            @Header("Prefer") String preferHeader,
            @Query("startdatetime") String startDateTime,
            @Query("enddatetime") String endDateTime,
            Callback<EventWrapper> callback);
}