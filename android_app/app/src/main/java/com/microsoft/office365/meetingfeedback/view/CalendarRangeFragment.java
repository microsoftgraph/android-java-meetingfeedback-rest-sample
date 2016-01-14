/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microsoft.office365.meetingfeedback.BaseFragment;
import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.event.PageChangeEvent;
import com.microsoft.office365.meetingfeedback.model.DataStore;
import com.microsoft.office365.meetingfeedback.model.meeting.EventGroup;
import com.microsoft.office365.meetingfeedback.util.FormatUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class CalendarRangeFragment extends BaseFragment {

    public static final String PAGE = "PAGE";
    @Inject
    public DataStore mDataStore;

    private TextView mRangeOne;
    private TextView mRangeTwo;
    private TextView mRangeThree;
    private TextView mRangeFour;
    private List<TextView> mRanges;
    private int mActivePage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_range_display, container, false);
        mRangeOne = (TextView) view.findViewById(R.id.range_one);
        mRangeTwo = (TextView) view.findViewById(R.id.range_two);
        mRangeThree = (TextView) view.findViewById(R.id.range_three);
        mRangeFour = (TextView) view.findViewById(R.id.range_four);
        mRanges = Arrays.asList(mRangeOne, mRangeTwo, mRangeThree, mRangeFour);
        if (savedInstanceState != null) {
            mActivePage = savedInstanceState.getInt(PAGE, 0);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGE, mActivePage);
    }

    @Override
    public void onResume() {
        super.onResume();
        setup();
    }

    public void setup() {
        List<EventGroup> eventGroups = mDataStore.getEventGroups();
        for (int i = 0; i < eventGroups.size(); i++) {
            EventGroup eventGroup = eventGroups.get(i);
            TextView range = mRanges.get(i);
            range.setText(FormatUtil.formatDateCompact(eventGroup.mDateRange.mEnd.getTime()));
            final int page = i;
            range.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActivePage(page);
                    EventBus.getDefault().post(new PageChangeEvent(page));
                }
            });
        }
        setActivePage(mActivePage);
    }

    public void setActivePage(int i) {
        mActivePage = i;
        for (TextView range : mRanges) {
            range.setAlpha(0.5f);
            range.setTypeface(Typeface.DEFAULT);
            range.setPaintFlags(range.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
        TextView textView = mRanges.get(i);
        textView.setAlpha(1.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
