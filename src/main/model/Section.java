package main.model;

public class Section {

	private String streetName;
	private double length;
	private Intersection start;
	private Intersection end;

	public Section(Intersection start, Intersection end, double length, String streetName) {
		this.streetName = streetName;
		this.length = length;
		this.start = start;
		this.end = end;
	}

	public double getLength() {
		return length;
	}

	public long getIdStartIntersection() {
		return start.getId();
	}

	public long getIdEndIntersection() {
		return end.getId();
	}

	public Intersection getStart() {
		return start;
	}

	public Intersection getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "Section from " + getIdStartIntersection() + " to " + getIdEndIntersection();
	}

	public String getStreetName() {
		return streetName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		return true;
	}

}
