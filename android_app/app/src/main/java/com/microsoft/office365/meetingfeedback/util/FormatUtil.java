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
