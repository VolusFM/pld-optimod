package main.model;

import java.util.Calendar;

/**
 * Delivery either represents a delivery OR a depot (duration equal to zero),
 * which is the beginning and end of a Tour.
 *
 */
public class Delivery {

    private int duration;
    private Calendar hour;
    private Intersection address;

    /**
     * Create a new Delivery.
     * 
     * @param duration
     *            is the duration of the delivery.
     * @param address
     *            if the Intersection where the delivery is supposed to happen.
     */
    public Delivery(int duration, Intersection address) {
	this.duration = duration;
	this.address = address;
    }

    /**
     * Getter for the duration the delivery.
     * 
     * @return Integer, the duration of the delivery.
     */
    public int getDuration() {
	return duration;
    }

    /**
     * Getter for the hour of the delivery.
     * 
     * @return Calendar, a calendar representing the hour of the delivery.
     */
    public Calendar getHour() {
	return hour;
    }

    /**
     * Setter for the hour of the delivery.
     * 
     * @param hour
     *            is the hour to set for the delivery.
     */
    public void setHour(Calendar hour) {
	this.hour = hour;
    }

    /**
     * Getter for the address of the delivery.
     * 
     * @return Intersection, the address of the delivery.
     */
    public Intersection getAddress() {
	return address;
    }

    @Override
    public String toString() {
	return "D at " + address;
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
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	// if (duration != other.duration)
	// return false;
	// if (hour == null) {
	// if (other.hour != null)
	// return false;
	// } else if (!hour.equals(other.hour))
	// return false;
	return true;
    }

}
