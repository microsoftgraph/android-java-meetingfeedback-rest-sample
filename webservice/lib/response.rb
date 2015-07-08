=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
=end
class Response

  attr_reader :event_id, :username, :ratings, :avg_rating, :owner

  def initialize(args={})
    @meeting = args.fetch(:meeting)
    @user_param = args.fetch(:username)
  end

  def meeting_owner?
    @user_param == owner
  end

  def has_already_rated?
    your_rating_data.present?
  end

  def your_rating
    return 0.0 if your_rating_data.nil?
    your_rating_data.value
  end

  def owner
    @meeting.owner
  end

  def ratings
    @meeting.ratings
  end

  def event_id
    @meeting.event_id
  end

  def avg_rating
    return 0.0 if ratings.empty?
    ratings.map{ |x| x.value }.inject{|sum, el| sum + el}.to_f / ratings.size
  end

  private

  def your_rating_data
    ratings.where('username = ?', @user_param).first
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