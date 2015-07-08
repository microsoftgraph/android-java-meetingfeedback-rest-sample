/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class RateMyMeetingsDialogFragment extends DialogFragment {

    private static final String ARG_DIALOG_CONFIG = "ARG_DIALOG_CONFIG";

    public static RateMyMeetingsDialogFragment newInstance(String title, String message, DialogConfig.DialogType dialogType) {
        Bundle args = new Bundle();
        DialogConfig dialogConfig = new DialogConfig(title, message, dialogType);
        args.putSerializable(ARG_DIALOG_CONFIG, dialogConfig);
        RateMyMeetingsDialogFragment fragment = new RateMyMeetingsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        DialogConfig dialogConfig = (DialogConfig) getArguments().getSerializable(ARG_DIALOG_CONFIG);
        setCancelable(false);
        switch (dialogConfig.mDialogType) {
            case ALERT:
                return buildAlertDialogFromConfig(dialogConfig);
            default:
            case PROGRESS:
                return buildProgressDialogFromConfig(dialogConfig);
        }
    }

    private AlertDialog buildAlertDialogFromConfig(DialogConfig dialogConfig) {
        return new AlertDialog.Builder(getActivity()).setTitle(dialogConfig.mTitle)
                .setMessage(dialogConfig.mMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private AlertDialog buildProgressDialogFromConfig(DialogConfig dialogConfig) {
        return new ProgressDialog.Builder(getActivity()).setTitle(dialogConfig.mTitle)
                .setMessage(dialogConfig.mMessage)
                .create();
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
