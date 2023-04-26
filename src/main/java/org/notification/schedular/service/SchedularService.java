package org.notification.schedular.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.notification.schedular.model.NotificationDetails;
import org.notification.schedular.model.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/*
 * This service class for schedular.
 */
@Service
public class SchedularService {

	private static Logger logger = LogManager.getLogger(SchedularService.class);

	private static final String URL = "http://localhost:8081/weather-update-notification/getAllNotificationDetails";

	private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

	private static String APP_ID = "&appid=8eabac3331febf1b6ec62bba3147a138";

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * Using this method it fetch all notification details proceed to send
	 * notifiaction.
	 */
	public void checkAndTriggerNotification() {
		logger.info("SchedularService : checkAndTriggerNotification()");
		List<NotificationDetails> notificationDetails = getNotificationDetails();
		if (!CollectionUtils.isEmpty((notificationDetails))) {
			logger.info("SchedularService : Number of notification details received " + notificationDetails.size());
			checkTempforNotification(notificationDetails);
		}
	}

	/*
	 * It check temperature for details which received from weather update
	 * notification API. Then it call to open weather API and check it is above
	 * or equal to provided temperature for city If it is it is above or equal
	 * to provided temperature for city then trigger to send notification If any
	 * error occur while fetching details it will @throw Exception
	 */
	protected void checkTempforNotification(List<NotificationDetails> notificationDetails) {
		logger.info("SchedularService : checkTempforNotification()");
		for (NotificationDetails notificationDetail : notificationDetails) {
			WeatherApiResponse weatherResponse = null;
			try {
				weatherResponse = restTemplate.getForObject(
						StringUtils.join(WEATHER_URL, notificationDetail.getCity(), APP_ID), WeatherApiResponse.class);
			} catch (Exception exception) {
				logger.error("SchedularService : Error occured while fetching weather details. Error message "
						+ exception.getMessage());
			}
			if (Objects.nonNull(weatherResponse)) {
				Float temp = convertkelvinToCelcious(weatherResponse.getMain().getTemp());
				logger.info("SchedularService : Temperature received from API " + temp);
				if (temp >= Float.valueOf(notificationDetail.getTemp())) {
					sendNotification(notificationDetail, String.valueOf(temp));
				}
			}
		}
	}

	/*
	 * This method help to send notification
	 */
	protected void sendNotification(NotificationDetails notificationDetails, String temp) {
		logger.info("Notification sent to " + notificationDetails.getChannel() + ", message = Temperature of "
				+ notificationDetails.getCity() + " city is reached to " + temp);
	}

	/*
	 * It fetch all notification details from weather update notification API If
	 * any error occur while fetching details it will @throw Exception
	 */
	protected List<NotificationDetails> getNotificationDetails() {
		logger.info("SchedularService : getNotificationDetails()");
		try {
			ResponseEntity<List<NotificationDetails>> notificationDetails = restTemplate.exchange(URL, HttpMethod.GET,
					null, new ParameterizedTypeReference<List<NotificationDetails>>() {
					});
			if (Objects.isNull(notificationDetails)) {
				logger.error("SchedularService : Error occured while fetching notification details.");
				throw new Exception("SchedularService : No details received after fetching notification details.");
			}
			logger.info("SchedularService : response from notfication detail API is "
					+ notificationDetails.getStatusCode());
			return notificationDetails.getBody();
		} catch (Exception exception) {
			logger.error("SchedularService : Error occured while fetching notification details. Error message "
					+ exception.getMessage());
		}
		return null;
	}

	/*
	 * We receive temperature in kelvin from open weather API Using this method
	 * we convert that to celsius
	 */
	protected Float convertkelvinToCelcious(String temp) {
		logger.info("SchedularService : convertkelvinToCelcious()");
		logger.info("SchedularService : temp received " + temp);
		return Float.valueOf(temp) - Float.valueOf("273.15");
	}

}
