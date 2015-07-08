/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class RatingServiceAlarmManager {

    public static final int TEN_SECONDS_IN_MILLIS = 10000;
    private final AlarmManager mAlarmManager;
    private Context mContext;

    public RatingServiceAlarmManager(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void scheduleRatingService() {
        Intent meetingsServiceIntent = new Intent(mContext, MyMeetingsService.class);
        final PendingIntent pIntent = PendingIntent.getService(mContext, MyMeetingsService.MEETING_REQUEST_CODE, meetingsServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        int intervalMillis = TEN_SECONDS_IN_MILLIS;
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }

    public void cancelRatingService() {
        Intent intent = new Intent(mContext, MyMeetingsService.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(mContext, MyMeetingsService.MEETING_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(pIntent);
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
