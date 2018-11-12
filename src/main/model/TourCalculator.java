package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;
import main.model.tsp.TSP1;
import main.model.tsp.TemplateTSP;

/**
 * TourCalculator is a singleton class which handles the calculation of tours
 */
public class TourCalculator {

     private TourFactory tourFactory = TourFactory.getInstance();
     private Plan map = ModelInterface.getPlan();
     private List<Delivery> deliveries;

     private List<List<Delivery>> deliveriesForEachTour;

     private Delivery depot;

     /* Unique instance */
     private static TourCalculator instance = null;

     /* TSP related fields */
     private double[][] costTSP;
     private int nodesCount;
     private int[] delay;

     /* TSP related fields for multiple tours */
     private List<double[][]> costsTSPForEachTour = new ArrayList<>();
     private List<Integer> nodesCountForEachTour = new ArrayList<>();
     private List<int[]> delayForEachTour = new ArrayList<>();

     private int calculationTimeLimitMs = 1000000;

     private HashMap<Pair<Long, Long>, Step> steps;
     // XXX : I REALLY hope we don't need a specific steps instance by tour

     private int deliveryMenCount = 1;

     private TemplateTSP TSPimplementation = new TSP1();

     /**
      * Create the tour calculator.
      */
     private TourCalculator() {
	  tourFactory = TourFactory.getInstance();
	  deliveries = new ArrayList<>();
	  deliveriesForEachTour = new ArrayList<List<Delivery>>();
	  steps = new HashMap<>();
     }

     /**
      * Get the unique instance of the singleton.
      * 
      * @return TourCalculator, the unique instance of the class.
      */
     public static TourCalculator getInstance() {
	  if (instance == null) {
	       instance = new TourCalculator();
	  }
	  return instance;
     }

     /**
      * XXX see comment
      * 
      * @param d
      */
     public void addDelivery(Delivery d) {
	  // FIXME : see addDeliveryToTour
	  this.deliveries.add(d);
     }

     /**
      * Setter for the depot for the tours.
      * 
      * @param depot is the Delivery corresponding to the depot.
      */
     public void setDepot(Delivery depot) {
	  this.depot = depot;
     }

     /**
      * Setter for the map.
      * 
      * @param map is the plan for the deliveries.
      */
     public void setMap(Plan map) {
	  this.map = map;
     }

     /**
      * Setter for the delivery men count.
      * 
      * @param deliveryMenCount is the number of delivery men.
      * @throws IllegalArgumentException, if deliveryMenCount isn't strictly
      *         positive.
      */
     public void setDeliveryMenCount(int deliveryMenCount) {
	  if (deliveryMenCount <= 0) {
	       throw new IllegalArgumentException("Le nombre de livreurs doit être strictement positif.");
	  } else {
	       this.deliveryMenCount = deliveryMenCount;
	  }
     }

     /**
      * Getter of deliveries to do (excluding the depot).
      * 
      * @return List, the list of Deliveries to include in tours.
      */
     public List<Delivery> getDeliveries() {
	  return deliveries;
     }

     /**
      * Getter of the depot for the current deliveries.
      * 
      * @return the Delivery corresponding to the depot.
      */
     public Delivery getDepot() {
	  return depot;
     }

     /**
      * Find the delivery taking place at the intersection, if it exists.
      * 
      * @param intersection is the intersection where a Delivery is supposed to
      *        happen.
      * @return the corresponding Delivery if it exists, or null if it doesn't.
      */
     public Delivery findCorrespondingDelivery(Intersection intersection) {
	  for (Delivery delivery : deliveries) {
	       if (delivery.getAddress().equals(intersection)) {
		    return delivery;
	       }
	  }
	  if (depot.getAddress().equals(intersection)) {
	       return depot;
	  }
	  return null;
     }

     /**
      * Do the calculation based on the map, depot and deliveries, to create the
      * tours
      */
     public void calculateTours() {
	  /* Creates the global sub-graph, with all deliveries */
	  createGraph();

	  // TODO : do the K-means fragmentation if needed
	  // K-means responsability : fill the deliveriesForEachTour
	  List<Cluster> clusters = TourCalculator.clusterizeData(deliveryMenCount);
	  for (Cluster cluster : clusters){
	      deliveriesForEachTour.add(cluster.getDeliveries());
	  }
	  // XXX : else resolve the big TSP
	  
	  /* Solves TSP within the sub-graph, and create the tours */
	  // resolveTSP();

	  for (int i = 0; i < deliveryMenCount; i++) {
	       createSubGraph(i);
	       resolveTSPSubGraph(i);
	  }

     }

