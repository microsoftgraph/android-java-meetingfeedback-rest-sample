/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model.discovery;

import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.discoveryservices.ServiceInfo;
import com.microsoft.discoveryservices.odata.DiscoveryClient;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.services.odata.interfaces.DependencyResolver;

import java.util.HashMap;
import java.util.List;

public class ServiceDiscoveryManager {

    public static final String TAG = "ServiceDiscoveryManager";
    private final DependencyResolver mDependencyResolver;

    private HashMap<String, ServiceInfo> mServicesMap = new HashMap<>();

    public ServiceDiscoveryManager(DependencyResolver dependencyResolver) {
        mDependencyResolver = dependencyResolver;
    }

    public void discoverServices(final ServiceDiscoveryCallback callback) {
        DiscoveryClient discoveryClient = new DiscoveryClient(Constants.DISCOVERY_RESOURCE_URL, mDependencyResolver);
        try {
            ListenableFuture<List<ServiceInfo>> services = discoveryClient
                    .getservices()
                    .select("serviceResourceId,serviceEndpointUri,capability")
                    .read();
            Futures.addCallback(services,
                    new FutureCallback<List<ServiceInfo>>() {
                        @Override
                        public void onSuccess(final List<ServiceInfo> result) {
                            for (ServiceInfo serviceInfo : result) {
                                mServicesMap.put(serviceInfo.getcapability(), serviceInfo);
                            }
                            Log.d(TAG, "Services Discovered. result = " + mServicesMap);
                            callback.onServiceDiscoverySuccess();
                        }

                        @Override
                        public void onFailure(final Throwable throwable) {
                            Log.e("Asset", throwable.getMessage());
                            callback.onServiceDiscoveryFailure(throwable);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Exception!", e);
            callback.onServiceDiscoveryFailure(e);
        }
    }

    public ServiceInfo getCalendarServiceInfo() {
        return mServicesMap.get(Constants.CALENDAR_ID);
    }

    public ServiceInfo getMailServiceInfo() {
        return mServicesMap.get(Constants.MAIL_CAPABILITY);
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
