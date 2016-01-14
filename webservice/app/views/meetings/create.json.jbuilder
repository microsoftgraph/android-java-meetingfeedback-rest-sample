#    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
#    See LICENSE in the project root for license information.

json.event_id @response.event_id
json.owner @response.owner
json.is_meeting_owner @response.meeting_owner?
json.has_already_rated @response.has_already_rated?
if @response.has_already_rated?
  json.your_rating @response.your_rating
end
json.avg_rating @response.avg_rating
json.ratings @response.ratings
