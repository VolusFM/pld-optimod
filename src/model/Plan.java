package model;

import java.util.HashMap;

public class Plan {
	
	private HashMap<Long,Intersection> graph;
	
	public void addIntersection(Intersection toAdd){
		graph.put(toAdd.getId(), toAdd);
	}
	
	public void addSection(Section toAdd){
		long idIntersection = toAdd.getIdStartIntersection();
		graph.get(idIntersection).addOutcomingSection(toAdd);
	}
	
	public Intersection getIntersectionById(long idDeparture){
		return graph.get(idDeparture);
	}
	
	public Plan(){
		graph = new HashMap<>();
	}
	
	public void print() {
		for (Long key : graph.keySet())
		{
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();
			
		}
	}
	
}
