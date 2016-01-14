#    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
#    See LICENSE in the project root for license information.

json.meeting_data @mymeetings do |meeting|
  json.event_id meeting[:event_id]
  json.num_ratings meeting[:num_ratings]
end
