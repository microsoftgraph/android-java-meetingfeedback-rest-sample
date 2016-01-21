/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.officeclient;

import com.microsoft.services.discovery.ServiceInfo;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.discovery.ServiceDiscoveryManager;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.services.outlook.fetchers.OutlookClient;
import com.microsoft.services.orc.resolvers.ADALDependencyResolver;

public class ClientManager {

    private static final String TAG = "ClientManager";
    private DataStore mDataStore;
    private final ADALDependencyResolver mResolver;
    private ServiceDiscoveryManager mServiceDiscoveryManager;

    private RatingServiceManager mRatingServiceManager;

    public ClientManager(DataStore dataStore,
                         ADALDependencyResolver resolver,
                         ServiceDiscoveryManager serviceDiscoveryManager,
                         RatingServiceManager ratingServiceManager
                         ) {
        mDataStore = dataStore;
        mResolver = resolver;
        mServiceDiscoveryManager = serviceDiscoveryManager;
        mRatingServiceManager = ratingServiceManager;
    }

    private CalendarClientManager setupCalendarClientManager() {
        ServiceInfo serviceInfo = mServiceDiscoveryManager.getCalendarServiceInfo();
        mResolver.setResourceId(serviceInfo.getServiceResourceId());
        OutlookClient outlookClient = new OutlookClient(Constants.ENDPOINT_ID, mResolver);
        return new CalendarClientManager(mDataStore, outlookClient);
    }

    private EmailClientManager setupMailClientManager() {
        ServiceInfo serviceInfo = mServiceDiscoveryManager.getMailServiceInfo();
        mResolver.setResourceId(serviceInfo.getServiceResourceId());
        OutlookClient outlookClient = new OutlookClient(Constants.ENDPOINT_ID, mResolver);
        return new EmailClientManager(mDataStore, outlookClient, mRatingServiceManager);
    }

    //todo: make these singletons possibly
    public CalendarClientManager getCalendarClientManager() {
        return setupCalendarClientManager();
    }

    public EmailClientManager getEmailClientManager() {
        return setupMailClientManager();
    }

}
