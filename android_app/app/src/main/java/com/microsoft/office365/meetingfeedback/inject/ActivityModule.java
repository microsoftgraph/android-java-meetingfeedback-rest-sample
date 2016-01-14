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
