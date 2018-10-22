package model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
	
	private int id;
	private double lat;
	private double lon;
	private List<Section> outcomingSections;
	
	public void addOutcomingSection(Section outcomingSection){
		//what if we add the same section twice ?
		outcomingSections.add(outcomingSection);
	}
	
	public int getId() {
		return id;
	}

	public Intersection(int id, double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
		this.id = id;
		outcomingSections = new ArrayList<>();
	}
	
}
