=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
class MeetingsController < ApplicationController

  respond_to :json

  def index
    @responses = Meeting.all.includes(:ratings).map{|x| 
      Response.new(meeting: x, username: params[:username])
    }
  end

  def create
    @meeting = find_or_create_meeting(params[:event_id])
    @meeting.ratings << Rating.create(rating_params)
    @meeting.reload
    @response = Response.new(meeting: @meeting,
                             username: params[:username])
  end

  def mymeetings
    meetings = Meeting.where(owner: params[:username]).includes(:ratings)
    @mymeetings = meetings.map{ |x| {event_id: x.event_id, num_ratings: x.ratings.count}}
  end

  def show
    meeting = find_or_create_meeting(params[:id])
    @response = Response.new(meeting: meeting, username: params[:username])
  end

  private

  def rating_params
    params.require(:rating).permit(:value, :comment, :username, :event_id)
  end

  def find_or_create_meeting(id)
    meeting = Meeting.where(event_id: id).first
    return meeting if meeting.present?
    Meeting.create(event_id: id, owner: params[:owner])
  end

end
