/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook;

import com.microsoft.office365.meetingfeedback.model.outlook.payload.MessageWrapper;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface EmailInterface {
    @POST("/me/microsoft.graph.sendmail")
    void sendMail(
            @Header("Content-type") String contentTypeHeader,
            @Body MessageWrapper mail,
            Callback<Void> callback);
}
