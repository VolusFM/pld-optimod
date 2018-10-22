package model;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;


public class Section {

	private String streetName;
	private double length;
	private Intersection startIntersection;
	private Intersection endIntersection;
	
	public long getIdStartIntersection(){
		return startIntersection.getId();
	}

	public Section(Intersection startIntersection, Intersection endIntersection, double length, String streetName) {
		this.streetName = streetName;
		this.length = length;
		this.startIntersection = startIntersection;
		this.endIntersection = endIntersection;
	}

	public void print() {
		System.out.println(streetName);	
	}
	
	
}
