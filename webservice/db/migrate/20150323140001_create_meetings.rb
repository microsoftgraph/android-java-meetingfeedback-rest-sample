=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
class CreateMeetings < ActiveRecord::Migration
  def change
    create_table :meetings do |t|
      t.string :event_id
      t.string :owner
    end
  end
end
