package org.notification.schedular.service;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.notification.schedular.model.Main;
import org.notification.schedular.model.NotificationDetails;
import org.notification.schedular.model.WeatherApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class SchedularServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Spy
	@InjectMocks
	private SchedularService service = new SchedularService();

	@Test
	public void shouldcheckAndTriggerNotification() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "20", "test");

		Mockito.doReturn(Arrays.asList(notificationDetails)).when(service).getNotificationDetails();
		Mockito.doNothing().when(service).checkTempforNotification(Arrays.asList(notificationDetails));
		service.checkAndTriggerNotification();
		Mockito.verify(service).checkTempforNotification(Arrays.asList(notificationDetails));
	}

	@Test
	public void shouldcheckAndTriggerNotificationWhenNotificationDetailsEmpty() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "20", "test");

		Mockito.doReturn(Arrays.asList()).when(service).getNotificationDetails();
		service.checkAndTriggerNotification();
		Mockito.verify(service, Mockito.never()).checkTempforNotification(Arrays.asList(notificationDetails));
	}

	@Test
	public void shouldcheckTempforNotification() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "18", "test");

		WeatherApiResponse response = getWeatherApiResponse();
		Mockito.doReturn(response).when(restTemplate).getForObject(
				StringUtils.join("https://api.openweathermap.org/data/2.5/weather?q=", notificationDetails.getCity(), "&appid=8eabac3331febf1b6ec62bba3147a138"),
				WeatherApiResponse.class);

		service.checkTempforNotification(Arrays.asList(notificationDetails));
		Mockito.verify(service).convertkelvinToCelcious(response.getMain().getTemp());
		Mockito.verify(service).sendNotification(Mockito.any(), Mockito.anyString());
	}

	@Test
	public void shouldNotcheckTempforNotification() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "20", "test");

		WeatherApiResponse response = getWeatherApiResponse();
		Mockito.doReturn(null).when(restTemplate).getForObject(
				StringUtils.join(null, notificationDetails.getCity(), "&appid=8eabac3331febf1b6ec62bba3147a138"),
				WeatherApiResponse.class);

		service.checkTempforNotification(Arrays.asList(notificationDetails));
		Mockito.verify(service, Mockito.never()).convertkelvinToCelcious(response.getMain().getTemp());
		Mockito.verify(service, Mockito.never()).sendNotification(Mockito.any(), Mockito.anyString());
	}

	@Test
	public void shouldGetNotificationDetails() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "20", "test");
		ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);

		Mockito.doReturn(responseEntity).when(restTemplate).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
				Mockito.any(HttpEntity.class), Mockito.any(ParameterizedTypeReference.class));
		Mockito.doReturn(Arrays.asList(notificationDetails)).when(responseEntity).getBody();
		Assert.assertEquals(Arrays.asList(notificationDetails).get(0).getName(),
				service.getNotificationDetails().get(0).getName());

	}

	@Test
	public void shouldNotGetNotificationDetails() {
		NotificationDetails notificationDetails = new NotificationDetails("TestName", "test", "20", "test");
		ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);

		Mockito.doReturn(null).when(restTemplate).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
				Mockito.any(HttpEntity.class), Mockito.any(ParameterizedTypeReference.class));
		Mockito.doReturn(Arrays.asList(notificationDetails)).when(responseEntity).getBody();
		Assert.assertNull(service.getNotificationDetails());

	}

	@Test
	public void shouldConvertkelvinToCelcious() {
		Assert.assertEquals(26.850006103515625, service.convertkelvinToCelcious("300.00"), 0.0f);
	}

	private WeatherApiResponse getWeatherApiResponse() {
		Main main = new Main();
		main.setTemp("300");
		WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
		weatherApiResponse.setMain(main);
		return weatherApiResponse;
	}

}
