/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.microsoft.office365.meetingfeedback.model.EventFilter;
import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.office365.meetingfeedback.model.outlook.payload.Event;
import com.microsoft.office365.meetingfeedback.model.service.RatingServiceAlarmManager;
import com.microsoft.office365.meetingfeedback.model.webservice.RatingServiceManager;
import com.microsoft.office365.meetingfeedback.view.CalendarFragmentPagerAdapter;
import com.microsoft.office365.meetingfeedback.view.CalendarRangeFragment;
import com.microsoft.office365.meetingfeedback.view.RatingDialogFragment;
import com.microsoft.office365.meetingfeedback.view.ShowRatingDialogEvent;

import javax.inject.Inject;

public class CalendarActivity extends NavigationBarActivity {

    private static final String TAG = "CalendarActivity";
    public static final String MY_MEETINGS = "My Meetings";

    private ViewPager mCalendarViewPager;
    private CalendarFragmentPagerAdapter mAdapter;
    private CalendarRangeFragment mCalendarRangeFragment;
    private int mPage;

    @Inject
    RatingServiceManager mRatingServiceManager;
    @Inject
    RatingServiceAlarmManager mRatingServiceAlarmManager;

    private Spinner mSpinner;
    private ArrayAdapter mSpinnerAdapter;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalendarViewPager = (ViewPager) findViewById(R.id.activity_calendar_viewpager);
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
                mCalendarRangeFragment.setActivePage(mPage);
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
        mCalendarRangeFragment.setup();
        setPage(mPage);
    }

    public void onEvent(ShowRatingDialogEvent event) {
        RatingDialogFragment.newInstance(event.mEventId).show(getSupportFragmentManager(), RATING_DIALOG_FRAGMENT_TAG);
    }

    public void onEvent(final SendRatingEvent sendRatingEvent) {
        mDialogUtil.showProgressDialog(
                this,
                getString(R.string.submit_rating),
                getString(R.string.submitting_rating_description)
        );

        final Event event = mDataStore.getEventById(sendRatingEvent.mRatingData.mEventId);

        mEmailService.sendRatingMail(
                event,
                sendRatingEvent.mRatingData
        );

        mRatingServiceManager.addRating(
                event.mOrganizer.emailAddress.mAddress,
                sendRatingEvent.mRatingData
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