     /**
      * Creates the graph to be used with TSP algorithm
      * 
      * It uses the plan and all the deliveries, as well as the depot
      * 
      * It creates a sub-graph, containing only the useful nodes (deliveries and
      * depot), and the arrows correspond to the shortest path found between the
      * 2 nodes in the main graph
      * 
      * It assumes the ordering of deliveries remain the same (i.e. the list
      * deliveries will not be modified) And it expects that the depot will
      * always be the first in both list (<b>this is actually a pre-condition in
      * the TSP algorithm</b>)
      */
     private void createGraph() {
	  /* Initialization */
	  nodesCount = 1 + deliveries.size(); // depot as first + deliveries

	  delay = new int[nodesCount];
	  delay[0] = 0; // no delay in the depot
	  for (int i = 0; i < deliveries.size(); i++) {
	       delay[i + 1] = deliveries.get(i).getDuration();
	  }

	  costTSP = new double[nodesCount][nodesCount];

	  /* Calculation of costs with Dijkstra */
	  // First line of matrix is depot; other lines are the delivery points
	  costTSP[0] = dijkstraHelper(depot.getAddress());
	  for (int i = 0; i < deliveries.size(); i++) {
	       costTSP[i + 1] = dijkstraHelper(deliveries.get(i).getAddress());
	  }
     }

     /**
      * Create a restricted graph to be used with the TSP algorithm (see
      * createGraph())
      * 
      * @param index
      */
     public void createSubGraph(int index) {
	  // Attempts to stays as close as possible to original implementation
	  List<Delivery> deliveries = deliveriesForEachTour.get(index);

	  /* Initialization */
	  int nodesCount = 1 + deliveries.size(); // depot as first + deliveries
	  nodesCountForEachTour.add(index, nodesCount);

	  int[] delay = new int[nodesCount];
	  delay[0] = 0; // no delay in the depot
	  for (int i = 0; i < deliveries.size(); i++) {
	       delay[i + 1] = deliveries.get(i).getDuration();
	  }
	  delayForEachTour.add(index, delay);

	  double[][] costTSP = new double[nodesCount][nodesCount];

	  /* Calculation of costs with Dijkstra */
	  // First line of matrix is depot; other lines are the delivery points
	  costTSP[0] = dijkstraHelperSubGraph(index, depot.getAddress());
	  for (int i = 0; i < deliveries.size(); i++) {
	       costTSP[i + 1] = dijkstraHelperSubGraph(index, deliveries.get(i).getAddress());
	  }
	  costsTSPForEachTour.add(index, costTSP);
     }

     /**
      * Dijkstra helper function, create the useful row for TSP cost matrix
      * based on the result of the algorithm, and create the steps related to
      * Dijkstra result
      */
     private double[] dijkstraHelper(Intersection source) {
	  Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = map.Dijkstra(source);

	  HashMap<Long, Double> cost = result.getKey();
	  HashMap<Long, Long> predecessors = result.getValue();

	  /*
	   * idsList is the "header" of the list : the ids of the nodes to use
	   * in the correct order
	   */
	  long[] idsList = new long[nodesCount];
	  idsList[0] = depot.getAddress().getId();
	  for (int i = 0; i < deliveries.size(); i++) {
	       idsList[i + 1] = deliveries.get(i).getAddress().getId();
	  }

	  /* We use the "header" to construct the cost line */
	  double[] costResult = new double[nodesCount];
	  for (int i = 0; i < nodesCount; i++) {
	       costResult[i] = cost.get(idsList[i]);
	  }

	  /* We also create the steps */
	  createSteps(predecessors, source);

	  /* And we return the cost */
	  return costResult;
     }

     /**
      * Dijkstra helper function, create the useful row for TSP cost matrix
      * based on the result of the algorithm, and create the steps related to
      * Dijkstra result Sub-graph implementation, see dijkstraHelper
      */
     private double[] dijkstraHelperSubGraph(int index, Intersection source) {
	  Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = map.Dijkstra(source);
	  // XXX : this is what we need to be careful about ^^^^^

	  HashMap<Long, Double> cost = result.getKey();
	  HashMap<Long, Long> predecessors = result.getValue();

	  int nodesCount = nodesCountForEachTour.get(index);
	  List<Delivery> deliveries = deliveriesForEachTour.get(index);
	  /*
	   * idsList is the "header" of the list : the ids of the nodes to use
	   * in the correct order
	   */
	  long[] idsList = new long[nodesCount];
	  idsList[0] = depot.getAddress().getId();
	  for (int i = 0; i < deliveries.size(); i++) {
	       idsList[i + 1] = deliveries.get(i).getAddress().getId();
	  }

	  /* We use the "header" to construct the cost line */
	  double[] costResult = new double[nodesCount];
	  for (int i = 0; i < nodesCount; i++) {
	       costResult[i] = cost.get(idsList[i]);
	  }

	  /* We also create the steps */
	  // XXX : I don't know if we need to change this v
	  createSteps(predecessors, source);

	  /* And we return the cost */
	  return costResult;
     }

