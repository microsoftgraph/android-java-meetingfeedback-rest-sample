/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
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
