/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.meeting;

import java.io.Serializable;
import java.util.List;

public class EventGroup implements Serializable {

    public int mPage;
    public DateRange mDateRange;
    public List<EventDecorator> mGroupedEventDecorators;

    public EventGroup(int page, DateRange dateRange, List<EventDecorator> groupedEventDecorators) {
        mPage = page;
        mDateRange = dateRange;
        mGroupedEventDecorators = groupedEventDecorators;
    }

    @Override
    public String toString() {
        return "EventsGroupedByDateRange{" +
                "mPage=" + mPage +
                ", mDateRange=" + mDateRange +
                ", mGroupedEventDecorators=" + mGroupedEventDecorators +
                '}';
    }
}
