package main.model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class Cluster {
	private List<Intersection> intersections;
	private Pair<Double,Double> centroid;
	
	/**
	 * Create a cluster.
	 * @param intersections
	 */
	public Cluster(Pair<Double,Double> centroid){
		this.centroid = centroid;
		intersections = new ArrayList<Intersection>();
	}
	
	public void addIntersection(Intersection intersection){
		this.intersections.add(intersection);
	}
	
	/**
	 * 
	 */
	public void reinitializeClusters() {
		intersections = new ArrayList<Intersection>();
	}
	/**
	 * centroid getter
	 * @return
	 */
	public Pair<Double, Double> getCentroid() {
		return centroid;
	}
	
	/**
	 * intersections s getter
	 * @return
	 */
	public List<Intersection> getIntersections() {
		return intersections;
	}
	
	/**
	 * centroid s setter
	 * @param centroid
	 */
	public void setCentroid(Pair<Double, Double> centroid) {
		this.centroid = centroid;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String cluster = "Centroid : x = " + centroid.getKey().toString() + " y =" + centroid.getValue().toString() +"\n\r";
		for (Intersection intersection : intersections) {
			cluster += intersection.toString() +"\n\r";
		}
		return cluster;
	}
}
