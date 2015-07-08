/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.aad.adal.IWindowComponent;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationResultsCallback;
import com.microsoft.office365.meetingfeedback.model.discovery.ServiceDiscoveryCallback;
import com.microsoft.office365.meetingfeedback.model.discovery.ServiceDiscoveryManager;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsService;

import javax.inject.Inject;

public class ConnectActivity extends BaseActivity implements AuthenticationResultsCallback, IWindowComponent {

    private Button mConnectButton;
    private ProgressBar mConnectProgressBar;
    private TextView mDescriptionTextView;
    private String TAG = "ConnectActivity";

    @Inject
    ServiceDiscoveryManager mServiceDiscoveryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mConnectButton = (Button) findViewById(R.id.activity_connect_connect_button);
        mConnectProgressBar = (ProgressBar) findViewById(R.id.activity_connect_progress_bar);
        mDescriptionTextView = (TextView) findViewById(R.id.activity_connect_description_text_view);
        mConnectButton.setVisibility(View.GONE);
        mAuthenticationManager.authenticate(this);
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthenticationManager.authenticate(ConnectActivity.this);
            }
        });
    }

    @Override
    public void onAuthenticationSuccess() {
        Log.d(TAG, "authentication success!");

        mDialogUtil.showProgressDialog(this, "Discovering Services...", "one moment..");
        mServiceDiscoveryManager.discoverServices(new ServiceDiscoveryCallback() {
            @Override
            public void onServiceDiscoverySuccess() {
                finish();
                Intent startingIntent = getIntent();
                String eventId = startingIntent.getStringExtra(MyMeetingsService.EVENT_ID);
                Intent intent = new Intent(ConnectActivity.this, CalendarActivity.class);
//                intent.putExtra(MyMeetingsService.EVENT_ID, eventId);
                startActivity(intent);
            }

            @Override
            public void onServiceDiscoveryFailure(Throwable throwable) {
                mDialogUtil.dismissDialog(ConnectActivity.this);
            }
        });

    }

    @Override
    public void onAuthenticationFailure(Exception e) {
        Log.e(TAG, "authentication failure! Exception: " + e);
        mConnectButton.setVisibility(View.VISIBLE);
        mConnectProgressBar.setVisibility(View.GONE);
        mDescriptionTextView.setText(R.string.connect_text_error);
        mDescriptionTextView.setVisibility(View.VISIBLE);
        Toast.makeText(
                ConnectActivity.this,
                R.string.connect_toast_text_error,
                Toast.LENGTH_LONG).show();

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
