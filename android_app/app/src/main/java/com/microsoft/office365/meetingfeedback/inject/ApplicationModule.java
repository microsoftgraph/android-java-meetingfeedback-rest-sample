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
