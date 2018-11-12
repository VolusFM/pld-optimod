package main.ui;

/**
 * Wrapper class representing a tuple latitude/longitude, in the geographic
 * coordinate system
 */
public class GeographicCoordinate {
	double latitude;
	double longitude;

	public GeographicCoordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
