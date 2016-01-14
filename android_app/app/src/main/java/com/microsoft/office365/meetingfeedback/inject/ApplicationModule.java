/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.inject;

import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.office365.meetingfeedback.MeetingFeedbackApplication;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationContextBuilder;
import com.microsoft.office365.meetingfeedback.model.discovery.ServiceDiscoveryManager;
import com.microsoft.office365.meetingfeedback.model.officeclient.ClientManager;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsService;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingService;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.office365.meetingfeedback.util.ConnectivityUtil;
import com.microsoft.office365.meetingfeedback.util.SharedPrefsUtil;
import com.microsoft.services.odata.impl.ADALDependencyResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {MyMeetingsService.class})
public class ApplicationModule {

    private final MeetingFeedbackApplication mApplication;

    public ApplicationModule(MeetingFeedbackApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public ADALDependencyResolver providesADALDependencyResolver(AuthenticationContext authContext) {
        return new ADALDependencyResolver(authContext, Constants.DISCOVERY_RESOURCE_ID, Constants.CLIENT_ID);
    }

    @Provides
    @Singleton
    public ServiceDiscoveryManager providesServiceDiscoveryManager(ADALDependencyResolver adalDependencyResolver) {
        return new ServiceDiscoveryManager(adalDependencyResolver);
    }

    @Provides
    @Singleton
    public RatingServiceManager providesRatingServiceManager(DataStore dataStore) {
        return new RatingServiceManager(new RatingService(), dataStore);
    }

    @Provides
    public SharedPrefsUtil providesSharedPrefsUtil() {
        return new SharedPrefsUtil(mApplication);
    }

    @Provides
    @Singleton
    public DataStore providesDataStore(SharedPrefsUtil sharedPrefsUtil) {
        return new DataStore(sharedPrefsUtil);
    }

    @Provides
    @Singleton
    public ClientManager providesClientManager(DataStore dataStore,
                                               ADALDependencyResolver resolver,
                                               ServiceDiscoveryManager serviceDiscoveryManager,
                                               RatingServiceManager ratingServiceManager) {
        return new ClientManager(dataStore, resolver, serviceDiscoveryManager, ratingServiceManager);
    }

    @Provides
    @Singleton
    public AuthenticationContext providesAuthenticationContext() {
        return AuthenticationContextBuilder.newInstance(mApplication); //todo: will application context work in all cases here?
    }

    @Provides
    public ConnectivityUtil providesConnectivityUtil() {
        return new ConnectivityUtil(mApplication);
    }

    @Provides
    public RatingServiceAlarmManager providesRatingServiceAlarmManager() {
        return new RatingServiceAlarmManager(mApplication);
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
