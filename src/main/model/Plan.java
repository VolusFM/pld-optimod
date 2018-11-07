package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;
import main.ui.PlanView.GeographicCoordinate;

public class Plan {

	private double HIGH = Double.MAX_VALUE;
	private HashMap<Long, Intersection> graph;

	/**
	 * Add an intersection to the plan.
	 * 
	 * @param toAdd
	 *            is the intersection to add.
	 */
	public void addIntersection(Intersection toAdd) {
		graph.put(toAdd.getId(), toAdd);
	}

	/**
	 * Add a section to the plan.
	 * 
	 * @param toAdd
	 *            is the section to add.
	 */
	public void addSection(Section toAdd) {
		long idIntersection = toAdd.getIdStartIntersection();
		graph.get(idIntersection).addOutcomingSection(toAdd);
	}

	/**
	 * Retrieve an Intersection using its id.
	 * 
	 * @param id
	 *            is the id of the Intersection we are looking for.
	 * @return Intersection, the Intersection mapped by id in graph.
	 */
	public Intersection getIntersectionById(long id) {
		return graph.get(id);
	}

	/**
	 * Initialize plan.
	 */
	public Plan() {
		graph = new HashMap<>();
	}

	@Deprecated
	public void print() {
		for (long key : graph.keySet()) {
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();

		}
	}

	/**
	 * Getter for graph.
	 * 
	 * @return HashMap<Long, Intersection>, the graph of the plan.
	 */
	public HashMap<Long, Intersection> getGraph() {
		return graph;
	}

	/**
	 * Run Dijkstra's algorithm on graph. In the result, if distance =
	 * Double.MAX_VALUE, the associated Intersection doesn't exist or can't be
	 * reach.
	 * 
	 * @param sourceIntersection
	 * @return a pair with as the first member distances, and as the second
	 *         member predecessors.
	 */
	public Pair<HashMap<Long, Double>, HashMap<Long, Long>> Dijkstra(Intersection sourceIntersection) {
		List<Long> settledId = new ArrayList<>();
		List<Long> unSettledId = new ArrayList<>();

		// intersection id -> distance
		HashMap<Long, Double> distances = new HashMap<>();
		// intersection id -> predecessor id
		HashMap<Long, Long> predecessors = new HashMap<>();

		// Initialization
		for (Long id : graph.keySet()) {
			distances.put(id, HIGH);
			predecessors.put(id, sourceIntersection.getId());
		}

		unSettledId.add(sourceIntersection.getId());

		distances.put(sourceIntersection.getId(), 0.); // 0. because 0 is not a
														// double

		while (!unSettledId.isEmpty()) {
			long idEvaluationIntersectionId = findIntersectionWithLowestDistance(distances, unSettledId);
			unSettledId.remove(unSettledId.indexOf(idEvaluationIntersectionId));
			settledId.add(idEvaluationIntersectionId);
			Intersection evaluationIntersection = graph.get(idEvaluationIntersectionId);
			List<Section> neighbours = evaluationIntersection.getOutcomingSections();
			for (Section neighbour : neighbours) {
				long destinationId = neighbour.getIdEndIntersection();
				if (!settledId.contains(destinationId)) {
					double newDistance = distances.get(idEvaluationIntersectionId) + neighbour.getLength();
					if (newDistance < distances.get(neighbour.getIdEndIntersection())) {
						distances.put(neighbour.getIdEndIntersection(), newDistance);
						predecessors.put(neighbour.getIdEndIntersection(), idEvaluationIntersectionId);
						unSettledId.add(neighbour.getIdEndIntersection());
					}
				}
			}
		}

		Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = new Pair<>(distances, predecessors);
		return result;
	}

	/**
	 * Find the lowest distance in distances for key in unSettledId.
	 * 
	 * @param distances
	 * @param unSettledIntersectionId
	 * @return
	 */
	private long findIntersectionWithLowestDistance(HashMap<Long, Double> distances, List<Long> unSettledId) {
		double lowestDistance = HIGH;
		long idIntersectionWithLowestDistance = unSettledId.get(0);

		for (long id : unSettledId) {
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
	 * @param latitude
	 *            is the latitude of the point where the user clicked.
	 * @param longitude
	 *            is the longitude of the point where the user clicked.
	 * @return Intersection, the closest Intersection.
	 */
	protected Intersection findClosestIntersection(double latitude, double longitude) {
		GeographicCoordinate reference = new GeographicCoordinate(latitude, longitude);
		double minDistance;
		double currentDistance;
		Intersection closest;
		Intersection current;

		Iterator<Intersection> intersectionIterator = graph.values().iterator();
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

		System.out.println("Closest is id : " + closest.getId() + " with distance : " + minDistance);

		return closest;
	}

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

		start = getIntersectionById(closest.getIdStartIntersection());
		end = getIntersectionById(closest.getIdEndIntersection());
		minDistance = distanceBetweenLine(reference, new GeographicCoordinate(start.getLat(), start.getLon()), new GeographicCoordinate(end.getLat(), end.getLon()));

		while (sectionsIterator.hasNext()) {
			current = sectionsIterator.next();
			start = getIntersectionById(current.getIdStartIntersection());
			end = getIntersectionById(current.getIdEndIntersection());

			currentDistance = distanceBetweenLine(reference, new GeographicCoordinate(start.getLat(), start.getLon()), new GeographicCoordinate(end.getLat(), end.getLon()));

			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				closest = current;
			}
		}

		System.out.println("Closest intersection is : " + closest.getStreetName() + " with distance : " + minDistance);

		return closest;
	}

	/**
	 * Get the distance between two points on the plan.
	 * 
	 * @param coordinate
	 *            contains the coordinates of the first point.
	 * @param anotherCoordinate
	 *            contains the coordinates of the second point.
	 * @return double, the distance between the two points.
	 */
	private double distanceBetween(GeographicCoordinate coordinate, GeographicCoordinate anotherCoordinate) {
		return Math.sqrt((coordinate.getLatitude() - anotherCoordinate.getLatitude()) * (coordinate.getLatitude() - anotherCoordinate.getLatitude()) + (coordinate.getLongitude() - anotherCoordinate.getLongitude()) * (coordinate.getLongitude() - anotherCoordinate.getLongitude()));
	}

	private double distanceBetweenLine(GeographicCoordinate point, GeographicCoordinate oneExtremity, GeographicCoordinate anotherExtremity) {
		return Math.abs(distanceBetween(point, oneExtremity) + distanceBetween(point, anotherExtremity) - distanceBetween(oneExtremity, anotherExtremity));
	}

	private List<Section> listAllSections() {
		List<Section> sections = new ArrayList<Section>();
		for (Intersection intersection : graph.values()) {
			sections.addAll(intersection.getOutcomingSections());
		}
		return sections;
	}
}
