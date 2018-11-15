package main.model;

import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;

/**
 * ModelInterface provides all methods other packages need to access the model
 * package.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public abstract class ModelInterface {

    private static Plan plan = new Plan();
    private static TourCalculator tourCalculator = TourCalculator.getInstance();
    private static TourFactory tourFactory = TourFactory.getInstance();

    /**
     * Add a delivery to the tour calculator.
     * 
     * @param toAdd is the delivery to add.
     */
    public static void addDeliveryToTourCalculator(Delivery toAdd) {
	tourCalculator.addDelivery(toAdd);
    }

    /**
     * Add a delivery to an already calculated tour.
     * 
     * @param toAdd             is the delivery to add.
     * @param precedingDelivery is the delivery to realize before the delivery to
     *                              add.
     * @param tour              is the tour to which we want to add the delivery.
     */
    public static void addDeliveryToTour(Delivery toAdd, Delivery precedingDelivery, Tour tour) {
	tourCalculator.addDeliveryToTour(toAdd, precedingDelivery, tour);
    }

    /**
     * Remove a delivery from tour calculator.
     * 
     * @param toRemove is the delivery to remove.
     */
    public static void removeDelivery(Delivery toRemove) {
	Tour t = tourFactory.findTourContainingDelivery(toRemove);
	tourCalculator.removeDeliveryFromTour(toRemove, t);
    }

    /**
     * Find the tour containing the delivery.
     * 
     * @param delivery is the delivery for which we search.
     * @return Tour, the Tour containing delivery.
     */
    public static Tour findTourContainingDelivery(Delivery delivery) {
	return tourFactory.findTourContainingDelivery(delivery);
    }

    /**
     * Find the closest section in the plan, given a set of coordinates.
     * 
     * @param latitude  is the latitude of the search location.
     * @param longitude is the latitude of the search location.
     * @return Section, the section closest to the search location.
     */
    public static Section findClosestSection(double latitude, double longitude) {
	return plan.findClosestSection(latitude, longitude);
    }

    /**
     * Find the closest intersection in the plan, given a set of coordinates.
     * 
     * @param latitude  is the latitude of the search location.
     * @param longitude is the latitude of the search location.
     * @return Intersection, the section closest to the search location.
     */
    public static Intersection findClosestIntersection(double latitude, double longitude) {
	return plan.findClosestIntersection(latitude, longitude);
    }

    /**
     * Find the step before a delivery.
     * 
     * @param delivery is the Delivery to which the step must lead.
     * @return Step, the step leading to the given delivery.
     */
    public static Step findStepBeforeDelivery(Delivery delivery) {
	return tourFactory.findStepBeforeDelivery(delivery);
    }

    /**
     * Find the delivery corresponding to a given intersection.
     * 
     * @param intersection is the place where a delivery a supposed to happen.
     * @return Delivery, the delivery corresponding to the intersection, or null if
     *         there is none.
     */
    public static Delivery findCorrespondingDelivery(Intersection intersection) {
	return tourCalculator.findCorrespondingDelivery(intersection);
    }

    /**
     * Creates the graph of tourCalculator to be used with TSP algorithm. It uses
     * the plan and all the deliveries, as well as the depot.
     */
    public static void createGraph() {
	tourCalculator.createGraph();
    }

    /**
     * Function calls to initialize.
     */
    public static void initializeTourCalculator() {
	tourCalculator.initialize();
    }

    /**
     * Function calls to empty the loaded deliveries.
     */
    public static void emptyLoadedDeliveries() {
	tourCalculator.emptyLoadedDeliveries();
    }

    /**
     * Function calls to empty the tour factory.
     */
    public static void emptyTourFactory() {
	tourFactory.empty();
    }

    /**
     * Run Dijkstra's algorithm on the plan.
     * 
     * @param toAdd is the intersection to add.
     */
    public static void addIntersection(Intersection toAdd) {
	plan.addIntersection(toAdd);
    }

    /**
     * Add a section to the plan.
     * 
     * @param toAdd is the section to add.
     */
    public static void addSection(Section toAdd) {
	plan.addSection(toAdd);
    }

    /**
     * Run Dijkstra's algorithm on the plan
     * 
     * @param sourceIntersection is the intersection from where we run the
     *                               algorithm.
     * @return Pair, a pair with as the first member distances, and as the second
     *         member predecessors.
     */
    public static Pair<HashMap<Long, Double>, HashMap<Long, Long>> dijkstra(Intersection sourceIntersection) {
	return plan.dijkstra(sourceIntersection);
    }

    /**
     * Run kMeans algorithm on the plan.
     * 
     * @param clustersCount is number of cluster needed.
     * @param deliveries    is list of deliveries to clusterize.
     * @param d             is epsilon parameter for kMeans.
     * @return List, the list of calculated clusters.
     */
    public static List<Cluster> kMeans(int clustersCount, List<Delivery> deliveries, double d) {
	return tourCalculator.kMeans(clustersCount, deliveries, d);
    }

    /**
     * Getter for the plan.
     * 
     * @return Plan, the plan.
     */
    public static Plan getPlan() {
	return plan;
    }

    /**
     * Getter for the tour calculator.
     * 
     * @return TourCalculator, the tour calculator.
     */
    public static TourCalculator getTourCalculator() {
	return tourCalculator;
    }

    /**
     * Get all the deliveries loaded in the model.
     * 
     * @return List, a list of deliveries.
     */
    public static List<Delivery> getDeliveries() {
	return tourCalculator.getDeliveries();
    }

    /**
     * Get the tour planning.
     * 
     * @return List, a list of all the tours calculated by the calculator.
     */
    public static List<Tour> getTourPlanning() {
	return tourFactory.getTourPlanning();
    }

    /**
     * Get the depot loaded in the model.
     * 
     * @return Delivery, the depot loaded in the model.
     */
    public static Delivery getDepot() {
	return tourCalculator.getDepot();
    }

    /**
     * Getter for the delivery men count.
     * 
     * @return int, the count of delivery men
     */
    public static int getDeliveryMenCount() {
	return tourCalculator.getDeliveryMenCount();
    }

    /**
     * Find the deliveries for a specified delivery man id
     * 
     * @param deliveryManId the specified delivery man id
     * @return List<Delivery>, the deliveries's List of a delivery man with an id ==
     *         deliveryManId.
     */
    public static List<Delivery> getDeliveriesById(int deliveryManId) {
	return tourFactory.getDeliveriesById(deliveryManId);
    }

    /**
     * Set the delivery men count in the tour calculator.
     * 
     * @param count is the number of delivery men to use.
     */
    public static void setDeliveryMenCount(int count) {
	tourCalculator.setDeliveryMenCount(count);
    }

    /**
     * Setter for the depot for the tours.
     * 
     * @param depot is the Delivery corresponding to the depot.
     */
    public static void setDepot(Delivery depot) {
	tourCalculator.setDepot(depot);
    }

    /**
     * Setter for the map.
     * 
     * @param map is the plan for the deliveries.
     */
    public static void setMap(Plan map) {
	plan = map;
	tourCalculator.setMap(map);
    }

}
