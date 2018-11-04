package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import model.tsp.TSP1;
import model.tsp.TemplateTSP;

public class TourCalculator {

	private TourFactory tourFactory;
	private Plan map;
	private List<Delivery> deliveries;
	
	private Delivery depot; // XXX
	
	/* Unique instance */
	private static TourCalculator instance = null;

	
	
	/* TSP related fields */
	private double[][] costTSP;
	private int nodesCount;
	private int[] delay;
	
	private int calculationTimeLimitMs = 10000;
	
	
	
	
	private TourCalculator() {
		tourFactory = TourFactory.getInstance();
		deliveries = new ArrayList<>();
	}

	@Deprecated
	public static TourCalculator init(Plan map, List<Delivery> deliveries, Delivery depot) {
		if (instance == null) getInstance();
		
		instance.deliveries = deliveries;
		instance.map = map;
		instance.depot = depot;
		return instance;
	}

	public static TourCalculator getInstance() {
		if (instance == null) {
			instance = new TourCalculator();
		}
		return instance;
	}
	
	public void addDelivery(Delivery d) {
		this.deliveries.add(d);
	}
	
	public void print() {
		for (Delivery d : deliveries)
		{
			System.out.println("got delivery with intersection id :" + d.getAddress().getId());
			
		}
	}
	
	public void setDepot(Delivery depot) {
		this.depot = depot;
	}
	
	public void setMap(Plan map) {
		this.map = map;
	}
	
	
	
	public void calculateTours(int deliveryMenCount) {
		// FIXME : for now, assumes a deleveryMenCount of 1
		// FIXME : this branch doesn't take into account the object 'Step'

		/* Creates the sub-graph */
		createGraph();

		/* Solves TSP within the sub-graph */
		resolveTSP();
		
		/* Create tours from the result of the TSP, and add them to the 
		 * model */
		createTours();
	}

	


	/**
	 * Creates the graph to be used with TSP algorithm
	 * 
	 * It uses the plan and all the deliveries, as well as the depot
	 * XXX : how do we know which delivery is the depot ? (-> added the depot as 
	 * an attribute)
	 * 
	 * It creates a sub-graph, containing only the useful nodes (deliveries and depot), 
	 * and the arrows corresopnd to the shortest path found between the 2 nodes in the
	 * main graph
	 * 
	 * It assumes the ordering of deliveries remain the same (i.e. the list 
	 * deliveries will not be modified)
	 * And it expect that the depot will always be the first in both list (this 
	 * is actually a pre-condution in the TSP algotithm)
	 */
	private void createGraph() {
		/* Initialization */
		nodesCount = 1 + deliveries.size(); // depot as first + deliveries
		
		delay = new int[nodesCount];
		delay[0] = 0; // no delay in the depot
		for (int i = 0; i < deliveries.size(); i++) {
			delay[i+1] = deliveries.get(i).getDuration();
		}
		
		costTSP = new double[nodesCount][nodesCount];

		
		/* Calculation of costs with Dijkstra */
		// First line of matrix is depot; other lines are the delivery points
		costTSP[0] = dijkstraHelper(depot.getAddress());
		for (int i = 0; i < deliveries.size(); i++) {
			costTSP[i+1] = dijkstraHelper(deliveries.get(i).getAddress());
		}
	}
	

	/**
	 * Disjkstra helper function, create the useful row for TSP cost matrix based
	 * on the result of the algorithm
	 */
	private double[] dijkstraHelper(Intersection source) {
		Pair<double[], long[]> result = map.Dijkstra(source);
		
		double[] cost = result.getKey();
		// FIXME : use the predecessors for something here -> create the Steps
		
		/* "Header" of the list : the ids of the nodes to use in the correct order */
		long[] idsList = new long[nodesCount];		
		idsList[0] = depot.getAddress().getId();
		for (int i = 0; i < deliveries.size(); i++) {
			idsList[i+1] = deliveries.get(i).getAddress().getId();
		}
		
		/* We use the header to construct the cost line */
		double[] costResult = new double[nodesCount];
		for (int i = 0; i < nodesCount; i++) {
			costResult[i] = cost[(int) idsList[i]];
		}	
		
		/* And we return it */
		return costResult;
	}
	
	
	
	
	/**
	 * Wrapper function around TSP resolution, delegate responsability to TSP.
	 * Execute the TSP algorithm, extract the needed information to create the tours
	 */
	private void resolveTSP() {
		TemplateTSP tsp = new TSP1();
		tsp.chercherEtAfficherMeilleureSolution(calculationTimeLimitMs, nodesCount, costTSP, delay);
	}

	
	
	private void createTours() {
		// TODO : when merging with other branches
	}
	
}
