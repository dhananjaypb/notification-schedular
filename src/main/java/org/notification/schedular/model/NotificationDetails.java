package org.notification.schedular.model;

/*
 * Entity class to map response and request.
 */
public class NotificationDetails {

	private String name;
	private String city;
	private String temp;
	private String channel;

	public NotificationDetails() {
	}

	public NotificationDetails(String name, String city, String temp, String channel) {
		this.name = name;
		this.city = city;
		this.temp = temp;
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "NotificationDetails{" + "name='" + name + '\'' + ", city='" + city + '\'' + ", temp='" + temp + '\''
				+ ", channel='" + channel + '\'' + '}';
	}
}
