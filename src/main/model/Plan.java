package main.model;

import java.util.HashMap;
import java.util.Iterator;

import main.ui.PlanView.GeographicCoordinate;
import main.ui.PlanView.ScreenCoordinate;

public class Plan {

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
		for (Long key : graph.keySet()) {
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();

		}
	}

	public HashMap<Long, Intersection> getGraph() {
		return graph;
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
		
		System.out.println("Closest is id : " + closest.getId() + " with distance : " + minDistance);
		
		return closest;
	}

	
	private double distanceBetween(GeographicCoordinate coordinate, GeographicCoordinate anotherCoordinate) {
		return (Math.sqrt((coordinate.getLatitude() - anotherCoordinate.getLatitude()) * (coordinate.getLatitude() - anotherCoordinate.getLatitude()) + 
				(coordinate.getLongitude() - anotherCoordinate.getLongitude()) * (coordinate.getLongitude() - anotherCoordinate.getLongitude())));
	}
}
