package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.util.Pair;
import xml.XMLException;

import java.util.HashMap;
import java.util.Iterator;

public class Plan {
	
	private double HIGH = Double.MAX_VALUE;
	private HashMap<Integer,Intersection> graph;
	
	public void addIntersection(Intersection toAdd){
		graph.put(toAdd.getId(), toAdd);
	}
	
	public void addSection(Section toAdd){
		int idIntersection = toAdd.getIdStartIntersection();
		graph.get(idIntersection).addOutcomingSection(toAdd);
	}
	
	public Intersection getIntersectionById(int idDeparture){
		return graph.get(idDeparture);
	}
	
	public Plan(){
		graph = new HashMap<>();
	}
	
	public void print() {
		for (int key : graph.keySet())
		{
			System.out.println("got intersection " + key + " with sections :");
			graph.get(key).print();
			
		}
	}
	
	/**
	 * execute a Dijkstra algorithm on graph. In the result, if distance = Double.MAX_VALUE, the associated Intersection 
	 * doesn't exist or can't be reach.
	 * @param sourceIntersection
	 * @return a pair with as the first members distances, and as a second members predecessors. 
	 */
	public Pair<double[], long[]> Dijkstra(Intersection sourceIntersection) {
		List<Integer> settledId = new ArrayList<Integer>();
		List<Integer> unSettledId = new ArrayList<Integer>();
		//to make sure that the two table are wide enough.
		//TODO find another way to do it
		int higherId = getHigherIntersectionId();
		double [] distances = new double [higherId+1];
		long [] predecessors = new long [higherId+1];
		// FIXME : doesn't this break if higherId > Integer.MAX_VALUE ? (which is the case), 
		// as indexes can't be bigger than an int and ids are bigger than an int (which is why we chose longs)
		
		
		
		//Initialization
		//TODO find a way for distances and predecessors to not be as empty and/or find a way to initialize predecessor at something more meaningful
		for (int i=0; i<higherId+1; i++) {
			distances[i]=HIGH;
			predecessors[i]=sourceIntersection.getId();
		}
		unSettledId.add(sourceIntersection.getId());
		distances[sourceIntersection.getId()] = 0;
		
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
		
		Pair<double[], long[]> toReturn = new Pair<double[], long[]>(distances, predecessors);
		return toReturn;
	}
	
	/**
	 * find the higher IntersectionId
	 */
	private int getHigherIntersectionId() {
		Set keys = graph.keySet();
		Iterator it = keys.iterator();
		int higherIntersectionId = 0;
		while (it.hasNext()){
		    Object key = it.next(); 
		    if (graph.get(key).getId()>higherIntersectionId) {
		    	higherIntersectionId=graph.get(key).getId();
		    }
		}
		return higherIntersectionId;
	}
	
	/**
	 * find the lowest distances in distances for key in unSettledIntersectionId
	 * @param distances
	 * @param unSettledIntersectionId
	 */
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
	}
	
	
}
