/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.discovery;

public interface ServiceDiscoveryCallback {

    void onServiceDiscoverySuccess();

    void onServiceDiscoveryFailure(Throwable throwable);

}
