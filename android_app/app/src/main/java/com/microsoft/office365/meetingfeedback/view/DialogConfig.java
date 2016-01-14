/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

import java.io.Serializable;

public class DialogConfig implements Serializable {

    public String mTitle;
    public String mMessage;
    public DialogType mDialogType;

    public enum DialogType {
        ALERT,
        PROGRESS
    }

    public DialogConfig(String title, String message, DialogType dialogType) {
        mTitle = title;
        mMessage = message;
        mDialogType = dialogType;
    }
}
