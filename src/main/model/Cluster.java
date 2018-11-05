package main.model;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	List<Intersection> intersections;
	int maxNbIntersection;
	
	/**
	 * Create a cluster.
	 * @param intersections
	 */
	public Cluster(ArrayList<Intersection> intersections, int maxNbIntersection){
		this.intersections = new ArrayList<Intersection>(intersections);
		this.maxNbIntersection = maxNbIntersection;
	}
	
	public boolean addIntersection(Intersection intersection){
		if(this.intersections.size()<this.maxNbIntersection){
			this.intersections.add(intersection);
		}
		return (this.intersections.size()<this.maxNbIntersection);
	}
}
