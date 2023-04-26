package org.notification.schedular.model;

public class Wind {

	private String speed;
	private String deg;
	private String gust;

	public Wind() {
		super();
	}

	public Wind(String speed, String deg, String gust) {
		super();
		this.speed = speed;
		this.deg = deg;
		this.gust = gust;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDeg() {
		return deg;
	}

	public void setDeg(String deg) {
		this.deg = deg;
	}

	public String getGust() {
		return gust;
	}

	public void setGust(String gust) {
		this.gust = gust;
	}

	@Override
	public String toString() {
		return "Wind [speed=" + speed + ", deg=" + deg + ", gust=" + gust + "]";
	}

}