     /**
      * Wrapper function around TSP resolution, delegate responsibility to TSP.
      * Execute the TSP algorithm, extract the needed information to create the
      * tours
      */
     private void resolveTSP() {
	  TSPimplementation.searchSolution(calculationTimeLimitMs, nodesCount, costTSP, delay);

	  List<Step> solutionSteps = findStepsFromResult(TSPimplementation.getBestSolution());

	  if (deliveryMenCount == 1) {
	       // do not bother recalculating, hardcoding is fine
	       // TourFactory.createTour(1, solutionSteps, depot, deliveries);
	  }
     }
     
     /**
      * 
      * @param index
      */
     private void resolveTSPSubGraph(int index) {
	  TSPimplementation.searchSolution(calculationTimeLimitMs, nodesCountForEachTour.get(index),
		    costsTSPForEachTour.get(index), delayForEachTour.get(index));

	  List<Step> solutionSteps = findStepsFromResultSubGraph(index, TSPimplementation.getBestSolution());
	  TourFactory.createTour(index, solutionSteps, depot, deliveriesForEachTour.get(index));
     }
    
     
     /**
      * Create the steps of the tours.
      * 
      * @param predecessors
      * @param source
      */
     private void createSteps(HashMap<Long, Long> predecessors, Intersection source) {
	  long sourceId = source.getId();
	  for (Long id : predecessors.keySet()) {
	       if (id.equals(sourceId)) {
		    continue;
	       }

	       // Filter useful nodes, we don't need to create steps between
	       // every single intersection
	       if (findCorrespondingDelivery(map.getIntersectionById(id)) == null) {
		    continue;
	       }

	       ArrayList<Long> intersectionsIds = new ArrayList<>();
	       Long currentId = id;
	       do {
		    intersectionsIds.add(currentId);
		    currentId = predecessors.get(currentId);
	       } while (currentId != sourceId);

	       intersectionsIds.add(sourceId);

	       Collections.reverse(intersectionsIds);

	       ArrayList<Section> sections = new ArrayList<>();
	       for (int i = 0; i < intersectionsIds.size() - 1; i++) {
		    Section s = findSectionBetween(intersectionsIds.get(i), intersectionsIds.get(i + 1));
		    if (s != null) {
			 sections.add(s);
		    }
	       }

	       Step step = new Step(sections);
	       Pair<Long, Long> pair = new Pair<Long, Long>(sourceId, id);
	       steps.put(pair, step);
	  }
     }

     /**
      * Find the section, if it exists, between two intersections
      * 
      * @param idStart the id of the departure intersection
      * @param idEnd the id of the arriving intersection
      * @return the Section from the departure intersection to the arriving
      *         intersection, it it exists
      */
     private Section findSectionBetween(Long idStart, Long idEnd) {
	  for (Section section : map.getIntersectionById(idStart).getOutcomingSections()) {
	       if (section.getEnd().getId() == idEnd) {
		    return section;
	       }
	  }
	  return null;
     }

     /**
      * Creates the list of steps corresponding to the TSP solution
      * 
      * @param solution : the array of integers (which represents the
      *        intersections in order, in the TSP solution)
      * @return a List of Steps corresponding to the steps used by the TSP to
      *         make a tour, including the step from the last delivery to the
      *         depot
      */
     private List<Step> findStepsFromResult(Integer[] solution) {
	  List<Step> list = new ArrayList<Step>();
	  for (int i = 0; i < solution.length - 1; i++) {
	       /* Convert ids used with TSP to ids used in Model */
	       long idStart = solution[i] == 0 ? depot.getAddress().getId()
			 : deliveries.get(solution[i] - 1).getAddress().getId();
	       long idEnd = solution[i + 1] == 0 ? depot.getAddress().getId()
			 : deliveries.get(solution[i + 1] - 1).getAddress().getId();

	       list.add(steps.get(new Pair<Long, Long>(idStart, idEnd)));
	  }

	  /* Add latest step : last delivery -> depot */
	  long idStart = deliveries.get(solution[solution.length - 1] - 1).getAddress().getId();
	  long idEnd = depot.getAddress().getId();

	  list.add(steps.get(new Pair<Long, Long>(idStart, idEnd)));

	  return list;
     }

