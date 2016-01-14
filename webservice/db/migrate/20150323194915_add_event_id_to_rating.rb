=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
class AddEventIdToRating < ActiveRecord::Migration
  def change
     add_column :ratings, :event_id, :string
  end
end
