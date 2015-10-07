/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.microsoft.office365.meetingfeedback.BaseDialogFragment;
import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.event.SendRatingEvent;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.outlookservices.Event;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class RatingDialogFragment extends BaseDialogFragment {

    @Inject
    DataStore mDataStore;

    private static final String MEETING_ID = "MEETING_ID";
    private Button mPositiveButton;
    private Event mEvent;

    private EditText mComments;
    private TextView mOrganizer;
    private RatingBar mRatingBar;
    private String mUsername;

    public static RatingDialogFragment newInstance(String meetingId) {
        RatingDialogFragment ratingDialogFragment = new RatingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MEETING_ID, meetingId);
        ratingDialogFragment.setArguments(bundle);
        return ratingDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String meetingId = getArguments().getString(MEETING_ID);
        mEvent = mDataStore.getEventById(meetingId);
        mUsername = mDataStore.getUser().getUsername();
        View inflatedView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_rating_dialog, null);

        mOrganizer = (TextView) inflatedView.findViewById(R.id.fragment_rating_organizer_name);
        mComments = (EditText) inflatedView.findViewById(R.id.fragment_rating_comments);
        mRatingBar = (RatingBar) inflatedView.findViewById(R.id.fragment_rating_rating_bar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(inflatedView)
                .setTitle(buildEventTitle())
                .setPositiveButton(R.string.rate_button_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String eventOwner = mEvent.getOrganizer().getEmailAddress().getName();
                        EventBus.getDefault().post(new SendRatingEvent(eventOwner, buildRatingData()));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        mOrganizer.setText(mEvent.getOrganizer().getEmailAddress().getName());

        final AlertDialog alertDialog = builder.create();

        if (savedInstanceState == null) {
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    mPositiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    //disabled until the rating is actually set!
                    mPositiveButton.setEnabled(false);
                }
            });
        }
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //user rated the event...it's ok to rate now!
                mPositiveButton.setEnabled(true);
            }
        });

        return alertDialog;
    }

    private RatingData buildRatingData() {
        return new RatingData(mEvent.getiCalUId(), mRatingBar.getRating(), mComments.getText().toString(), mUsername);
    }

    private String buildEventTitle() {
        return String.format("Rate %s", mEvent.getSubject());
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
