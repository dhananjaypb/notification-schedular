# notification-schedular
Schedular application which notify user temperature update

This application trigger every hour.

It will first call /getAllNotificationDetails endpoint of weather-update-notification API to fetch all present notification details.

Then It will call open weather API for city provided in notification details.

If temperature received from open weather API is matching or greater than provided temperature in notification detail then it will trigger notification on provided channel.
