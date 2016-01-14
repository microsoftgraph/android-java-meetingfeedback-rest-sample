/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.microsoft.office365.meetingfeedback.BaseActivity;
import com.microsoft.office365.meetingfeedback.view.DialogConfig.DialogType;
import com.microsoft.office365.meetingfeedback.view.RateMyMeetingsDialogFragment;

public class DialogUtil {

    public static final String TAG_DIALOG = "TAG_DIALOG";
    private static final String TAG = "DialogUtil";

    public void showProgressDialog(BaseActivity activity, int titleResId, int messageResId) {
        String title = null;
        if (titleResId > 0) {
            title = activity.getString(titleResId);
        }
        String message = null;
        if (messageResId > 0) {
            message = activity.getString(messageResId);
        }
        showProgressDialog(activity, title, message);
    }

    public void showProgressDialog(BaseActivity activity, String title, String message) {
        Log.d(TAG, "displaying!");
        activity.setDialog(RateMyMeetingsDialogFragment.newInstance(title, message, DialogType.PROGRESS));
        getDialog(activity).show(activity.getSupportFragmentManager(), TAG_DIALOG);
    }

    public void showAlertDialog(BaseActivity activity, String title, String message) {
        activity.setDialog(RateMyMeetingsDialogFragment.newInstance(title, message, DialogType.ALERT));
        getDialog(activity).show(activity.getSupportFragmentManager(), TAG_DIALOG);
    }


    public void dismissDialog(BaseActivity activity) {
        Fragment dialogFragment = activity.getSupportFragmentManager().findFragmentByTag(TAG_DIALOG);
        if (dialogFragment != null) {
            DialogFragment df = (DialogFragment) dialogFragment;
            df.dismiss();
            activity.setDialog(null);
        }
    }

    private RateMyMeetingsDialogFragment getDialog(BaseActivity activity) {
        return activity.getDialog();
    }


}
