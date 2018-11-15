package main.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Delivery either represents a delivery OR a depot (duration equal to zero),
 * which is the beginning and end of a Tour.
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class Delivery {

    private int duration;
    private Calendar hour;
    private Intersection address;

    /**
     * Create a new Delivery.
     * 
     * @param duration is the duration of the delivery.
     * @param address  if the Intersection where the delivery is supposed to happen.
     */
    public Delivery(int duration, Intersection address) {
	if (duration < 0) {
	    throw new IllegalArgumentException("La durée d'une livraison doit être positive !");
	}
	this.duration = duration;
	this.address = address;
    }

    /**
     * Getter for the duration the delivery.
     * 
     * @return Integer, the duration of the delivery.
     */
    public int getDuration() {
	return this.duration;
    }

    /**
     * Getter for the hour of the delivery.
     * 
     * @return Calendar, a calendar representing the hour of the delivery.
     */
    public Calendar getHour() {
	return this.hour;
    }

    /**
     * Getter for the address of the delivery.
     * 
     * @return Intersection, the address of the delivery.
     */
    public Intersection getAddress() {
	return this.address;
    }

    /**
     * Setter for the hour of the delivery.
     * 
     * @param hour is the hour to set for the delivery.
     */
    public void setHour(Calendar hour) {
	this.hour = hour;
    }

    @Override
    public String toString() {
	if (this.hour == null) {
	    return "(" + this.address.getLat() + ";" + this.address.getLon() + ")";
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm");
	dateFormat.setTimeZone(this.hour.getTimeZone());
	return "(" + this.address.getLat() + ";" + this.address.getLon() + ") - "
		+ dateFormat.format(this.hour.getTime());
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Delivery other = (Delivery) obj;
	if (this.address == null) {
	    if (other.address != null)
		return false;
	} else if (!this.address.equals(other.address)) {
	    return false;
	}
	return true;
    }

}
