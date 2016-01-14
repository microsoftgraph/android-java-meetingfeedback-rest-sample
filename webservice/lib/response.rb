=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
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
