/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook.payload;

import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("ContentType")
    public String mContentType;

    @SerializedName("Content")
    public String mContent;

}
