/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.microsoft.office365.meetingfeedback.event.SendRatingFailedEvent;
import com.microsoft.office365.meetingfeedback.inject.ActivityModule;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;
import com.microsoft.office365.meetingfeedback.model.officeclient.ClientManager;
import com.microsoft.office365.meetingfeedback.util.ConnectivityUtil;
import com.microsoft.office365.meetingfeedback.util.DialogUtil;
import com.microsoft.office365.meetingfeedback.view.RateMyMeetingsDialogFragment;

import javax.inject.Inject;

import dagger.ObjectGraph;
import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends ActionBarActivity {

    public static final String RATING_DIALOG_FRAGMENT_TAG = "RatingDialogFragmentTag";

    private ObjectGraph mActivityGraph;

    @Inject
    AuthenticationManager mAuthenticationManager;
    @Inject
    ClientManager mClientManager;
    @Inject
    public DataStore mDataStore;
    @Inject
    DialogUtil mDialogUtil;
    @Inject
    ConnectivityUtil mConnectivityUtil;

    //common dialog fragment for messaging
    private RateMyMeetingsDialogFragment mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeetingFeedbackApplication application = (MeetingFeedbackApplication) getApplication();
        mActivityGraph = application.getApplicationGraph().plus(new ActivityModule(this));
        mActivityGraph.inject(this);
        //ensure a network connection exists
        validateNetworkConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEvent(SendRatingFailedEvent event) {
        Log.e("Send Rating failed!", event.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void validateNetworkConnection() {
        if (!mConnectivityUtil.hasNetworkConnection()) {
            finish();
            startActivity(new Intent(this, NoNetworkActivity.class));
        }
    }

    public void setDialog(RateMyMeetingsDialogFragment progressDialog) {
        mProgressDialog = progressDialog;
    }

    public RateMyMeetingsDialogFragment getDialog() {
        return mProgressDialog;
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
