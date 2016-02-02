/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook.payload;

import com.google.gson.annotations.SerializedName;

public class Date {

    @SerializedName("dateTime")
    public String mDateTime;

    @SerializedName("timeZone")
    public String mTimeZone;
}
