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

	public void addIntersection(Intersection toAdd) {
		graph.put(toAdd.getId(), toAdd);
	}

	public void addSection(Section toAdd) {
		long idIntersection = toAdd.getIdStartIntersection();
		graph.get(idIntersection).addOutcomingSection(toAdd);
	}

	public Intersection getIntersectionById(long idDeparture) {
		return graph.get(idDeparture);
	}

	public Plan() {
		graph = new HashMap<>();
	}

	public void print() {
		for (long key : graph.keySet()) {
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();

		}
	}

	public HashMap<Long, Intersection> getGraph() {
		return graph;
	}

	/**
	 * Run Dijkstra's algorithm on graph. In the result, if distance =
	 * Double.MAX_VALUE, the associated Intersection doesn't exist or can't be
	 * reach.
	 * 
	 * @param sourceIntersection
	 * @return a pair with as the first member distances, and as the second member
	 *         predecessors.
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

		distances.put(sourceIntersection.getId(), 0.); // 0. because 0 is not a double

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

		Pair<HashMap<Long, Double>, HashMap<Long, Long>> toReturn = new Pair<>(distances, predecessors);
		return toReturn;
	}

	/**
	 * find the lowest distances in distances for key in unSettledIntersectionId
	 * 
	 * @param distances
	 * @param unSettledIntersectionId
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
	
	
	public Intersection findClosestIntersection(double latitude, double longitude) {
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
		return closest;
	}

	
	private double distanceBetween(GeographicCoordinate coordinate, GeographicCoordinate anotherCoordinate) {
		return (Math.sqrt((coordinate.getLatitude() - anotherCoordinate.getLatitude()) * (coordinate.getLatitude() - anotherCoordinate.getLatitude()) + 
				(coordinate.getLongitude() - anotherCoordinate.getLongitude()) * (coordinate.getLongitude() - anotherCoordinate.getLongitude())));
	}
}
