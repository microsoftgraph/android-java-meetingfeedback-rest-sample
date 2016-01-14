=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
class CreateRatings < ActiveRecord::Migration
  def change
    create_table :ratings do |t|
      t.decimal :value, required: true
      t.belongs_to :meeting
      t.text :comment
      t.string :username
    end
    add_index :ratings, :meeting_id
  end
end
