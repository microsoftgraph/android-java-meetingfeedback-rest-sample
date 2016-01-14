/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.meetingfeedback.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.microsoft.office365.meetingfeedback.R;
import com.microsoft.office365.meetingfeedback.model.meeting.RatingData;
import com.microsoft.office365.meetingfeedback.model.webservice.payload.MeetingServiceResponseData;

import java.util.ArrayList;
import java.util.List;

public class RatingsRecyclerViewAdapter extends RecyclerView.Adapter<RatingsRecyclerViewAdapter.RatingsViewHolder> {

    private Context mContext;
    private MeetingServiceResponseData mResponseData;

    public RatingsRecyclerViewAdapter(Context context, MeetingServiceResponseData responseData) {
        mContext = context;
        mResponseData = responseData;
    }

    public List<RatingData> getRatings() {
        if (mResponseData == null) {
            return new ArrayList<>();
        }
        return mResponseData.mRatings;
    }

    @Override
    public RatingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rating_view, parent, false);
        return new RatingsViewHolder(view);
    }

    public RatingData getItem(int i) {
        return getRatings().get(i);
    }


    @Override
    public void onBindViewHolder(RatingsViewHolder holder, int position) {
        RatingData item = getItem(position);
        holder.mRatingBar.setRating(item.mRating);
        holder.mRatingValue.setText(item.mRating + "");
        holder.mRatingComments.setText(item.getCommentString());
    }

    @Override
    public int getItemCount() {
        return getRatings().size();
    }

    public class RatingsViewHolder extends RecyclerView.ViewHolder {

        RatingBar mRatingBar;
        TextView mRatingValue;
        TextView mRatingComments;

        public RatingsViewHolder(View view) {
            super(view);
            mRatingBar = (RatingBar) view.findViewById(R.id.view_rating_rating_bar);
            mRatingValue = (TextView) view.findViewById(R.id.view_rating_value);
            mRatingComments = (TextView) view.findViewById(R.id.view_rating_description);
        }

    }
}
