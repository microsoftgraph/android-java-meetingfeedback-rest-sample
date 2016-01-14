/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.officeclient;

import com.microsoft.discoveryservices.ServiceInfo;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.discovery.ServiceDiscoveryManager;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.outlookservices.odata.OutlookClient;
import com.microsoft.services.odata.impl.ADALDependencyResolver;

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
        mResolver.setResourceId(serviceInfo.getserviceResourceId());
        OutlookClient outlookClient = new OutlookClient(Constants.ENDPOINT_ID, mResolver);
        return new CalendarClientManager(mDataStore, outlookClient);
    }

    private EmailClientManager setupMailClientManager() {
        ServiceInfo serviceInfo = mServiceDiscoveryManager.getMailServiceInfo();
        mResolver.setResourceId(serviceInfo.getserviceResourceId());
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
