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
