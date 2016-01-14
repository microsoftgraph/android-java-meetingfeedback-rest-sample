/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.meeting;

import com.microsoft.outlookservices.Event;

import java.util.List;

public interface FetchEventsCallback {

    void onEventsFetchSuccess(List<Event> events);

    void onEventsFetchFailed();

}
