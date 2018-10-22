package model;

import java.util.HashMap;

public class Plan {
	
	private HashMap<Integer,Intersection> graph;
	
	public void addIntersection(Intersection toAdd){
		graph.put(toAdd.getId(), toAdd);
	}
	
	public void addSection(Section toAdd){
		int idIntersection = toAdd.getIdStartIntersection();
		graph.get(idIntersection).addOutcomingSection(toAdd);
	}
	
	public Intersection getIntersectionById(int idIntersection){
		return graph.get(idIntersection);
	}
	
	public Plan(){
		graph = new HashMap<Integer,Intersection>();
	}
	
}
