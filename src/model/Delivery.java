package model;

import java.util.Calendar;
import java.util.Observable;

public class Delivery extends Observable {

	private int duration;
	//private Date hour;
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
}
