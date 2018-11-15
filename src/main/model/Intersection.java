package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Intersection represents the intersection between two or more streets. It is
 * located by geographical coordinates (latitude and longitude) and has outgoing
 * Sections.
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class Intersection {

    private long id;
    private double lat;
    private double lon;
    private List<Section> outcomingSections;

    /**
     * Create an Intersection.
     * 
     * @param id  is the unique id of the intersection.
     * @param lat is the latitude of the intersection.
     * @param lon is the longitude of the intersection.
     */
    public Intersection(long id, double lat, double lon) {
	this.lat = lat;
	this.lon = lon;
	this.id = id;
	this.outcomingSections = new ArrayList<>();
    }

    /**
     * Add an outcoming section to the intersection.
     * 
     * @param outcomingSection is the Section to add.
     */
    public void addOutcomingSection(Section outcomingSection) {
	if (outcomingSection == null) {
	    throw new AssertionError("Added null outcoming section");
	}
	if (this.outcomingSections.indexOf(outcomingSection) == -1) {
	    this.outcomingSections.add(outcomingSection);
	}
    }

    /**
     * Getter for the id of the intersection.
     * 
     * @return Long, the id of the intersection.
     */
    public long getId() {
	return this.id;
    }

    /**
     * Getter for the latitude of the intersection.
     * 
     * @return Double, the latitude of the intersection.
     */
    public double getLat() {
	return this.lat;
    }

    /**
     * Getter for the longitude of the intersection.
     * 
     * @return Double, the longitude of the intersection.
     */
    public double getLon() {
	return this.lon;
    }

    /**
     * Getter for the sections leaving from the intersection.
     * 
     * @return List of Section, the List of outcoming sections.
     */
    public List<Section> getOutcomingSections() {
	return this.outcomingSections;
    }

    @Override
    public String toString() {
	String intersection = "intersection num " + this.id + ", lat = " + this.lat + ", long =" + this.lon;
	return intersection;
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
	if (this.id != other.id)
	    return false;
	return true;
    }

}
