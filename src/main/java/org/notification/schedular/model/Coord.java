package org.notification.schedular.model;

public class Coord {
	
	private String lon;
	private String lat;
	
	public Coord() {
		super();
	}

	public Coord(String lon, String lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "Coord [lon=" + lon + ", lat=" + lat + "]";
	}
}
