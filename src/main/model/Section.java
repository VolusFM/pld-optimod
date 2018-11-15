package main.model;

/**
 * Section represents a part of a street. It has a positive length, is delimited
 * by two intersections and corresponds to a street.
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class Section {

    private String streetName;
    private double length;
    private Intersection start;
    private Intersection end;

    /**
     * Create a Section.
     * 
     * @param start      is the start of the Section.
     * @param end        is the end of the Section.
     * @param length     is the length of the section.
     * @param streetName is the name of the street the section corresponds to.
     */
    public Section(Intersection start, Intersection end, double length, String streetName) {
	this.streetName = streetName;
	this.length = length;
	this.start = start;
	this.end = end;
    }

    /**
     * Getter for length.
     * 
     * @return Double, the length of the Section.
     */
    public double getLength() {
	return this.length;
    }

    /**
     * Getter for the start intersection.
     * 
     * @return Intersection, the start of the section.
     */
    public Intersection getStart() {
	return this.start;
    }

    /**
     * Getter for the end intersection.
     * 
     * @return Intersection, the end of the section.
     */
    public Intersection getEnd() {
	return this.end;
    }

    /**
     * Getter for the street name.
     * 
     * @return String, the name of the street the section corresponds to.
     */
    public String getStreetName() {
	return this.streetName;
    }

    @Override
    public String toString() {
	return "Section from " + getStart().getId() + " to " + getEnd().getId();
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
	if (this.end == null) {
	    if (other.end != null)
		return false;
	} else if (!this.end.equals(other.end))
	    return false;
	if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length))
	    return false;
	if (this.start == null) {
	    if (other.start != null)
		return false;
	} else if (!this.start.equals(other.start))
	    return false;
	if (this.streetName == null) {
	    if (other.streetName != null)
		return false;
	} else if (!this.streetName.equals(other.streetName))
	    return false;
	return true;
    }

}
