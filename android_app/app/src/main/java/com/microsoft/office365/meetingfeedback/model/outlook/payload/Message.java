/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook.payload;

import com.google.gson.annotations.SerializedName;

/**
 * Mail Value Object for holding values in an email
 */
public class Message {

    @SerializedName("Subject")
    public String mSubject;

    @SerializedName("Body")
    public Body mBody;

    @SerializedName("ToRecipients")
    public ToRecipient[] mToRecipients;

    @SerializedName("sender")
    public Sender mSender;

    @SerializedName("from")
    public From mFrom;
}
