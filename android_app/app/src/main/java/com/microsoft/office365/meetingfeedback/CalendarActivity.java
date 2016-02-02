/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.microsoft.office365.meetingfeedback.event.LoadCalendarFailedEvent;
import com.microsoft.office365.meetingfeedback.event.LoadCalendarSuccessEvent;
import com.microsoft.office365.meetingfeedback.event.PageChangeEvent;
import com.microsoft.office365.meetingfeedback.event.RefreshCalendarDataEvent;
import com.microsoft.office365.meetingfeedback.event.SendRatingEvent;
import com.microsoft.office365.meetingfeedback.event.SendRatingFailedEvent;
import com.microsoft.office365.meetingfeedback.event.SendRatingSuccessEvent;
import com.microsoft.office365.meetingfeedback.event.UserRatingAddedSuccessEvent;
import com.microsoft.office365.meetingfeedback.model.Constants;
import com.microsoft.office365.meetingfeedback.model.EventFilter;
import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.office365.meetingfeedback.model.officeclient.CalendarClientManager;
import com.microsoft.office365.meetingfeedback.model.service.MyMeetingsService;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.office365.meetingfeedback.util.EventUtil;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;
import com.microsoft.office365.meetingfeedback.view.CalendarFragmentPagerAdapter;
import com.microsoft.office365.meetingfeedback.view.CalendarRangeFragment;
import com.microsoft.office365.meetingfeedback.view.RatingDialogFragment;
import com.microsoft.office365.meetingfeedback.view.ShowRatingDialogEvent;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Event;
import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CalendarActivity extends NavigationBarActivity {

    private static final String TAG = "CalendarActivity";

    private ViewPager mCalendarViewPager;
    private CalendarFragmentPagerAdapter mAdapter;
    private CalendarRangeFragment mCalendarRangeFragment;
    private int mPage;
    private CalendarClientManager mOutlookClientManager;


    @Inject
    RatingServiceManager mRatingServiceManager;
    @Inject
    RatingServiceAlarmManager mRatingServiceAlarmManager;
    private String meetingToLoad;

    private Spinner mSpinner;
    private ArrayAdapter mSpinnerAdapter;
    //todo: move
    public static final String MY_MEETINGS = "My Meetings";

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        meetingToLoad = intent.getStringExtra(MyMeetingsService.EVENT_ID);
        System.out.println("-->" + meetingToLoad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalendarViewPager = (ViewPager) findViewById(R.id.activity_calendar_viewpager);
        mOutlookClientManager = mClientManager.getCalendarClientManager();
        meetingToLoad = getIntent().getStringExtra(MyMeetingsService.EVENT_ID);
        mSpinner = (Spinner) findViewById(R.id.activity_calendar_select_role);
        mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.meeting_filter_spinner_options,
                android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCurrentFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateCurrentFilter();
            }
        });
        updateCurrentFilter();
        mCalendarViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPage = position;
                setPage(mPage);
                mCalendarRangeFragment.setActivePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (savedInstanceState == null) {
            requestCalendarData();
            mCalendarRangeFragment = new CalendarRangeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_calendar_fragment_frame, mCalendarRangeFragment)
                    .commit();
        } else {
            mCalendarRangeFragment = (CalendarRangeFragment) getSupportFragmentManager().findFragmentById(R.id.activity_calendar_fragment_frame);
            setupViewPagerState();
        }
        //schedule the rating service to begin polling for new meetings
        mRatingServiceManager.loadMyMeetingsAndSchedulePolling(mRatingServiceAlarmManager);
    }

    private void updateCurrentFilter() {
        String filterValue = mSpinner.getSelectedItem().toString();
        EventFilter eventFilter = filterValue.equals(MY_MEETINGS) ? EventFilter.MY_MEETINGS : EventFilter.RATE_MEETINGS;
        mDataStore.setEventFilter(eventFilter);
        setupViewPagerState();
    }

    private void requestCalendarData() {
        mDialogUtil.showProgressDialog(this, R.string.calendar_events, R.string.calendar_events_loading);

        mCalendarService.fetchEvents();
        //mOutlookClientManager.fetchEvents();
        mRatingServiceManager.loadRatingsFromWebservice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPagerState();
    }

    public void onEvent(RefreshCalendarDataEvent event) {
        requestCalendarData();
    }

    public void onEvent(LoadCalendarFailedEvent event) {
        mDialogUtil.showAlertDialog(this, getString(R.string.failure_title), getString(R.string.loading_calendar_failed));
        mDialogUtil.dismissDialog(this);
    }

    public void onEvent(SendRatingFailedEvent event) {
        Log.e(TAG, "SendRatingFailedEvent received :(");
        mDialogUtil.dismissDialog(this);
        mDialogUtil.showAlertDialog(this, getString(R.string.failure_title), getString(R.string.send_rating_failed_exception));
    }

    public void onEvent(SendRatingSuccessEvent event) {
        mDialogUtil.dismissDialog(this);
        Log.d(TAG, "SendRatingSuccessEvent received!");
        Toast.makeText(this, "Rating Sent!", Toast.LENGTH_SHORT).show();

        //update the webservice with data as well
        setupViewPagerState();
    }

    public void onEvent(UserRatingAddedSuccessEvent event) {
        setupViewPagerState();
    }

    public void onEvent(PageChangeEvent event) {
        setPage(event.mPage);
    }

    public void onEvent(LoadCalendarSuccessEvent event) {
        mDialogUtil.dismissDialog(this);
        setupViewPagerAdapter();
        if (meetingToLoad != null) {
            startupMeetingFromId();
        }
        mCalendarRangeFragment.setup();
    }

    private void startupMeetingFromId() {
        Intent i = new Intent(CalendarActivity.this, MeetingDetailActivity.class);
        i.putExtra(MeetingDetailActivity.EVENT_ID_EXTRA, meetingToLoad);
        startActivity(i);
    }

    public void onEvent(ShowRatingDialogEvent event) {
        RatingDialogFragment.newInstance(event.mEventId).show(getSupportFragmentManager(), RATING_DIALOG_FRAGMENT_TAG);
    }

    public void onEvent(final SendRatingEvent sendRatingEvent) {
        mDialogUtil.showProgressDialog(this, getString(R.string.submit_rating), getString(R.string.submitting_rating_description));

        final Event event = mDataStore.getEventById(sendRatingEvent.mRatingData.mEventId);
        String subject = String.format(
                "Your meeting, %s, on %s (%s) , was recently reviewed.",
                event.mSubject,
                FormatUtil.displayFormattedEventDate(event),
                FormatUtil.displayFormattedEventTime(event));
        StringBuilder stringBuilder = new StringBuilder();
        String eventDate = FormatUtil.displayFormattedEventDate(event);
        String eventTime = FormatUtil.displayFormattedEventTime(event);
        stringBuilder.append(String.format("<div>Your meeting, %s, on %s (%s) , was recently reviewed.</div>", event.mSubject, eventDate, eventTime));
        stringBuilder.append(String.format("<div>Rating: %s </div>", sendRatingEvent.mRatingData.mRating));
        String remarks = TextUtils.isEmpty(sendRatingEvent.mRatingData.mReview) ? "No Remarks." : sendRatingEvent.mRatingData.mReview;
        stringBuilder.append(String.format("<div>Remarks/How to improve: %s</div>", remarks));
        String body = stringBuilder.toString();

        mEmailService.sendMail(
                event.mOrganizer.emailAddress.mAddress,
                subject,
                body,
                new Callback<Void>() {
                    @Override
                    public void success(Void aVoid, Response response) {
                        //update the webservice with the ratingEvent rating
                        String eventOwner = EventUtil.getEventOwner(event);
                        mRatingServiceManager.addRating(eventOwner, sendRatingEvent.mRatingData);
                        EventBus.getDefault().post(new SendRatingSuccessEvent(sendRatingEvent.mRatingData.mEventId));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        EventBus.getDefault().post(new SendRatingFailedEvent(error));
                    }
                }
        );
    }

    private void setupViewPagerState() {
        setupViewPagerAdapter();
        setPage(mPage);
    }

    private void setPage(int page) {
        setPageTitle(page);
        mCalendarViewPager.setCurrentItem(page);
    }

    private void setPageTitle(int position) {
        EventGroup eventGroup = mDataStore.getEventGroups().get(position);
        setTitle(eventGroup.mDateRange.getRangeAsFormattedString());
    }

    private void setupViewPagerAdapter() {
        mAdapter = new CalendarFragmentPagerAdapter(getSupportFragmentManager());
        mCalendarViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