     private List<Step> findStepsFromResultSubGraph(int index, Integer[] solution) {
	  List<Step> list = new ArrayList<Step>();
	  for (int i = 0; i < solution.length - 1; i++) {
	       /* Convert ids used with TSP to ids used in Model */
	       long idStart = solution[i] == 0 ? depot.getAddress().getId()
			 : deliveriesForEachTour.get(index).get(solution[i] - 1).getAddress().getId();
	       long idEnd = solution[i + 1] == 0 ? depot.getAddress().getId()
			 : deliveriesForEachTour.get(index).get(solution[i + 1] - 1).getAddress().getId();

	       list.add(steps.get(new Pair<Long, Long>(idStart, idEnd)));
	  }

	  /* Add latest step : last delivery -> depot */
	  long idStart = deliveriesForEachTour.get(index).get(solution[solution.length - 1] - 1).getAddress().getId();
	  long idEnd = depot.getAddress().getId();

	  list.add(steps.get(new Pair<Long, Long>(idStart, idEnd)));

	  return list;
     }

     /**
      * Remove a delivery from its tour, and update the steps and time for the
      * tour
      * 
      * @param delivery
      * @param tour
      */
     public void removeDeliveryFromTour(Delivery delivery, Tour tour) {
	  // XXX : not futureproof yet, uses the steps hashmap
	  // XXX : remove tour argument and calculate it ?
	  /* Doesn't need any recalculation */

	  System.out.println("/********************/");
	  System.out.println("Steps before removal : " + tour.getSteps());

	  if (delivery.equals(depot)) {
	       System.out.println(("Cant remove depot !"));
	       return;
	  }

	  if (tour.getSteps().size() < 3) {
	       System.out.println(("Cant remove last delivery !"));
	       return;
	  }

	  /* Find the delivery in the steps */
	  Step stepBeforeDelivery = TourFactory.getInstance().findStepBeforeDelivery(delivery);
	  int indexOfStepBeforeDelivery = tour.getSteps().indexOf(stepBeforeDelivery);
	  int indexOfStepAfterDelivery = indexOfStepBeforeDelivery + 1;
	  Step stepAfterDelivery = tour.getSteps().get(indexOfStepAfterDelivery);

	  System.out.println("stepBeforeDelivery : " + stepBeforeDelivery + " (" + indexOfStepBeforeDelivery + ")");
	  System.out.println("stepAfterDelivery : " + stepAfterDelivery + " (" + indexOfStepAfterDelivery + ")");

	  /* Link the deliveries before and after the one removed */
	  Delivery deliveryBefore = stepBeforeDelivery.getStartDelivery();
	  Delivery deliveryAfter = stepAfterDelivery.getEndDelivery();

	  /*
	   * The new step will be in place of the step before the delivery and
	   * will use the shortest path between them
	   */
	  Step newStep = steps.get(new Pair<>(deliveryBefore.getAddress().getId(), deliveryAfter.getAddress().getId()));
	  System.out.println(
		    "PAIR : " + new Pair<>(deliveryBefore.getAddress().getId(), deliveryAfter.getAddress().getId()));
	  System.out.println("newStep : " + newStep);
	  tour.getSteps().add(indexOfStepBeforeDelivery + 1, newStep);

	  /* Remove the steps before and after the delivery */
	  tour.getSteps().remove(stepBeforeDelivery);
	  tour.getSteps().remove(stepAfterDelivery);

	  System.out.println("Steps after removal : " + tour.getSteps());

	  tour.testCoherency();

	  /* Remove the delivery */
	  tour.removeDelivery(delivery);
	  deliveries.remove(delivery);

	  tour.calculateDeliveryHours();
     }

