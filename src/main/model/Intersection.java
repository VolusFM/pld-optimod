package main.model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {

	private long id;
	private double lat;
	private double lon;
	private List<Section> outcomingSections;

	public void addOutcomingSection(Section outcomingSection) {
		// TODO what if we add the same section twice ?
		outcomingSections.add(outcomingSection);
	}

	public long getId() {
		return id;
	}

	public Intersection(long id, double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
		this.id = id;
		outcomingSections = new ArrayList<>();
	}

	public void print() {
		for (Section s : outcomingSections)
			s.print();
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public List<Section> getOutcomingSections() {
		return outcomingSections;
	}
}
