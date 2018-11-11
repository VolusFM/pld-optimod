package main.model;

import java.util.Calendar;
import java.util.Observable;

public class Delivery extends Observable {

    private int duration;
    private Calendar hour;
    private Intersection address;

    public Delivery(int duration, Intersection address) {
	this.duration = duration;
	this.address = address;
    }

    public int getDuration() {
	return duration;
    }

    public Calendar getHour() {
	return hour;
    }

    public void setHour(Calendar hour) {
	this.hour = hour;
    }

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
