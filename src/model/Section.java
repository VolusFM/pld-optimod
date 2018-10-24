package model;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;


public class Section {

	private String streetName;
	private double length;
	private Intersection start;
	private Intersection end;
	
	public long getIdStartIntersection(){
		return start.getId();
	}

	public Section(Intersection start, Intersection end, double length, String streetName) {
		this.streetName = streetName;
		this.length = length;
		this.start = start;
		this.end = end;
	}

	public void print() {
		System.out.println(streetName);	
	}

	public double getLength() {
		return length;
	}

	public Intersection getStart() {
		return start;
	}

	public Intersection getEnd() {
		return end;
	}	
	
}
