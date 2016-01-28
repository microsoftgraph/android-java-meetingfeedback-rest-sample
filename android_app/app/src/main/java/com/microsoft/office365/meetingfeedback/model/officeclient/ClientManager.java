/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.officeclient;

import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.services.orc.resolvers.ADALDependencyResolver;
import com.microsoft.services.outlook.fetchers.OutlookClient;

public class ClientManager {

    private static final String TAG = "ClientManager";
    private DataStore mDataStore;
    private final ADALDependencyResolver mResolver;

    private RatingServiceManager mRatingServiceManager;

    public ClientManager(DataStore dataStore,
                         ADALDependencyResolver resolver,
                         RatingServiceManager ratingServiceManager
                         ) {
        mDataStore = dataStore;
        mResolver = resolver;
        mRatingServiceManager = ratingServiceManager;
        mResolver.setResourceId(Constants.OUTLOOK_RESOURCE_ID);
    }

    private CalendarClientManager setupCalendarClientManager() {
        OutlookClient outlookClient = new OutlookClient(Constants.ENDPOINT_ID, mResolver);
        return new CalendarClientManager(mDataStore, outlookClient);
    }

    //todo: make these singletons possibly
    public CalendarClientManager getCalendarClientManager() {
        return setupCalendarClientManager();
    }

}
