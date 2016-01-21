/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.model.officeclient;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.microsoft.office365.meetingfeedback.event.SendRatingFailedEvent;
import com.microsoft.office365.meetingfeedback.event.SendRatingSuccessEvent;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;
import com.microsoft.services.outlook.Event;
import com.microsoft.services.outlook.ItemBody;
import com.microsoft.services.outlook.Message;
import com.microsoft.services.outlook.Recipient;
import com.microsoft.services.outlook.fetchers.OutlookClient;

import java.util.Collections;

import de.greenrobot.event.EventBus;

public class EmailClientManager {
    private final RatingServiceManager mRatingServiceManager;
    private DataStore mDataStore;
    private OutlookClient mOutlookClient;

    public EmailClientManager(DataStore dataStore, OutlookClient outlookClient, RatingServiceManager ratingServiceManager) {
        mDataStore = dataStore;
        mOutlookClient = outlookClient;
        mRatingServiceManager = ratingServiceManager;
    }

    public void sendRating(final RatingData ratingData) {

        final Event event = getEvent(ratingData.mEventId);

        final Message ratingMessage = buildMessage(ratingData, event);
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                Integer ret = null;
                try {
                    ret = mOutlookClient.getMe().getOperations().sendMail(ratingMessage, false).get();
                } catch (Exception e) {
                    EventBus.getDefault().post(new SendRatingFailedEvent(e));
                    cancel(true);
                }
                return ret;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                //update the webservice with the event rating
                String eventOwner = EventUtil.getEventOwner(event);
                mRatingServiceManager.addRating(eventOwner, ratingData);
                EventBus.getDefault().post(new SendRatingSuccessEvent(ratingData.mEventId));
            }
        }.execute();
    }

    private Message buildMessage(RatingData ratingData, Event event) {
        Message ratingMessage = new Message();
        ratingMessage.setToRecipients(Collections.singletonList(event.getOrganizer()));
        ratingMessage.setBody(buildMessageBody(ratingData, event));
        Recipient anonymousSender = Constants.getReviewSenderRecipient();
        ratingMessage.setSender(anonymousSender);
        ratingMessage.setFrom(anonymousSender);
        ratingMessage.setReplyTo(Collections.singletonList(anonymousSender));
        ratingMessage.setSubject(event.getSubject() + " was rated!");
        return ratingMessage;
    }

    private ItemBody buildMessageBody(RatingData ratingData, Event event) {
        ItemBody body = new ItemBody();
        StringBuilder stringBuilder = new StringBuilder();
        String eventDate = FormatUtil.displayFormattedEventDate(event);
        String eventTime = FormatUtil.displayFormattedEventTime(event);
        stringBuilder.append(String.format("Your meeting, %s, on %s (%s) , was recently reviewed. \n\n", event.getSubject(), eventDate, eventTime));
        stringBuilder.append(String.format("Rating: %s \n", ratingData.mRating));
        String remarks = TextUtils.isEmpty(ratingData.mReview) ? "No Remarks." : ratingData.mReview;
        stringBuilder.append(String.format("Remarks/How to improve: %s", remarks));
        String content = stringBuilder.toString();
        body.setContent(content);
        return body;
    }

    private Event getEvent(String id) {
        return mDataStore.getEventById(id);
    }

}
