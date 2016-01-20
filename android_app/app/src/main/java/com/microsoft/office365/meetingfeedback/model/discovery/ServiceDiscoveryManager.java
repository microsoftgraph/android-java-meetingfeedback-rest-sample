/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.discovery;

import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.services.discovery.ServiceInfo;
import com.microsoft.services.discovery.fetchers.DiscoveryClient;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.services.orc.core.DependencyResolver;

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
                    .getServices()
                    .select("serviceResourceId,serviceEndpointUri,capability")
                    .read();
            Futures.addCallback(services,
                    new FutureCallback<List<ServiceInfo>>() {
                        @Override
                        public void onSuccess(final List<ServiceInfo> result) {
                            for (ServiceInfo serviceInfo : result) {
                                mServicesMap.put(serviceInfo.getCapability(), serviceInfo);
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
