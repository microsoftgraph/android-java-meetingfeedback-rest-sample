/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model;

import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;
import com.microsoft.office365.meetingfeedback.util.CalendarUtil;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.office365.meetingfeedback.util.SharedPrefsUtil;
import com.microsoft.outlookservices.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {

    List<Event> mEvents = new ArrayList<>();
    Map<String, Event> mEventsMap = new HashMap<>();
    Map<String, MeetingServiceResponseData> mMeetingServiceData = new HashMap<>();

    private User mUser;
    private Map<String, Double> mMyMeetings;
    private SharedPrefsUtil mSharedPrefsUtil;
    private EventFilter mFilter;

    public DataStore(SharedPrefsUtil sharedPrefsUtil) {
        mSharedPrefsUtil = sharedPrefsUtil;
    }

    public List<EventGroup> getEventGroups() {
        return CalendarUtil.eventsAsEventDecorators(this);
    }

    public List<Event> getEvents() {
        if (mEvents == null) {
            return new ArrayList<>();
        }
        return mEvents;
    }

    public void setMeetingServiceResponseData(List<MeetingServiceResponseData> meetingServiceResponseDatas) {
        for (MeetingServiceResponseData serviceResponseData : meetingServiceResponseDatas) {
            mMeetingServiceData.put(serviceResponseData.mEventId, serviceResponseData);
        }
    }

    public void updateMeetingServiceResponse(MeetingServiceResponseData meetingServiceResponseData) {
        mMeetingServiceData.put(meetingServiceResponseData.mEventId, meetingServiceResponseData);
    }

    public MeetingServiceResponseData getWebServiceRatingDataForEvent(String eventId) {
        return mMeetingServiceData.get(eventId);
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
        for (Event event : events) {
            mEventsMap.put(event.getiCalUId(), event);
        }
    }

    public Event getEventById(String id) {
        return mEventsMap.get(id);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        String userName = null;
        if (mUser != null) {
            userName = mUser.getUsername();
        }
        mSharedPrefsUtil.setSavedUsername(userName);
    }

    public boolean isUserLoggedIn() {
        return mUser != null;
    }

    public String getUsername() {
        return mUser.getUsername();
    }

    public void setMyMeetings(Map<String, Double> myMeetings) {
        mMyMeetings = myMeetings;
        mSharedPrefsUtil.setSavedMeetingResults(mMyMeetings);
    }

    public void logout() {
        mUser = null;
        mEvents = null;
        mEvents = null;
        mMyMeetings = null;
    }

    public List<Event> getFilteredEvents() {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : getEvents()) {
            boolean condition = event.getIsOrganizer();
            condition = mFilter.equals(EventFilter.MY_MEETINGS) ? condition : !condition;
            if (condition) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    public void setEventFilter(EventFilter eventFilter) {
        mFilter = eventFilter;
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
