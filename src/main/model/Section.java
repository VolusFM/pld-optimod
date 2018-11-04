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

	public void print() {
		System.out.println(streetName);
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
}
