#    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.

json.event_id @response.event_id
json.owner @response.owner
json.is_meeting_owner @response.meeting_owner?
json.has_already_rated @response.has_already_rated?
if @response.has_already_rated?
  json.your_rating @response.your_rating
end
json.avg_rating @response.avg_rating
json.ratings @response.ratings

#
#    O365-Android-MeetingFeedback, https://github.com/OfficeDev/O365-Android-MeetingFeedback
#
#    Copyright (c) Microsoft Corporation
#    All rights reserved.
#
#    MIT License:
#    Permission is hereby granted, free of charge, to any person obtaining
#    a copy of this software and associated documentation files (the
#    "Software"), to deal in the Software without restriction, including
#    without limitation the rights to use, copy, modify, merge, publish,
#    distribute, sublicense, and/or sell copies of the Software, and to
#    permit persons to whom the Software is furnished to do so, subject to
#    the following conditions:
#
#    The above copyright notice and this permission notice shall be
#    included in all copies or substantial portions of the Software.
#
#    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
#    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
#    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
#    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
#    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
#    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
#    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#