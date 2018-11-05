package main.model;

import java.text.SimpleDateFormat;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
		dateFormat.setTimeZone(hour.getTimeZone());
		return "Delivery [duration=" + duration + ", hour=" + dateFormat.format(hour.getTime()) + ", address=" + address + "]";
	}
	
}
