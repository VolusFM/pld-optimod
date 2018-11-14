
package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javafx.util.Pair;
import main.model.tsp.TSP1;
import main.model.tsp.TemplateTSP;

/**
 * TourCalculator is a singleton class which handles the calculation of tours.
 */
public class TourCalculator {

    private Plan map = ModelInterface.getPlan();
    private List<Delivery> deliveries;
    private List<List<Delivery>> deliveriesForEachTour;
    private Delivery depot;

    // Calculation time limit in milliseconds
    private int calculationTimeLimit = 10000;
    private int deliveryMenCount = 2;

    /* Unique instance */
    private static TourCalculator instance = null;

    /* TSP related fields */
    private double[][] costTSP;
    private int nodesCount;
    private int[] delay;
    private TemplateTSP TSPimplementation = new TSP1();
    private HashMap<Pair<Long, Long>, Step> steps;

    /* TSP related fields for multiple tours */
    private List<double[][]> costsTSPForEachTour;
    private List<Integer> nodesCountForEachTour;
    private List<int[]> delayForEachTour;

    /* Constants */
    private static final int MAXKMEANS = 1000;
    private static final double MAXDOUBLE = Double.MAX_VALUE;

    /**
     * Create the tour calculator.
     */
    private TourCalculator() {
	this.deliveries = new ArrayList<>();
	this.deliveriesForEachTour = new ArrayList<List<Delivery>>();
	this.steps = new HashMap<>();
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
     * Initialize the calculator before a calculation. Remove possible traces from
     * previous calculations.
     */
    protected void initialize() {
	TourFactory.getInstance().empty();
	this.deliveriesForEachTour = new ArrayList<>();
	this.costsTSPForEachTour = new ArrayList<>();
	this.nodesCountForEachTour = new ArrayList<>();
	this.delayForEachTour = new ArrayList<>();
	this.steps = new HashMap<>();
    }

    /**
     * Add a delivery to the list of deliveries.
     * 
     * @param delivery is the delivery to add.
     */
    protected void addDelivery(Delivery delivery) {
	this.deliveries.add(delivery);
    }

    /**
     * Empty the loaded deliveries.
     */
    protected void emptyLoadedDeliveries() {
	this.deliveries = new ArrayList<>();
	this.depot = null;
    }

    /**
     * Do the calculation based on the map, depot and deliveries, to create the
     * tours.
     */
    public void calculateTours() {
	initialize();
	/* Creates the global sub-graph, with all deliveries */
	createGraph();
	List<Cluster> clusters = clusterizeData(this.deliveryMenCount, 0);
	for (Cluster cluster : clusters) {
	    this.deliveriesForEachTour.add(cluster.getDeliveries());
	}
	/* Solves TSP within the sub-graph, and create the tours */
	for (int i = 0; i < Math.min(this.deliveries.size(), this.deliveryMenCount); i++) {
	    // We don't want to create too many tours
	    createSubGraph(i);
	    resolveTSPSubGraph(i);
	}
    }

    /**
     * Create the graph to use with TSP algorithm.
     * 
     * It uses the plan and all the deliveries, as well as the depot.
     * 
     * It creates a sub-graph, containing only the useful nodes (deliveries and
     * depot), and the arcs correspond to the shortest path found between the 2
     * nodes in the main graph.
     * 
     * It assumes the ordering of deliveries remain the same (i.e. the list
     * deliveries will not be modified) and it expects that the depot will always be
     * the first in both lists (<b>this is actually a pre-condition in the TSP
     * algorithm</b>)
     */
    protected void createGraph() {
	/* Initialization */
	this.nodesCount = 1 + this.deliveries.size(); // depot as first + deliveries

	this.delay = new int[this.nodesCount];
	this.delay[0] = 0; // no delay in the depot
	for (int i = 0; i < this.deliveries.size(); i++) {
	    this.delay[i + 1] = this.deliveries.get(i).getDuration();
	}
	this.costTSP = new double[this.nodesCount][this.nodesCount];

	/* Calculation of costs with Dijkstra */
	// First line of matrix is depot; other lines are the delivery points
	this.costTSP[0] = dijkstraHelper(this.depot.getAddress());
	for (int i = 0; i < this.deliveries.size(); i++) {
	    this.costTSP[i + 1] = dijkstraHelper(this.deliveries.get(i).getAddress());
	}

    }

    /**
     * Create a restricted graph to be used with the TSP algorithm (see
     * createGraph()).
     * 
     * @param index is the index in deliveriesForEachTour where the List of
     *                  deliveries needed for this TSP is.
     */
    private void createSubGraph(int index) {
	List<Delivery> tourDeliveries = this.deliveriesForEachTour.get(index);
	/* Initialization */
	int tourNodesCount = 1 + tourDeliveries.size(); // depot as first + deliveries
	this.nodesCountForEachTour.add(index, tourNodesCount);

	int[] tourDelay = new int[tourNodesCount];
	tourDelay[0] = 0; // no delay in the depot
	for (int i = 0; i < tourDeliveries.size(); i++) {
	    tourDelay[i + 1] = tourDeliveries.get(i).getDuration();
	}
	this.delayForEachTour.add(index, tourDelay);

	double[][] tourCostTSP = new double[tourNodesCount][tourNodesCount];

	// Calculation of costs with Dijkstra
	// First line of matrix is depot; other lines are the delivery points
	tourCostTSP[0] = dijkstraHelperSubGraph(index, this.depot.getAddress());
	for (int i = 0; i < tourDeliveries.size(); i++) {
	    tourCostTSP[i + 1] = dijkstraHelperSubGraph(index, tourDeliveries.get(i).getAddress());
	}
	this.costsTSPForEachTour.add(index, tourCostTSP);
    }

    /**
     * Dijkstra helper function, create the useful row for TSP cost matrix based on
     * the result of the algorithm, and create the steps related to Dijkstra result.
     * 
     * @param source is the source from which Dijkstra will start.
     * @return Array, the row for the TSP cost matrix.
     */
    private double[] dijkstraHelper(Intersection source) {
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = this.map.dijkstra(source);
	HashMap<Long, Double> cost = result.getKey();
	HashMap<Long, Long> predecessors = result.getValue();

	// idsList is the "header" of the list : the ids of the nodes to use in the
	// correct order
	long[] idsList = new long[this.nodesCount];
	idsList[0] = this.depot.getAddress().getId();
	for (int i = 0; i < this.deliveries.size(); i++) {
	    idsList[i + 1] = this.deliveries.get(i).getAddress().getId();
	}
	// We use the "header" to construct the cost line
	double[] costResult = new double[this.nodesCount];
	for (int i = 0; i < this.nodesCount; i++) {
	    costResult[i] = cost.get(idsList[i]);
	}

	createSteps(predecessors, source);
	return costResult;
    }

    /**
     * Dijkstra helper function, create the useful row for TSP cost matrix based on
     * the result of the algorithm, and create the steps related to Dijkstra result
     * Sub-graph implementation, see dijkstraHelper.
     * 
     * @param index  is the index in deliveriesForEachTour where List of delivery
     *                   needed for TSP is.
     * @param source is the source from which Dijkstra will start.
     * @return Array, the row for the TSP cost matrix.
     */
    private double[] dijkstraHelperSubGraph(int index, Intersection source) {
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> result = this.map.dijkstra(source);

	HashMap<Long, Double> cost = result.getKey();
	HashMap<Long, Long> predecessors = result.getValue();

	int tourNodesCount = this.nodesCountForEachTour.get(index);
	List<Delivery> tourDeliveries = this.deliveriesForEachTour.get(index);

	// idsList is the "header" of the list : the ids of the nodes to use in the
	// correct order

	long[] idsList = new long[tourNodesCount];
	idsList[0] = this.depot.getAddress().getId();
	for (int i = 0; i < tourDeliveries.size(); i++) {
	    idsList[i + 1] = tourDeliveries.get(i).getAddress().getId();
	}

	// We use the "header" to construct the cost line
	double[] costResult = new double[tourNodesCount];
	for (int i = 0; i < tourNodesCount; i++) {
	    costResult[i] = cost.get(idsList[i]);
	}

	createSteps(predecessors, source);

	return costResult;
    }

    /**
     * Resolve TSP for the subGraph.
     * 
     * @param index is the index of the subgraph.
     */
    private void resolveTSPSubGraph(int index) {
	this.TSPimplementation.searchSolution(this.calculationTimeLimit, this.nodesCountForEachTour.get(index),
		this.costsTSPForEachTour.get(index), this.delayForEachTour.get(index));
	List<Step> solutionSteps = findStepsFromResultSubGraph(index, this.TSPimplementation.getBestSolution());
	TourFactory.getInstance().createTour(index, solutionSteps, this.depot, this.deliveriesForEachTour.get(index));
    }

    /**
     * Create the steps of the tours.
     * 
     * @param predecessors is result of Dijkstra
     * @param source       is the source where start the Dijkstra s algorithm.
     */
    private void createSteps(HashMap<Long, Long> predecessors, Intersection source) {
	long sourceId = source.getId();
	for (Long id : predecessors.keySet()) {
	    if (id.equals(sourceId)) {
		continue;
	    }
	    // Filter useful nodes, we don't need to create steps between
	    // every single intersection
	    if (findCorrespondingDelivery(this.map.getIntersectionById(id)) == null) {
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
	    this.steps.put(pair, step);
	}
    }

    /**
     * Find the delivery taking place at the intersection, if it exists.
     * 
     * @param intersection is the intersection where a Delivery is supposed to
     *                         happen.
     * @return the corresponding Delivery if it exists, or null if it doesn't.
     */
    public Delivery findCorrespondingDelivery(Intersection intersection) {
	for (Delivery delivery : this.deliveries) {
	    if (delivery.getAddress().equals(intersection)) {
		return delivery;
	    }
	}
	if (this.depot.getAddress().equals(intersection)) {
	    return this.depot;
	}
	return null;
    }

    /**
     * Find the section, if it exists, between two intersections.
     * 
     * @param idStart the id of the departure intersection.
     * @param idEnd   the id of the arriving intersection.
     * @return the Section from the departure intersection to the arriving
     *         intersection, it it exists.
     */
    private Section findSectionBetween(Long idStart, Long idEnd) {
	for (Section section : this.map.getIntersectionById(idStart).getOutcomingSections()) {
	    if (section.getEnd().getId() == idEnd) {
		return section;
	    }
	}
	return null;
    }

    /**
     * Take result of the TSP and create associated Steps.
     * 
     * @param index    is index of subGraph.
     * @param solution is result of the TSP.
     * @return List, the list of calculated steps.
     */
    private List<Step> findStepsFromResultSubGraph(int index, Integer[] solution) {
	List<Step> list = new ArrayList<Step>();
	for (int i = 0; i < solution.length - 1; i++) {
	    // Convert ids used with TSP to ids used in Model
	    long idStart = solution[i] == 0 ? this.depot.getAddress().getId()
		    : this.deliveriesForEachTour.get(index).get(solution[i] - 1).getAddress().getId();
	    long idEnd = solution[i + 1] == 0 ? this.depot.getAddress().getId()
		    : this.deliveriesForEachTour.get(index).get(solution[i + 1] - 1).getAddress().getId();

	    list.add(this.steps.get(new Pair<Long, Long>(idStart, idEnd)));
	}

	// Add latest step : last delivery -> depot
	long idStart = this.deliveriesForEachTour.get(index).get(solution[solution.length - 1] - 1).getAddress()
		.getId();
	long idEnd = this.depot.getAddress().getId();
	list.add(this.steps.get(new Pair<Long, Long>(idStart, idEnd)));

	return list;
    }

    /**
     * Remove a delivery from its tour, and update the steps and time for the tour.
     * Doesn't need any recalculation.
     * 
     * @param delivery is the delivery to remove.
     * @param tour     is where the delivery must be remove.
     */
    public void removeDeliveryFromTour(Delivery delivery, Tour tour) {
	if (delivery.equals(this.depot)) {
	    throw new RuntimeException("Cant remove depot !");
	}
	if (tour.getSteps().size() < 3) {
	    throw new RuntimeException("Cant remove last delivery !");
	}

	// Find the delivery in the steps
	Step stepBeforeDelivery = TourFactory.getInstance().findStepBeforeDelivery(delivery);
	int indexOfStepBeforeDelivery = tour.getSteps().indexOf(stepBeforeDelivery);
	int indexOfStepAfterDelivery = indexOfStepBeforeDelivery + 1;
	Step stepAfterDelivery = tour.getSteps().get(indexOfStepAfterDelivery);

	// Link the deliveries before and after the one removed
	Delivery deliveryBefore = stepBeforeDelivery.getStartDelivery();
	Delivery deliveryAfter = stepAfterDelivery.getEndDelivery();

	// The new step will be in place of the step before the delivery and will use
	// the shortest path between them
	Step newStep = this.steps
		.get(new Pair<>(deliveryBefore.getAddress().getId(), deliveryAfter.getAddress().getId()));
	tour.getSteps().add(indexOfStepBeforeDelivery + 1, newStep);
	// Remove the steps before and after the delivery
	tour.getSteps().remove(stepBeforeDelivery);
	tour.getSteps().remove(stepAfterDelivery);
	// Remove the delivery
	tour.removeDelivery(delivery);
	this.deliveries.remove(delivery);
	tour.calculateDeliveryHours();
    }

    /**
     * Add a new delivery in a tour, after a selected delivery. Some calculations
     * are required (Dijkstra for new steps), but no re-execution of TSP or K-means
     * 
     * @param newDelivery       the new Delivery to add to the tour.
     * @param precedingDelivery the Delivery which will precede the new one.
     * @param tour              is the tour to which we want to add the delivery.
     */
    public void addDeliveryToTour(Delivery newDelivery, Delivery precedingDelivery, Tour tour) {

	// Test if the delivery already exists
	if (this.deliveries.contains(newDelivery) || newDelivery.getAddress().equals(this.depot.getAddress())) {
	    throw new RuntimeException("Delivery already exists !");
	}

	// Add the new Delivery to the list of deliveries
	this.deliveries.add(newDelivery);

	// Add the newDelivery to the tour
	tour.addDeliveryAtIndex(newDelivery, tour.getDeliveryPoints().indexOf(precedingDelivery));

	// Create the step between the new delivery all other deliveries
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> result;
	HashMap<Long, Long> predecessors;

	for (Delivery delivery : this.deliveries) {
	    result = this.map.dijkstra(delivery.getAddress());
	    predecessors = result.getValue();
	    createSteps(predecessors, delivery.getAddress());
	}

	result = this.map.dijkstra(this.depot.getAddress());
	predecessors = result.getValue();
	createSteps(predecessors, this.depot.getAddress());

	// Remove the step between the preceding Delivery and the delivery after the
	// preceding delivery in the current tour
	Step stepBeforePrecedingDelivery = TourFactory.getInstance().findStepBeforeDelivery(precedingDelivery);
	int indexOfStepBeforePrecedingDelivery = tour.getSteps().indexOf(stepBeforePrecedingDelivery);
	if (indexOfStepBeforePrecedingDelivery == tour.getSteps().size() - 1) {
	    indexOfStepBeforePrecedingDelivery = -1;
	}
	Step stepAfterPrecedingDelivery = tour.getSteps().get(indexOfStepBeforePrecedingDelivery + 1);
	Delivery deliveryAfterNewDelivery = stepAfterPrecedingDelivery.getEndDelivery();

	int indexOfStepAfterPrecedingDelivery = tour.getSteps().indexOf(stepAfterPrecedingDelivery);
	tour.getSteps().remove(stepAfterPrecedingDelivery);

	// Update the steps of the tour
	Step stepFromPrecedingDeliveryToNewDelivery = this.steps
		.get(new Pair<>(precedingDelivery.getAddress().getId(), newDelivery.getAddress().getId()));
	Step stepFromNewDeliveryToItsNextDelivery = this.steps
		.get(new Pair<>(newDelivery.getAddress().getId(), deliveryAfterNewDelivery.getAddress().getId()));

	tour.getSteps().add(indexOfStepAfterPrecedingDelivery, stepFromPrecedingDeliveryToNewDelivery);
	tour.getSteps().add(indexOfStepAfterPrecedingDelivery + 1, stepFromNewDeliveryToItsNextDelivery);

	// Update the times for the modified tour
	tour.calculateDeliveryHours();
    }

    /**
     * data clustering using k-means algorithm. Can return some empty clusters if
     * clusterNb is badly set, or is superior to intersections s size. It can also
     * happen depending on the random initialization.
     * 
     * @param clustersCount is the number of clusters in the returned list. Must be
     *                          strictly inferior to dataPoints's size, or kMeans
     *                          will throw an AssertionError.
     * @param dataPoints    is the data to partition.
     * @param epsilon       is the convergence coefficient.
     * @return a list of clustersCount clusters.
     */
    protected List<Cluster> kMeans(int clustersCount, List<Delivery> dataPoints, double epsilon) {
	if (!(clustersCount <= dataPoints.size()) || (clustersCount == 0)) {
	    throw new AssertionError("Kmean was called with incorrect clusterNb.");
	}
	/* Cluster initialization */
	List<Cluster> clusters = new ArrayList<Cluster>();
	List<Delivery> dataPointsCopy = new ArrayList<Delivery>(dataPoints);
	for (int i = 0; i < clustersCount; i++) {
	    int rdInt = ThreadLocalRandom.current().nextInt(0, dataPointsCopy.size());
	    Pair<Double, Double> randomCentroid = new Pair<Double, Double>(
		    dataPointsCopy.get(rdInt).getAddress().getLat(), dataPointsCopy.get(rdInt).getAddress().getLon());
	    dataPointsCopy.remove(dataPointsCopy.get(rdInt));
	    Cluster newCluster = new Cluster(randomCentroid);
	    clusters.add(newCluster);
	}
	double maxConvergenceCoeff = epsilon + 1;
	double convergenceCoeff = epsilon + 1;
	while (maxConvergenceCoeff > epsilon) {
	    maxConvergenceCoeff = 0;
	    for (Cluster cluster : clusters) {
		cluster.reinitializeClusters();
	    }
	    // Centroid repartition based on euclidian distance
	    for (Delivery delivery : dataPoints) {
		Intersection intersection = delivery.getAddress();
		Pair<Double, Double> intersectionData = new Pair<Double, Double>(intersection.getLat(),
			intersection.getLon());
		double min = calculateDistance(intersectionData, clusters.get(0).getCentroid());
		int assignedClusterIndex = 0;
		for (int i = 0; i < clusters.size(); i++) {
		    double distance = calculateDistance(intersectionData, clusters.get(i).getCentroid());
		    if (distance < min) {
			min = distance;
			assignedClusterIndex = i;
		    }
		}
		clusters.get(assignedClusterIndex).addDelivery(delivery);
	    }

	    /* Recalculate centroid position */
	    for (Cluster cluster : clusters) {
		double barycentersLatitude = 0;
		double barycentersLongitude = 0;

		// Take care of empty cluster so that they still have a centroid
		if (cluster.getDeliveries().size() > 0) {
		    for (Delivery delivery : cluster.getDeliveries()) {
			barycentersLatitude += delivery.getAddress().getLat();
			barycentersLongitude += delivery.getAddress().getLon();
		    }
		    barycentersLatitude = barycentersLatitude / cluster.getDeliveries().size();
		    barycentersLongitude = barycentersLongitude / cluster.getDeliveries().size();
		} else {
		    barycentersLatitude = cluster.getCentroid().getKey();
		    barycentersLongitude = cluster.getCentroid().getValue();
		}
		Pair<Double, Double> newCentroid = new Pair<Double, Double>(barycentersLatitude, barycentersLongitude);
		convergenceCoeff = calculateDistance(newCentroid, cluster.getCentroid());
		if (maxConvergenceCoeff < convergenceCoeff) {
		    maxConvergenceCoeff = convergenceCoeff;
		}
		cluster.setCentroid(newCentroid);
	    }
	}
	return clusters;
    }

    /**
     * Private method used to create a cluster list when cluster number is > to
     * deliveries size.
     * 
     * @param clustersCount is number of cluster needed.
     * @return List, the list of created clusters.
     */
    private List<Cluster> createTrivialClusters(int clustersCount) {
	List<Cluster> bestClusters = new ArrayList<Cluster>();
	for (Delivery delivery : this.deliveries) {
	    Pair<Double, Double> centroid = new Pair<Double, Double>(delivery.getAddress().getLat(),
		    delivery.getAddress().getLon());
	    Cluster cluster = new Cluster(centroid);
	    cluster.addDelivery(delivery);
	    bestClusters.add(cluster);
	}
	return bestClusters;
    }

    /**
     * Private method used to check is a cluster list contains empty clusters.
     * 
     * @param clustersLists is the list to check.
     * @return boolean, whether there is an empty cluster.
     */
    private static boolean hasEmptyCluster(List<Cluster> clustersList) {
	boolean hasEmptyCluster = false;
	int indexCluster = 0;
	while (!hasEmptyCluster && indexCluster < clustersList.size()) {
	    if (clustersList.get(indexCluster).getDeliveries().isEmpty()) {
		hasEmptyCluster = true;
	    }
	    indexCluster++;
	}
	return hasEmptyCluster;
    }

    /**
     * Return id of the nearest delivery to toMove, which belongs to a not full, not
     * balanced cluster from currentClusters and which is from a different cluster
     * than currentClusterIndex.
     * 
     * @param toMove                        is the delivery of which we search the
     *                                          nearest.
     * @param currentClusters               is the list of clusters.
     * @param currentClusterIndex           is the id of the cluster which contains
     *                                          toMove.
     * @param remainingAdditionalDeliveries is the number of clusters not balanced
     *                                          with one more delivery.
     * @param deliveryIdToClusterId         is a HashMap of mapping a delivery ID to
     *                                          the id of the cluster which contains
     *                                          the delivery.
     * @return Integer, the id of the nearest delivery.
     */
    private int nearestDeliveryInClusters(Delivery toMove, List<Cluster> currentClusters, int currentClusterIndex,
	    int remainingAdditionalDeliveries, HashMap<Integer, Integer> deliveryIdToClusterId) {
	int minIndex = -1;
	int evenDeliveryNumber = this.deliveries.size() / currentClusters.size();
	double[] distanceToToMove = this.costTSP[this.deliveries.indexOf(toMove)];
	double minDistance = MAXDOUBLE;
	for (int indexCostTSP = 1; indexCostTSP < distanceToToMove.length; indexCostTSP++) {
	    int indexDelivery = indexCostTSP - 1;
	    /*
	     * in case this is the last cluster with additional deliveries
	     */
	    boolean isClusterNotFull;
	    if (remainingAdditionalDeliveries > 0) {
		isClusterNotFull = ((currentClusters.get(deliveryIdToClusterId.get(indexDelivery)).getDeliveries()
			.size()) < evenDeliveryNumber + 1);
	    } else {
		isClusterNotFull = ((currentClusters.get(deliveryIdToClusterId.get(indexDelivery)).getDeliveries()
			.size()) < evenDeliveryNumber);
	    }

	    if ((distanceToToMove[indexCostTSP] < minDistance) && (isClusterNotFull)
		    && !(currentClusters.get(deliveryIdToClusterId.get(indexDelivery)).isBalanced())
		    && (currentClusterIndex != deliveryIdToClusterId.get(indexDelivery))) {
		minDistance = distanceToToMove[indexCostTSP];
		minIndex = indexDelivery;
	    }
	}
	return minIndex;
    }

    /**
     * Calculate best set of clusters in a array of MAXKMEANS set of clusters. Will
     * not take into account empty clusters. The graph must be created before this
     * method is called. Needs at least two deliveries.
     * 
     * @param clustersCount is the number of clusters ; if it is superior to the
     *                          number of deliveries, only so many clusters will be
     *                          returned.
     * @param epsilon       is the convergence coefficient.
     * @return List, a list of balanced clusters.
     */
    public List<Cluster> clusterizeData(int clustersCount, double epsilon) {
	// Particular case where each cluster only has one delivery
	if (clustersCount >= this.deliveries.size()) {
	    return createTrivialClusters(clustersCount);
	}
	// General case
	List<Cluster> bestClusters = new ArrayList<Cluster>();
	double minCoeff = MAXDOUBLE;
	// Selection of best cluster set on MAXKMEANS iterations
	for (int i = 0; i < MAXKMEANS; i++) {
	    List<Cluster> currentClusters = this.kMeans(clustersCount, this.deliveries, epsilon);
	    int evenDeliveryNumber = this.deliveries.size() / clustersCount;
	    // This represent how many clusters have one more delivery
	    int remainingAdditionalDeliveries = this.deliveries.size() % clustersCount;
	    // Ignore sets with empty clusters
	    boolean hasEmptyCluster = hasEmptyCluster(currentClusters);
	    if (!hasEmptyCluster) {
		// Re-balance clusterList
		int currentClusterIndex = getBiggestUnbalancedCluster(currentClusters);
		HashMap<Integer, Integer> idDeliveryToIdCluster = clusterListToHashMap(currentClusters);
		while (currentClusterIndex != -1) {
		    int maxIntersectionNumber;
		    // Check if there can be an additional delivery
		    if ((currentClusters.get(currentClusterIndex).getDeliveries().size() > evenDeliveryNumber)
			    && (remainingAdditionalDeliveries > 0)) {
			maxIntersectionNumber = evenDeliveryNumber + 1;
			remainingAdditionalDeliveries--;
		    } else {
			maxIntersectionNumber = evenDeliveryNumber;
		    }
		    int nbExceedingDeliveries = currentClusters.get(currentClusterIndex).getDeliveries().size()
			    - maxIntersectionNumber;
		    currentClusters.get(currentClusterIndex).sortDeliveriesByEuclidianDistanceToCentroid();

		    // Move exceeding Deliveries to the cluster containing the nearest intersection
		    while ((nbExceedingDeliveries > 0)) {
			Delivery toMove = currentClusters.get(currentClusterIndex).popDelivery(0);
			int indexToMove = this.deliveries.indexOf(toMove);
			if (indexToMove == -1) {
			    throw new AssertionError("Delivery present in cluster does not exist.");
			}

			// Find the nearest delivery contained in a not full cluster (which is not the
			// current cluster)
			int minIndex = nearestDeliveryInClusters(toMove, currentClusters, currentClusterIndex,
				remainingAdditionalDeliveries, idDeliveryToIdCluster);
			if (minIndex == -1) {
			    throw new AssertionError(
				    "Error in clusterizeData in TourCalculator : no cluster was found to "
					    + "put an exceeding delivery. This error SHOULD NOT happen");
			}
			// Move exceeding delivery to nearest delivery's cluster
			Integer idNearestCluster = idDeliveryToIdCluster.get(minIndex);
			currentClusters.get(idNearestCluster).addDelivery(toMove);
			idDeliveryToIdCluster.put(minIndex, idNearestCluster);
			nbExceedingDeliveries = currentClusters.get(currentClusterIndex).getDeliveries().size()
				- maxIntersectionNumber;
		    }
		    currentClusters.get(currentClusterIndex).setIsBalanced(true);
		    currentClusterIndex = getBiggestUnbalancedCluster(currentClusters);
		}
	    }
	    // Evaluate cluster by adding distance to centroid
	    double coeff = 0;
	    for (Cluster cluster : currentClusters) {
		coeff += cluster.evaluateClusteringQuality();
	    }
	    if (coeff < minCoeff) {
		minCoeff = coeff;
		bestClusters = currentClusters;
	    }
	}
	return bestClusters;
    }

    /**
     * Find the id of the unbalanced cluster with the most deliveries.
     * 
     * @param clusters is the list of clusters in which we search.
     * @return Integer, the id of the unbalanced cluster with the most deliveries.
     */
    private static int getBiggestUnbalancedCluster(List<Cluster> clusters) {
	int clusterId = -1;
	int maxSize = 0;
	for (int index = 0; index < clusters.size(); index++) {
	    if (clusters.get(index).getDeliveries().size() > maxSize && !(clusters.get(index).isBalanced())) {
		maxSize = clusters.get(index).getDeliveries().size();
		clusterId = index;
	    }
	}
	return clusterId;
    }

    /**
     * Build a HashMap with delivery index as key and cluster index as value.
     * 
     * @param clusters is the list of clusters.
     * @return Map, a HashMap with delivery index as key and cluster index as value.
     */
    private HashMap<Integer, Integer> clusterListToHashMap(List<Cluster> clusters) {
	HashMap<Integer, Integer> idDeliveryToIdCluster = new HashMap<Integer, Integer>();
	for (int deliveryIndex = 0; deliveryIndex < this.deliveries.size(); deliveryIndex++) {
	    int clusterIndex = 0;
	    boolean foundCluster = false;
	    Delivery toFind = this.deliveries.get(deliveryIndex);
	    while ((clusterIndex < clusters.size()) && (!foundCluster)) {
		if (clusters.get(clusterIndex).getDeliveries().contains(toFind)) {
		    foundCluster = true;
		    idDeliveryToIdCluster.put(deliveryIndex, clusterIndex);
		}
		clusterIndex++;
	    }
	}
	return idDeliveryToIdCluster;
    }

    /**
     * Calculate euclidean distance between the two points.
     * 
     * @param firstPointCoordinates  is the coordinates of the first point.
     * @param secondPointCoordinates is the coordinates of the second point.
     * @return Double, the euclidean distance between the two points.
     */
    private static double calculateDistance(Pair<Double, Double> firstPointCoordinates,
	    Pair<Double, Double> secondPointCoordinates) {
	return Math.sqrt(Math.pow((firstPointCoordinates.getKey() - secondPointCoordinates.getKey()), 2)
		+ Math.pow((firstPointCoordinates.getValue() - secondPointCoordinates.getValue()), 2));
    }

    /**
     * Getter for the delivery men count.
     * 
     * @return Integer, the count of delivery men
     */
    public int getDeliveryMenCount() {
	return this.deliveryMenCount;
    }

    /**
     * Setter for the delivery men count.
     * 
     * @param deliveryMenCount is the number of delivery men.
     * @throws IllegalArgumentException, if deliveryMenCount isn't strictly
     *                                       positive.
     */
    public void setDeliveryMenCount(int deliveryMenCount) {
	if (deliveryMenCount <= 0) {
	    throw new IllegalArgumentException("Le nombre de livreurs doit être strictement positif.");
	}
	this.deliveryMenCount = deliveryMenCount;
    }

    /**
     * Getter of deliveries to do (excluding the depot).
     * 
     * @return List, the list of Deliveries to include in tours.
     */
    public List<Delivery> getDeliveries() {
	return this.deliveries;
    }

    /**
     * Getter of the depot for the current deliveries.
     * 
     * @return the Delivery corresponding to the depot.
     */
    public Delivery getDepot() {
	return this.depot;
    }

    /**
     * Setter for the depot for the tours.
     * 
     * @param depot is the Delivery corresponding to the depot.
     */
    protected void setDepot(Delivery depot) {
	this.depot = depot;
    }
    
    /**
     * Setter for the map.
     * 
     * @param map is the plan for the deliveries.
     */
    protected void setMap(Plan map) {
	this.map = map;
    }

}