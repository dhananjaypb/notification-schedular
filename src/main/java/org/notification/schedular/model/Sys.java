package org.notification.schedular.model;

public class Sys {
	
	private String country;
	private String sunrise;
	private String sunset;
	
	public Sys() {
		super();
	}

	public Sys(String country, String sunrise, String sunset) {
		super();
		this.country = country;
		this.sunrise = sunrise;
		this.sunset = sunset;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	@Override
	public String toString() {
		return "Sys [country=" + country + ", sunrise=" + sunrise + ", sunset=" + sunset + "]";
	}
}
