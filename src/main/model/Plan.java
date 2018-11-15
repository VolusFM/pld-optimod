package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;
import main.ui.GeographicCoordinate;

/**
 * Plan represents a plan of a city or city part, and consists in a set of
 * intersections (from which we will get the outcoming sections)
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class Plan {

    private double HIGH = Double.MAX_VALUE;
    private HashMap<Long, Intersection> graph;

    /**
     * Initialize plan.
     */
    public Plan() {
	this.graph = new HashMap<>();
    }

    /**
     * Add an intersection to the plan.
     * 
     * @param toAdd is the intersection to add.
     */
    protected void addIntersection(Intersection toAdd) {
	this.graph.put(toAdd.getId(), toAdd);
    }

    /**
     * Add a section to the plan.
     * 
     * @param toAdd is the section to add.
     */
    protected void addSection(Section toAdd) {
	long idIntersection = toAdd.getStart().getId();
	this.graph.get(idIntersection).addOutcomingSection(toAdd);
    }

    /**
     * Run Dijkstra's algorithm on graph. In the result, if distance =
     * Double.MAX_VALUE, the associated Intersection doesn't exist or can't be
     * reach.
     * 
     * @param sourceIntersection is the intersection from which we run the
     *                               algorithm.
     * @return a pair with as the first member distances, and as the second member
     *         predecessors.
     */
    protected Pair<HashMap<Long, Double>, HashMap<Long, Long>> dijkstra(Intersection sourceIntersection) {
	List<Long> settledId = new ArrayList<>();
	List<Long> unSettledId = new ArrayList<>();

	// intersection id -> distance
	HashMap<Long, Double> distances = new HashMap<>();
	// intersection id -> predecessor id
	HashMap<Long, Long> predecessors = new HashMap<>();

	// Initialization
	for (Long id : this.graph.keySet()) {
	    distances.put(id, this.HIGH);
	    predecessors.put(id, sourceIntersection.getId());
	}
	unSettledId.add(sourceIntersection.getId());
	distances.put(sourceIntersection.getId(), 0.);
	while (!unSettledId.isEmpty()) {
	    long idEvaluationIntersectionId = findIntersectionWithLowestDistance(distances, unSettledId);
	    unSettledId.remove(unSettledId.indexOf(idEvaluationIntersectionId));
	    settledId.add(idEvaluationIntersectionId);
	    Intersection evaluationIntersection = this.graph.get(idEvaluationIntersectionId);
	    List<Section> neighbours = evaluationIntersection.getOutcomingSections();
	    for (Section neighbour : neighbours) {
		long destinationId = neighbour.getEnd().getId();
		if (!settledId.contains(destinationId)) {
		    double newDistance = distances.get(idEvaluationIntersectionId) + neighbour.getLength();
		    if (newDistance < distances.get(neighbour.getEnd().getId())) {
			distances.put(neighbour.getEnd().getId(), newDistance);
			predecessors.put(neighbour.getEnd().getId(), idEvaluationIntersectionId);
			unSettledId.add(neighbour.getEnd().getId());
		    }
		}
	    }
	}
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = new Pair<>(distances, predecessors);
	return result;
    }

    /**
     * Find the intersection with the lowest distance in distances for ids in
     * unSettledIds.
     * 
     * @param distances    is the distances to each intersection.
     * @param unSettledIds is the list of candidate intersections' ids.
     * @return Long, the id of the closest intersection.
     */
    private long findIntersectionWithLowestDistance(HashMap<Long, Double> distances, List<Long> unSettledIds) {
	double lowestDistance = this.HIGH;
	long idIntersectionWithLowestDistance = unSettledIds.get(0);

	for (long id : unSettledIds) {
	    if (distances.get(id) < lowestDistance) {
		lowestDistance = distances.get(id);
		idIntersectionWithLowestDistance = id;
	    }
	}
	return idIntersectionWithLowestDistance;
    }

    /**
     * Find the Intersection closest to where the user clicked.
     * 
     * @param latitude  is the latitude of the point where the user clicked.
     * @param longitude is the longitude of the point where the user clicked.
     * @return Intersection, the closest Intersection.
     */
    protected Intersection findClosestIntersection(double latitude, double longitude) {
	GeographicCoordinate reference = new GeographicCoordinate(latitude, longitude);
	double minDistance;
	double currentDistance;
	Intersection closest;
	Intersection current;

	Iterator<Intersection> intersectionIterator = this.graph.values().iterator();
	closest = intersectionIterator.next();
	minDistance = distanceBetween(reference, new GeographicCoordinate(closest.getLat(), closest.getLon()));

	while (intersectionIterator.hasNext()) {
	    current = intersectionIterator.next();
	    currentDistance = distanceBetween(reference, new GeographicCoordinate(current.getLat(), current.getLon()));

	    if (currentDistance < minDistance) {
		minDistance = currentDistance;
		closest = current;
	    }
	}
	return closest;
    }

    /**
     * Find the Section closest to where the user clicked.
     * 
     * @param latitude  is the latitude of the point where the user clicked.
     * @param longitude is the longitude of the point where the user clicked.
     * @return Section, the closest Section.
     */
    protected Section findClosestSection(double latitude, double longitude) {
	GeographicCoordinate reference = new GeographicCoordinate(latitude, longitude);
	double minDistance;
	double currentDistance;
	Section closest;
	Section current;
	Iterator<Section> sectionsIterator = listAllSections().iterator();
	closest = sectionsIterator.next();
	Intersection start;
	Intersection end;
	start = getIntersectionById(closest.getStart().getId());
	end = getIntersectionById(closest.getEnd().getId());
	minDistance = distanceBetweenLine(reference, new GeographicCoordinate(start.getLat(), start.getLon()),
		new GeographicCoordinate(end.getLat(), end.getLon()));

	while (sectionsIterator.hasNext()) {
	    current = sectionsIterator.next();
	    start = getIntersectionById(current.getStart().getId());
	    end = getIntersectionById(current.getEnd().getId());
	    currentDistance = distanceBetweenLine(reference, new GeographicCoordinate(start.getLat(), start.getLon()),
		    new GeographicCoordinate(end.getLat(), end.getLon()));
	    if (currentDistance < minDistance) {
		minDistance = currentDistance;
		closest = current;
	    }
	}
	return closest;
    }

    /**
     * Get the distance between two points on the plan.
     * 
     * @param coordinate        contains the coordinates of the first point.
     * @param anotherCoordinate contains the coordinates of the second point.
     * @return double, the distance between the two points.
     */
    private static double distanceBetween(GeographicCoordinate coordinate, GeographicCoordinate anotherCoordinate) {
	return Math.sqrt((coordinate.getLatitude() - anotherCoordinate.getLatitude())
		* (coordinate.getLatitude() - anotherCoordinate.getLatitude())
		+ (coordinate.getLongitude() - anotherCoordinate.getLongitude())
			* (coordinate.getLongitude() - anotherCoordinate.getLongitude()));
    }

    /**
     * Get the distance between a point and a line.
     * 
     * @param point            is the point to evaluate.
     * @param oneExtremity     is the first extremity of the line.
     * @param anotherExtremity is the second extremity of the line.
     * @return Double, the distance between the line and the point.
     */
    private static double distanceBetweenLine(GeographicCoordinate point, GeographicCoordinate oneExtremity,
	    GeographicCoordinate anotherExtremity) {
	return Math.abs(distanceBetween(point, oneExtremity) + distanceBetween(point, anotherExtremity)
		- distanceBetween(oneExtremity, anotherExtremity));
    }

    /**
     * Get all sections in the plan.
     * 
     * @return List, a list of all sections in the plan.
     */
    private List<Section> listAllSections() {
	List<Section> sections = new ArrayList<Section>();
	for (Intersection intersection : this.graph.values()) {
	    sections.addAll(intersection.getOutcomingSections());
	}
	return sections;
    }

    /**
     * Retrieve an Intersection using its id.
     * 
     * @param id is the id of the Intersection we are looking for.
     * @return Intersection, the Intersection mapped by id in graph.
     */
    public Intersection getIntersectionById(long id) {
	return this.graph.get(id);
    }

    /**
     * Getter for the graph.
     * 
     * @return HashMap<Long, Intersection>, the graph of the plan.
     */
    public HashMap<Long, Intersection> getGraph() {
	return this.graph;
    }
    
    
    @Override
    public String toString() {
        return this.graph.toString();
    }
}
