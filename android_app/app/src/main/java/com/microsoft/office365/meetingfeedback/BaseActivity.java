/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.microsoft.office365.meetingfeedback.event.SendRatingFailedEvent;
import com.microsoft.office365.meetingfeedback.inject.ActivityModule;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;
import com.microsoft.office365.meetingfeedback.model.outlook.CalendarService;
import com.microsoft.office365.meetingfeedback.model.outlook.EmailService;
import com.microsoft.office365.meetingfeedback.util.ConnectivityUtil;
import com.microsoft.office365.meetingfeedback.util.DialogUtil;
import com.microsoft.office365.meetingfeedback.view.RateMyMeetingsDialogFragment;

import javax.inject.Inject;

import dagger.ObjectGraph;
import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String RATING_DIALOG_FRAGMENT_TAG = "RatingDialogFragmentTag";

    private ObjectGraph mActivityGraph;

    @Inject
    AuthenticationManager mAuthenticationManager;
    @Inject
    CalendarService mCalendarService;
    @Inject
    EmailService mEmailService;
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
