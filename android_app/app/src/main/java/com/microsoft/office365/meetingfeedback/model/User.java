/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model;

import com.microsoft.aad.adal.UserInfo;

public class User {

    private String mUsername;
    private String mFirstName;
    private String mLastName;

    public User(UserInfo userInfo) {
        mUsername = userInfo.getDisplayableId();
        mFirstName = userInfo.getGivenName();
        mLastName = userInfo.getFamilyName();
    }

    public String getUsername() {
        return mUsername;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

}
