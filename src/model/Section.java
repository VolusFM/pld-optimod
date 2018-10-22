package model;

public class Section {

	private String streetName;
	private int length;
	private Intersection startIntersection;
	private Intersection endIntersection;
	
	public int getIdStartIntersection(){
		return startIntersection.getId();
	}
}
