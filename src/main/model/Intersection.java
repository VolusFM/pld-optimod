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

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public List<Section> getOutcomingSections() {
		return outcomingSections;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intersection other = (Intersection) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (outcomingSections == null) {
			if (other.outcomingSections != null)
				return false;
		} else if (!outcomingSections.equals(other.outcomingSections))
			return false;
		return true;
	}

}
