/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.request;

import android.util.Log;

import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;

import java.util.concurrent.ExecutionException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RESTHelper {
    private static final String TAG = "RESTHelper";
    AuthenticationManager mAuthenticationManager;

    public RESTHelper(AuthenticationManager authenticationManager) {
        mAuthenticationManager = authenticationManager;
    }

    /**
     * Returns a retrofit rest adaptor class. The adaptor is created in calling code.
     *
     * @return A new RestAdapter instance.
     */
    public RestAdapter getRestAdapter() {
        //This method catches outgoing REST calls and injects the Authorization header before
        //sending to REST endpoint
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(final RequestFacade request) {
                try {
                    AuthenticationResult authenticationResult = (AuthenticationResult)mAuthenticationManager.authenticateSilent(null).get();
                    request.addHeader("Authorization", "Bearer " + authenticationResult.getAccessToken());
                    // This header has been added to identify this sample in the Microsoft Graph service.
                    // If you're using this code for your project please remove the following line.
                    request.addHeader("SampleID", "android-java-meetingfeedback-rest-sample");
                } catch (InterruptedException | ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        };

        //Sets required properties in rest adaptor class before it is created.
        return new RestAdapter.Builder()
                .setEndpoint(Constants.MICROSOFT_GRAPH_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setRequestInterceptor(requestInterceptor)
                .build();
    }

}
