/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.email.payload;

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
    public ToRecipients[] mToRecipients;

    @SerializedName("sender")
    public Senders[] mSenders;

    @SerializedName("from")
    public Froms[] mFroms;
}