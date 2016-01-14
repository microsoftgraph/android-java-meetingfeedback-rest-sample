/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefsUtil {

    private static final String SERIALIZED_MEETINGS = "SERIALIZED_MEETINGS";
    private static final String SERIALIZED_USERNAME = "SERIALIZED_USERNAME";
    private final SharedPreferences mSharedPrefs;
    private Context mContext;

    public SharedPrefsUtil(Context context) {
        mContext = context;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    public String getSavedUsername() {
        return mSharedPrefs.getString(SERIALIZED_USERNAME, "");
    }

    public void setSavedUsername(String username) {
        mSharedPrefs.edit().putString(SERIALIZED_USERNAME, username).apply();
    }

    public Map<String, Double> getSavedMeetingResults() {
        String string = mSharedPrefs.getString(SERIALIZED_MEETINGS, "");
        Map<String, Double> map = new HashMap<>();
        return new Gson().fromJson(string, map.getClass());
    }

    public void setSavedMeetingResults(Map<String, Double> meetingResults) {
        String meetingResultsAsString = new Gson().toJson(meetingResults);
        mSharedPrefs.edit().putString(SERIALIZED_MEETINGS, meetingResultsAsString).apply();
    }

}
