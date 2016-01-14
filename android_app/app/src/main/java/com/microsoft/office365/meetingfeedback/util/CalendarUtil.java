/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.DateRange;
import com.microsoft.office365.meetingfeedback.model.meeting.EventDecorator;
import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.outlookservices.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarUtil {

    public static final int NUM_PAGES_IN_CALENDAR = 4;
    public static final int DAYS_PER_PAGE = 7;

    private static DateRange buildDateRangeForPage(int page) {
        int endOffset = page * DAYS_PER_PAGE;
        int startOffset = (page + 1) * DAYS_PER_PAGE;
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH, -endOffset);
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, -startOffset);
        return new DateRange(start, end);
    }

    public static List<EventGroup> eventsAsEventDecorators(DataStore dataStore) {
        List<EventGroup> eventsGroupedByDateRanges = new ArrayList<>();
        List<Event> events = dataStore.getFilteredEvents();
        for (int i = 0; i < NUM_PAGES_IN_CALENDAR; i++) {
            DateRange dateRange = buildDateRangeForPage(i);
            List<EventDecorator> eventDecorators = new ArrayList<>();
            for (Event event : events) {
                if (dateRange.isWithinRange(event)) {
                    eventDecorators.add(new EventDecorator(event, dataStore.getWebServiceRatingDataForEvent(event.getiCalUId())));
                }
            }
            eventsGroupedByDateRanges.add(new EventGroup(i, dateRange, eventDecorators));
        }
        return eventsGroupedByDateRanges;
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
