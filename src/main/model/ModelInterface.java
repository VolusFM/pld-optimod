package main.model;

import java.util.List;

/**
 * ModelInterface provides all methods other packages need to access the model
 * package.
 */
public abstract class ModelInterface {

    private static Plan plan = new Plan();
    private static TourCalculator tourCalculator = TourCalculator.getInstance();
    private static TourFactory tourFactory = TourFactory.getInstance();

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
     * Set a new plan in the interface.
     * 
     * @param p is the new plan to use.
     */
    public static void setPlan(Plan p) {
	plan = p;
    }

    /**
     * Set a new tour calculator in the interface.
     * 
     * @param tc is the new tour calculator to use.
     */
    public static void setTourCalculator(TourCalculator tc) {
	tourCalculator = tc;
    }

    /**
     * Set a new tour factory in the interface.
     * 
     * @param tf is the new tour factory to use.
     */
    public static void setTourFactory(TourFactory tf) {
	tourFactory = tf;
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
     * Get all the deliveries loaded in the model.
     * 
     * @return List, a list of deliveries.
     */
    public static List<Delivery> getDeliveries() {
	return tourCalculator.getDeliveries();
    }

    /**
     * Add a delivery to the tour calculator.
     * 
     * @param toAdd is the delivery to add.
     */
    public static void addDelivery(Delivery toAdd) {
	setTourCalculator(TourCalculator.getInstance());
	tourCalculator.addDelivery(toAdd);
    }
    
    public static void removeDelivery(Delivery toSuppress) {
	Tour t = tourFactory.findTourContainingDelivery(toSuppress);
	System.out.println(t);
	tourCalculator.removeDeliveryFromTour(toSuppress, t);
    }

    /**
     * Get the tour planning.
     * 
     * @return List, a list of all the tours calculated by the calculator.
     */
    public static List<Tour> getTourPlanning() {
	return tourFactory.getTourPlanning();
    }
    
    public static Tour findTourContainingDelivery(Delivery delivery){
	return tourFactory.findTourContainingDelivery(delivery);
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
     * Find the closest section in the plan, given a set of coordinates.
     * 
     * @param latitude is the latitude of the search location.
     * @param longitude is the latitude of the search location.
     * @return Section, the section closest to the search location.
     */
    public static Section findClosestSection(double latitude, double longitude) {
	return plan.findClosestSection(latitude, longitude);
    }

    /**
     * Find the closest intersection in the plan, given a set of coordinates.
     * 
     * @param latitude is the latitude of the search location.
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
     * @return Delivery, the delivery corresponding to the intersection, or null
     *         if there is none.
     */
    public static Delivery findCorrespondingDelivery(Intersection intersection) {
	return tourCalculator.findCorrespondingDelivery(intersection);
    }

    public static void createGraph() {
	tourCalculator.createGraph();
    }
    
    /**
     * Getter for the delivery men count.
     * @return int, the count of delivery men
     */
    public static int getDeliveryMenCount(){
	return tourCalculator.getDeliveryMenCount();
    }
    
    /**
     * Find the deliveries for a specified delivery man id
     * @param deliveryManId the specified delivery man id
     * @return
     */
    public static List<Delivery> getDeliveriesById(int deliveryManId){
	return tourFactory.getDeliveriesById(deliveryManId);
    }
    
    /**
     * Function calls to empty the tour factory
     */
    public static void emptyTourFactory(){
	tourFactory.empty();
    }
    
}
