package main.ui;

/**
 * Wrapper class representing a tuple latitude/longitude in the geographic
 * coordinate system
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER Léo, THOLOT Cassandre
 */
public class GeographicCoordinate {
    double latitude;
    double longitude;

    /**
     * Create a tuple (latitude;longitude), in the geographic coordinate system,
     * as an object.
     * 
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public GeographicCoordinate(double latitude, double longitude) {
	this.latitude = latitude;
	this.longitude = longitude;
    }

    /**
     * Getter of the latitude attribute.
     * 
     * @return latitude.
     */
    public double getLatitude() {
	return latitude;
    }

    /**
     * Setter of the latitude attribute.
     * 
     * @param latitude the new latitude to set.
     */
    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    /**
     * Getter of the longitude attribute.
     * 
     * @return longitude.
     */
    public double getLongitude() {
	return longitude;
    }

    /**
     * Setter of the longitude attribute.
     * 
     * @param longitude the new longitude to set.
     */
    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }
}