     /**
      * Adds a new delivery in a tour, after a selected delivery
      * 
      * @param newDelivery the new Delivery to add to the tour
      * @param precedingDelivery the Delivery which will precede the new one
      */
     public void addDeliveryAfterDelivery(Delivery newDelivery, Delivery precedingDelivery) {
	  // XXX : not future proof either
	  /*
	   * Some calculations required (Dijkstra for new steps), but no
	   * re-execution of TSP or K-means
	   */

	  /* We test if the delivery already exists */
	  if (deliveries.contains(newDelivery)) {
	       System.out.println("Delivery already exists !");
	       return;
	  }

	  /* We add the new Delivery to the list of deliveries */
	  // TODO : add it to the right delivery list
	  deliveries.add(newDelivery);

	  /* We find the tour corresponding to the preceding Delivery */
	  Tour tour = TourFactory.getInstance().findTourContainingDelivery(precedingDelivery);

	  /* We add the newDelivery to the tour */
	  tour.addDeliveryAtIndex(newDelivery, tour.getDeliveryPoints().indexOf(precedingDelivery));

	  System.out.println("/*****************************/");

	  System.out.println("Step before modification : " + tour.getSteps());
	  System.out.println("Deliveries before modification : " + tour.getDeliveryPoints());

	  System.out.println();

	  /*
	   * We create the step between the new delivery all other deliveries
	   */
	  Pair<HashMap<Long, Double>, HashMap<Long, Long>> result;
	  HashMap<Long, Long> predecessors;

	  // result = map.Dijkstra(precedingDelivery.getAddress());
	  // predecessors = result.getValue();
	  // createSteps(predecessors, precedingDelivery.getAddress());
	  //
	  // System.out.println("CREATED STEPS FOR : " +
	  // precedingDelivery.getAddress().getId());
	  //
	  // result = map.Dijkstra(newDelivery.getAddress());
	  // predecessors = result.getValue();
	  // createSteps(predecessors, newDelivery.getAddress());
	  //
	  // System.out.println("CREATED STEPS FOR : " +
	  // newDelivery.getAddress().getId());

	  for (Delivery delivery : deliveries) {
	       result = map.Dijkstra(delivery.getAddress());
	       predecessors = result.getValue();
	       createSteps(predecessors, delivery.getAddress());
	  }

	  /*
	   * We remove the step between the preceding Delivery and the delivery
	   * after the preceding delivery in the current tour
	   */
	  Step stepBeforePrecedingDelivery = TourFactory.getInstance().findStepBeforeDelivery(precedingDelivery);
	  int indexOfStepBeforePrecedingDelivery = tour.getSteps().indexOf(stepBeforePrecedingDelivery);
	  if (indexOfStepBeforePrecedingDelivery == tour.getSteps().size() - 1) {
	       indexOfStepBeforePrecedingDelivery = -1;
	  }
	  Step stepAfterPrecedingDelivery = tour.getSteps().get(indexOfStepBeforePrecedingDelivery + 1);

	  Delivery deliveryAfterNewDelivery = stepAfterPrecedingDelivery.getEndDelivery();

	  // ///////
	  // result = map.Dijkstra(deliveryAfterNewDelivery.getAddress());
	  // predecessors = result.getValue();
	  // createSteps(predecessors, deliveryAfterNewDelivery.getAddress());
	  //
	  // System.out.println("CREATED STEPS FOR : " +
	  // deliveryAfterNewDelivery.getAddress().getId());
	  // ///////

	  int indexOfStepAfterPrecedingDelivery = tour.getSteps().indexOf(stepAfterPrecedingDelivery);
	  tour.getSteps().remove(stepAfterPrecedingDelivery);
	  // XXX : add a proper method in tour

	  /* We update the steps of the tour */
	  Step stepFromPrecedingDeliveryToNewDelivery = steps
		    .get(new Pair<>(precedingDelivery.getAddress().getId(), newDelivery.getAddress().getId()));
	  Step stepFromNewDeliveryToItsNextDelivery = steps
		    .get(new Pair<>(newDelivery.getAddress().getId(), deliveryAfterNewDelivery.getAddress().getId()));
	  tour.getSteps().add(indexOfStepAfterPrecedingDelivery, stepFromPrecedingDeliveryToNewDelivery);
	  tour.getSteps().add(indexOfStepAfterPrecedingDelivery + 1, stepFromNewDeliveryToItsNextDelivery);
	  // XXX : add a proper method in tour

	  System.out.println("Index : " + indexOfStepAfterPrecedingDelivery);
	  System.out.println();
	  System.out.println("Preceding delivery : " + precedingDelivery);
	  System.out.println("Succeding delivery : " + deliveryAfterNewDelivery);
	  System.out.println("New delivery : " + newDelivery);
	  System.out.println();
	  System.out.println("Removed step : " + stepAfterPrecedingDelivery);
	  System.out.println("New step 1 : " + stepFromPrecedingDeliveryToNewDelivery);
	  System.out.println("New step 2 : " + stepFromNewDeliveryToItsNextDelivery);
	  System.out.println();
	  System.out.println("Step after modification : " + tour.getSteps());
	  System.out.println("Deliveries aftermodification : " + tour.getDeliveryPoints());
	  tour.testCoherency();

	  /* We update the times for the modified tour */
	  tour.calculateDeliveryHours();
     }
}
