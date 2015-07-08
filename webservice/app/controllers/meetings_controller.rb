=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
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
=begin
    O365-Android-MeetingFeedback, https://github.com/OfficeDev/O365-Android-MeetingFeedback

    Copyright (c) Microsoft Corporation
    All rights reserved.

    MIT License:
    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
=end