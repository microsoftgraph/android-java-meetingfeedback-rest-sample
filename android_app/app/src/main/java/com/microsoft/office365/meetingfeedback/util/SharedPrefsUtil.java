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
