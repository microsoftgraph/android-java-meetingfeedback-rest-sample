/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.inject;

import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.office365.meetingfeedback.BaseActivity;
import com.microsoft.office365.meetingfeedback.CalendarActivity;
import com.microsoft.office365.meetingfeedback.ConnectActivity;
import com.microsoft.office365.meetingfeedback.MeetingDetailActivity;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationContextBuilder;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.util.DialogUtil;
import com.microsoft.office365.meetingfeedback.view.CalendarPageFragment;
import com.microsoft.office365.meetingfeedback.view.CalendarRangeFragment;
import com.microsoft.office365.meetingfeedback.view.RatingDialogFragment;
import com.microsoft.services.odata.impl.ADALDependencyResolver;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
        ConnectActivity.class,
        CalendarActivity.class,
        MeetingDetailActivity.class,
        CalendarRangeFragment.class,
        RatingDialogFragment.class,
        CalendarPageFragment.class,
},
        addsTo = ApplicationModule.class,
        library = true)

public class ActivityModule {

    private BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    public DialogUtil providesDialogUtil() {
        return new DialogUtil();
    }

    @Provides
    public AuthenticationManager providesAuthenticationManager(DataStore dataStore,
                                                               ADALDependencyResolver resolver,
                                                               RatingServiceAlarmManager alarmManager) {
        AuthenticationContext authenticationContext = AuthenticationContextBuilder.newInstance(mActivity);
        return new AuthenticationManager(resolver, dataStore, authenticationContext, alarmManager);
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
