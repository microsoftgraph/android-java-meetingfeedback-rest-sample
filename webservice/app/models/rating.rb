=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
class Rating < ActiveRecord::Base
  belongs_to :meeting
  validates :username, uniqueness: {scope: [:meeting_id]}
end
