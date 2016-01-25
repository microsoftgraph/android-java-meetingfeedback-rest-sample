/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
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

public class ConnectActivity extends BaseActivity implements AuthenticationResultsCallback, IWindowComponent {

    private Button mConnectButton;
    private ProgressBar mConnectProgressBar;
    private TextView mDescriptionTextView;
    private String TAG = "ConnectActivity";

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

        finish();
        Intent startingIntent = getIntent();
        Intent intent = new Intent(ConnectActivity.this, CalendarActivity.class);
        startActivity(intent);
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
