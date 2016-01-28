/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model;

import com.microsoft.services.outlook.EmailAddress;
import com.microsoft.services.outlook.Recipient;


public class Constants {
    public static final String AUTHORITY_URL = "https://login.microsoftonline.com/common";
    public static final String OUTLOOK_RESOURCE_ID = "https://outlook.office365.com/";

    // Update these constants with the values for your application:
    public static final String CLIENT_ID = "<YOUR_CLIENT_ID>";
    public static final String REDIRECT_URI = "<YOUR_REDIRECT_URI>";
    public static final String SERVICE_ENDPOINT = "<YOUR_SERVICE_ENDPOINT>";
    public static final String REVIEW_SENDER_ADDRESS = "<YOUR_REVIEW_SENDER_ADDRESS>";

    public static final String ENDPOINT_ID = "https://outlook.office365.com/api/v1.0";

    public static final String MICROSOFT_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/";
    public static final String MICROSOFT_GRAPH_RESOURCE_ID = "https://graph.microsoft.com/";

    public static Recipient getReviewSenderRecipient() {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(REVIEW_SENDER_ADDRESS);
        Recipient recipient = new Recipient();
        recipient.setEmailAddress(emailAddress);
        return recipient;
    }

}
