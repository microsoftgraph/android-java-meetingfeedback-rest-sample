/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.app.Application;

import com.microsoft.office365.meetingfeedback.inject.ApplicationModule;

import dagger.ObjectGraph;

public class MeetingFeedbackApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(new ApplicationModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
