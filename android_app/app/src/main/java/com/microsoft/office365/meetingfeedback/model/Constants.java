/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.model;

import com.microsoft.outlookservices.EmailAddress;
import com.microsoft.outlookservices.Recipient;


public class Constants {
    public static final String AUTHORITY_URL = "https://login.microsoftonline.com/common";
    public static final String DISCOVERY_RESOURCE_URL = "https://api.office.com/discovery/v1.0/me/";
    public static final String DISCOVERY_RESOURCE_ID = "https://api.office.com/discovery/";
    public static final String MAIL_CAPABILITY = "Mail";
    public static final String CALENDAR_ID = "Calendar";

    // Update these constants with the values for your application:
    public static final String CLIENT_ID = "<YOUR_CLIENT_ID>";
    public static final String REDIRECT_URI = "<YOUR_REDIRECT_URI>";
    public static final String SERVICE_ENDPOINT = "<YOUR_SERVICE_ENDPOINT>";
    public static final String REVIEW_SENDER_ADDRESS = "<YOUR_REVIEW_SENDER_ADDRESS>";

    public static final String ENDPOINT_ID = "https://outlook.office365.com/" + "api/v1.0";

    public static Recipient getReviewSenderRecipient() {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(REVIEW_SENDER_ADDRESS);
        Recipient recipient = new Recipient();
        recipient.setEmailAddress(emailAddress);
        return recipient;
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
