/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
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
