/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.email;

import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.authentication.AuthenticationManager;
import com.microsoft.office365.meetingfeedback.model.email.payload.Body;
import com.microsoft.office365.meetingfeedback.model.email.payload.EmailAddress;
import com.microsoft.office365.meetingfeedback.model.email.payload.Froms;
import com.microsoft.office365.meetingfeedback.model.email.payload.Message;
import com.microsoft.office365.meetingfeedback.model.email.payload.MessageWrapper;
import com.microsoft.office365.meetingfeedback.model.email.payload.Senders;
import com.microsoft.office365.meetingfeedback.model.email.payload.ToRecipients;
import com.microsoft.office365.meetingfeedback.model.request.RESTHelper;

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
     * @param emailAddress The recipient email address.
     * @param subject      The subject to use in the mail message.
     * @param body         The body of the message.
     * @param callback     UI callback to be invoked by Retrofit call when
     *                     operation completed
     */
    public void sendMail(
            final String recipient,
            final String subject,
            final String body,
            Callback<Void> callback) {
        // create the email
        MessageWrapper msg = createMailPayload(subject, body, recipient);

        // send it using our service
        mEmailClient.sendMail("application/json", msg, callback);
    }


    private MessageWrapper createMailPayload(
            String subject,
            String htmlBody,
            String recipient) {
        EmailAddress mailRecipient = new EmailAddress();
        mailRecipient.mAddress = recipient;

        ToRecipients toRecipients = new ToRecipients();
        toRecipients.emailAddress = mailRecipient;

        EmailAddress mailSender = new EmailAddress();
        mailSender.mAddress = Constants.REVIEW_SENDER_ADDRESS;

        Senders mailSenders = new Senders();
        mailSenders.emailAddress = mailSender;
        Froms mailFroms = new Froms();
        mailFroms.emailAddress = mailSender;

        Body body = new Body();
        body.mContentType = "HTML";
        body.mContent = "Body";//htmlBody;

        Message sampleMsg = new Message();
        sampleMsg.mSubject = "Subject";//subject;
        sampleMsg.mBody = body;
        sampleMsg.mToRecipients = new ToRecipients[]{toRecipients};
        //sampleMsg.mSenders = new Senders[]{mailSenders};
        //sampleMsg.mFroms = new Froms[]{mailFroms};

        return new MessageWrapper(sampleMsg);
    }

}