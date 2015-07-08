/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microsoft.office365.meetingfeedback.BaseFragment;
import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.event.RefreshCalendarDataEvent;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.EventFilter;
import com.microsoft.office365.meetingfeedback.model.meeting.EventDecorator;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class CalendarPageFragment extends BaseFragment {

    private static EventFilter mMeetingFilter;
    @Inject
    DataStore mDataStore;
    private RecyclerView mEventsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mNoEventsFoundIndicator;

    public int mPage;

    private static final String FILTER_TYPE = "FILTER_TYPE";
    public static final String PAGE_NUMBER = "PAGE_NUMBER";
    private List<EventDecorator> mEventDecorators;

    public static CalendarPageFragment newInstance(int page) {
        CalendarPageFragment calendarPageFragment = new CalendarPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_NUMBER, page);
        calendarPageFragment.setArguments(bundle);
        return calendarPageFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mPage = arguments.getInt(PAGE_NUMBER);
        mEventDecorators = mDataStore.getEventGroups().get(mPage).mGroupedEventDecorators;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
        setupAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_page, container, false);
        mEventsRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_calendar_page_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_calendar_page_swipe_refresh_layout);
        mNoEventsFoundIndicator = (TextView) view.findViewById(R.id.fragment_calendar_page_no_events_indicator);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EventBus.getDefault().post(new RefreshCalendarDataEvent());
            }
        });
        return view;
    }

    public void setupAdapter() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mNoEventsFoundIndicator.setVisibility(View.VISIBLE);
        if (mEventDecorators.size() > 0) {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mNoEventsFoundIndicator.setVisibility(View.GONE);
            EventsRecyclerViewAdapter adapter = new EventsRecyclerViewAdapter(getActivity(), mDataStore, mEventDecorators);
            mEventsRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEventsRecyclerView.setHasFixedSize(true);
        mEventsRecyclerView.setLayoutManager(layoutManager);
        mEventsRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
