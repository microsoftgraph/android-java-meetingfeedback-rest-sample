/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.microsoft.office365.meetingfeedback.inject.ActivityModule;

import dagger.ObjectGraph;

public abstract class BaseDialogFragment extends DialogFragment {

    private ObjectGraph mActivityGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeetingFeedbackApplication application = (MeetingFeedbackApplication) getActivity().getApplication();
        mActivityGraph = application.getApplicationGraph().plus(new ActivityModule(getBaseActivity()));
        mActivityGraph.inject(this);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
