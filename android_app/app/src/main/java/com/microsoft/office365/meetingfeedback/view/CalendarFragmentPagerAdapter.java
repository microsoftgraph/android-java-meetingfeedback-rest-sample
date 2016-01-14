/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.microsoft.office365.meetingfeedback.util.CalendarUtil;

public class CalendarFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public CalendarFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CalendarPageFragment getItem(int position) {
        return CalendarPageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return CalendarUtil.NUM_PAGES_IN_CALENDAR;
    }

}
