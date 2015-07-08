== RateMyMeetings WebService

This app currently runs on Azure at ratemymeetings.cloudapp.net

service directory: 
/home/josh/office365-android/webservice

start: 

`bundle exec foreman start`

stop: 

`kill $(ps aux | grep -E 'thin' | grep -v grep | awk '{print $2}')`

check running: 

`netstat -tulpn`

clear database: 

`rake db:drop`

`rake db:create`

`rake db:migrate`