/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.util;

import com.microsoft.outlookservices.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {

    public static String displayFormattedEventDate(Event event) {
        Date eventDate = event.getStart().getTime();
        return formatDate(eventDate);
    }

    public static String formatDate(Date eventDate) {
        return new SimpleDateFormat("MMMM dd", Locale.US).format(eventDate);
    }
    public static String formatDateCompact(Date eventDate) {
        return new SimpleDateFormat("MMM dd", Locale.US).format(eventDate);
    }

    public static String displayFormattedEventTime(Event event) {
        Date eventDate = event.getStart().getTime();
        Date eventDateEnd = event.getEnd().getTime();
        String formattedStartTime = new SimpleDateFormat("HH:mm", Locale.US).format(eventDate);
        String formattedEndTime = new SimpleDateFormat("HH:mm", Locale.US).format(eventDateEnd);
        return String.format("%s - %s", formattedStartTime, formattedEndTime);
    }

    public static String htmlTextWithoutComments(String htmlText) {
        return htmlText.replaceAll("(?s)<!--.*?-->", "");
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
