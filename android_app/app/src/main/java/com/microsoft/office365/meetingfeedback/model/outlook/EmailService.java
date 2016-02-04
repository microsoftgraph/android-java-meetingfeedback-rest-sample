/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.outlook;

import android.text.TextUtils;

import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Body;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.EmailAddress;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Event;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.From;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Message;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.MessageWrapper;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Sender;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.ToRecipient;
import com.microsoft.office365.meetingfeedback.model.request.RESTHelper;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Handles the creation of the message and contacting the
 * mail service to send the message. The app must have
 * connected to Office 365 and discovered the mail service
 * endpoints before using the createDraftMail method.
 */
public class EmailService {

    private EmailInterface mEmailClient;

    public EmailService(AuthenticationManager authenticationManager) {
        RestAdapter restAdapter = new RESTHelper(authenticationManager).getRestAdapter();

        mEmailClient = restAdapter.create(EmailInterface.class);
    }

    /**
     * Sends an email message using the Microsoft Graph API on Office 365. The mail is sent
     * from the address of the signed in user.
     *
     * @param event      Details about the event.
     * @param ratingData Details about the rating (rate and comments).
     */
    public void sendRatingMail (
            final Event event,
            final RatingData ratingData,
            Callback<Void> callback) {

        // create the email
        MessageWrapper msg = createMailPayload(
                formatSubject(event),
                formatBody(event, ratingData),
                event.mOrganizer.emailAddress.mAddress
        );

        // send it using our service
        mEmailClient.sendMail("application/json", msg, callback);
    }

    private MessageWrapper createMailPayload(
            String subject,
            String htmlBody,
            String recipient) {
        EmailAddress mailRecipient = new EmailAddress();
        mailRecipient.mAddress = recipient;

        ToRecipient toRecipient = new ToRecipient();
        toRecipient.emailAddress = mailRecipient;

        EmailAddress ratingAddress = new EmailAddress();
        ratingAddress.mAddress = Constants.REVIEW_SENDER_ADDRESS;

        Sender sender = new Sender();
        sender.emailAddress = ratingAddress;
        From from = new From();
        from.emailAddress = ratingAddress;

        Body body = new Body();
        body.mContentType = "HTML";
        body.mContent = htmlBody;

        Message sampleMsg = new Message();
        sampleMsg.mSubject = subject;
        sampleMsg.mBody = body;
        sampleMsg.mToRecipients = new ToRecipient[]{toRecipient};
        sampleMsg.mSender = sender;
        sampleMsg.mFrom = from;

        return new MessageWrapper(sampleMsg);
    }

    private String formatSubject(Event event) {
        return String.format(
                "Your meeting, %s, on %s (%s) , was recently reviewed.",
                event.mSubject,
                FormatUtil.displayFormattedEventDate(event),
                FormatUtil.displayFormattedEventTime(event));
    }

    private String formatBody(Event event, RatingData ratingData) {
        StringBuilder stringBuilder = new StringBuilder();
        String eventDate = FormatUtil.displayFormattedEventDate(event);
        String eventTime = FormatUtil.displayFormattedEventTime(event);
        stringBuilder.append(String.format("<div>Your meeting, %s, on %s (%s) , was recently reviewed.</div>", event.mSubject, eventDate, eventTime));
        stringBuilder.append(String.format("<div>Rating: %s </div>", ratingData.mRating));
        String remarks = TextUtils.isEmpty(ratingData.mReview) ? "No Remarks." : ratingData.mReview;
        stringBuilder.append(String.format("<div>Remarks/How to improve: %s</div>", remarks));
        return stringBuilder.toString();
    }
}
