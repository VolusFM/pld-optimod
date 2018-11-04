package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.util.Pair;

public class Plan {
	
	private double HIGH = Double.MAX_VALUE;
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
		for (long key : graph.keySet())
		{
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();
			
		}
	}

	public HashMap<Long,Intersection> getGraph() {
		return graph;
	}
	
	/**
	 * execute a Dijkstra algorithm on graph. In the result, if distance = Double.MAX_VALUE, the associated Intersection 
	 * doesn't exist or can't be reach.
	 * @param sourceIntersection
	 * @return a pair with as the first members distances, and as a second members predecessors. 
	 */
	public Pair<HashMap<Long, Double>, HashMap<Long, Long>> Dijkstra(Intersection sourceIntersection) {
		List<Long> settledId = new ArrayList<>();
		List<Long> unSettledId = new ArrayList<>();
/*
		
		//to make sure that the two table are wide enough.
		//FIXME find another way to do it 
		
		int higherId = getHigherIntersectionId();
		double[] distances = new double [higherId+1]; // XXX : OutOfMemory on allocation
		long[] predecessors = new long [higherId+1];
		// FIXME : doesn't this break if higherId > Integer.MAX_VALUE ? (which is the case), 
		// as indexes can't be bigger than an int and ids are bigger than an int (which is why we chose longs)
*/
		
		// intersection id -> distance
		HashMap<Long, Double> distances = new HashMap<>();
		// intersection id -> predecessor id
		HashMap<Long, Long> predecessors = new HashMap<>();

		
		
		
		
		
		//Initialization
/*
//TODO find a way for distances and predecessors to not be as empty and/or find a way to initialize predecessor at something more meaningful
		for (int i=0; i<higherId+1; i++) {
			distances[i]=HIGH;
			predecessors[i]=sourceIntersection.getId();
		}
*/
		for (Long id : graph.keySet()) {
			distances.put(id, HIGH);
			predecessors.put(id, sourceIntersection.getId());
		}
		
		unSettledId.add(sourceIntersection.getId());
		
/*		
		distances[sourceIntersection.getId()] = 0;
*/
		distances.put(sourceIntersection.getId(), 0.); // 0. because 0 is not a double

/*		
		//Algorithm
		while(!unSettledId.isEmpty()) {
			int idEvaluationIntersectionId = findIntersectionWithLowestDistance(distances, unSettledId);
			unSettledId.remove(unSettledId.indexOf(idEvaluationIntersectionId) );
			settledId.add(idEvaluationIntersectionId);
			Intersection evaluationIntersection = graph.get(idEvaluationIntersectionId);
			List<Section> neighbours = evaluationIntersection.getOutcomingSections();
			for (Section neighbour : neighbours) {
				int destinationId = neighbour.getIdEndIntersection();
				if (!settledId.contains(destinationId)) {
					double newDistance = distances[idEvaluationIntersectionId] + neighbour.getLength();
					if (newDistance < distances[neighbour.getIdEndIntersection()]) {
						distances[neighbour.getIdEndIntersection()]=newDistance;
						predecessors[neighbour.getIdEndIntersection()]=idEvaluationIntersectionId;
						unSettledId.add(neighbour.getIdEndIntersection());
					}
				}
			}
		}
*/
		
		while(!unSettledId.isEmpty()) {
			long idEvaluationIntersectionId = findIntersectionWithLowestDistance(distances, unSettledId);
			unSettledId.remove(unSettledId.indexOf(idEvaluationIntersectionId) );
			settledId.add(idEvaluationIntersectionId);
			Intersection evaluationIntersection = graph.get(idEvaluationIntersectionId);
			List<Section> neighbours = evaluationIntersection.getOutcomingSections();
			for (Section neighbour : neighbours) {
				long destinationId = neighbour.getIdEndIntersection();
				if (!settledId.contains(destinationId)) {
					double newDistance = distances.get(idEvaluationIntersectionId) + neighbour.getLength();
					if (newDistance < distances.get(neighbour.getIdEndIntersection())) {
						distances.put(neighbour.getIdEndIntersection(),newDistance);
						predecessors.put(neighbour.getIdEndIntersection(),idEvaluationIntersectionId);
						unSettledId.add(neighbour.getIdEndIntersection());
					}
				}
			}
		}
		
		Pair<HashMap<Long, Double>, HashMap<Long, Long>> toReturn = new Pair<>(distances, predecessors);
		return toReturn;
	}
	
	/**
	 * find the higher IntersectionId
	 */
//	private int getHigherIntersectionId() {
//		Set<Long> keys = graph.keySet();
//		Iterator<Long> it = keys.iterator();
//		int higherIntersectionId = 0;
//		while (it.hasNext()){
//		    Object key = it.next(); 
//		    if (graph.get(key).getId()>higherIntersectionId) {
//		    	higherIntersectionId=graph.get(key).getId();
//		    }
//		}
//		return higherIntersectionId;
//	}
	
	/**
	 * find the lowest distances in distances for key in unSettledIntersectionId
	 * @param distances
	 * @param unSettledIntersectionId
	 */
	/*
	private int findIntersectionWithLowestDistance(double [] distances, List<Integer> unSettledIntersectionId ) {
		double lowestDistance = HIGH;
		int idIntersectionWithLowestDistance = unSettledIntersectionId.get(0);
		for (Integer id : unSettledIntersectionId) {
			if(distances[id]<lowestDistance){
				lowestDistance=distances[id];
				idIntersectionWithLowestDistance = id;
			}
		}
		return idIntersectionWithLowestDistance;
	}*/
	private long findIntersectionWithLowestDistance(HashMap<Long, Double> distances, List<Long> unSettledId ) {
		double lowestDistance = HIGH;
		long idIntersectionWithLowestDistance = unSettledId.get(0);

		for (long id : unSettledId) {
			if(distances.get(id)<lowestDistance){
				lowestDistance=distances.get(id);
				idIntersectionWithLowestDistance = id;
			}
		}
		return idIntersectionWithLowestDistance;
	}
	
	
}
