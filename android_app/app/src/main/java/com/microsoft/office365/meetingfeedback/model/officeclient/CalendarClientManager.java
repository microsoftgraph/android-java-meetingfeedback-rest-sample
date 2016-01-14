/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.officeclient;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.office365.meetingfeedback.event.LoadCalendarFailedEvent;
import com.microsoft.office365.meetingfeedback.event.LoadCalendarSuccessEvent;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.DateRange;
import com.microsoft.outlookservices.Event;
import com.microsoft.outlookservices.odata.OutlookClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CalendarClientManager {

    private static final String TAG = "OutlookClientManager";
    public static final String PARAMETER_START_DATE_TIME = "startdatetime";
    public static final String PARAMETER_END_DATE_TIME = "enddatetime";
    public static final String PARAM_START = "Start desc";
    public static final String SELECT_CLAUSE = "Subject,Start,End,Organizer,Attendees,BodyPreview,isOrganizer,iCalUID";
    public static final int MAX_NUMBER_OF_EVENTS = 200;
    private DataStore mDataStore;
    private OutlookClient mOutlookClient;
    private List<Event> mAccumulatedEvents = new ArrayList<>();

    public CalendarClientManager(DataStore dataStore, OutlookClient outlookClient) {
        mDataStore = dataStore;
        mOutlookClient = outlookClient;
    }

    public void fetchEvents() {
        Log.d(TAG, "fetchEventsRecursive");

        mAccumulatedEvents = new ArrayList<>();

        new AsyncTask<Void, Void, List<Event>>() {
            @Override
            protected List<Event> doInBackground(Void... params) {
                try {
                    DateRange dateRange = getDateRange();
                    return mOutlookClient.getMe()
                            .getCalendarView()
                            .select(SELECT_CLAUSE)
                            .addParameter(PARAMETER_START_DATE_TIME, dateRange.mStart)
                            .addParameter(PARAMETER_END_DATE_TIME, dateRange.mEnd)
                            .orderBy(PARAM_START)
                            .top(MAX_NUMBER_OF_EVENTS)
                            .read()
                            .get();
                } catch (Exception e) {
                    cancel(true);
                    return null;
                }
            }

            @Override
            protected void onCancelled() {
                EventBus.getDefault().post(new LoadCalendarFailedEvent());
            }

            @Override
            protected void onPostExecute(List<Event> events) {
                mAccumulatedEvents.addAll(events);
                mDataStore.setEvents(mAccumulatedEvents);
                EventBus.getDefault().post(new LoadCalendarSuccessEvent());
            }
        }.execute();
    }

    private DateRange getDateRange() {
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, -28);
        return new DateRange(start, end);
    }

}
