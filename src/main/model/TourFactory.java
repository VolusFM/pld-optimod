package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * TourFactory handles the creation and stockage of Tour objects.
 *
 */
public class TourFactory {

    private static List<Tour> tourPlanning;
    /** Instance unique pré-initialisée */
    private static TourFactory instance = new TourFactory();

    private TourFactory() {
	tourPlanning = new ArrayList<>();
    }

    /**
     * Create a tour.
     * 
     * @param deliveryMan is the delivery man who has to realize the tour.
     * @param steps       are the steps between each delivery.
     * @param depot       is the depot where the tour starts and ends.
     * @param deliveries  is the list of delivery points.
     * @return Tour, the created tour.
     */
    public static Tour createTour(int deliveryMan, List<Step> steps, Delivery depot, List<Delivery> deliveries) {
	Tour currentTour = new Tour(depot, steps, deliveries, deliveryMan);
	currentTour.calculateDeliveryHours();
	tourPlanning.add(currentTour);
	return currentTour;
    }

    /**
     * Get the unique instance of the class.
     * 
     * @return TourFactory, the unique instance of the class.
     */
    public static TourFactory getInstance() {
	return instance;
    }

    /**
     * Getter for the planning of tour.
     * 
     * @return List, a list of all the calculated Tour objects.
     */
    protected List<Tour> getTourPlanning() {
	return tourPlanning;
    }

    /**
     * Find the step leading to a delivery.
     * 
     * @param delivery is the delivery at the end of the step we're looking for.
     * @return Step, the Step leading to the delivery.
     */
    protected Step findStepBeforeDelivery(Delivery delivery) {
	for (Tour tour : tourPlanning) {
	    if (tour.getDeliveryPoints().contains(delivery)) {
		for (Step step : tour.getSteps()) {
		    if (step.getEndDelivery().equals(delivery)) {
			return step;
		    }
		}
	    }
	}

	return null;
    }

    /**
     * Get the tours containing a specific section.
     * 
     * @param section is the section on which to filter the results.
     * @return List, the list of tours containing the given section.
     */
    public List<Tour> findToursContainingSection(Section section) {
	ArrayList<Tour> tours = new ArrayList<>();
	for (Tour tour : tourPlanning) {
	    for (Step st : tour.getSteps()) {
		for (Section s : st.getSections()) {
		    if (section.equals(s)) {
			tours.add(tour);
		    }
		}
	    }
	}

	if (tours.size() == 0) {
	    System.out.println("NO TOUR FOUND");
	    tours.add(tourPlanning.get(0));
	}

	return tours;
    }

    /**
     * Find deliveries for a given delivery man id.
     * 
     * @param deliveryManId is the id of the delivery man on which to filter the
     *                      results.
     * @return the list of deliveries given to the delivery man
     */
    public List<Delivery> getDeliveriesById(int deliveryManId) {
	int i = 0;
	while ((tourPlanning.get(i).getDeliveryManId() != deliveryManId) && (i < tourPlanning.size())) {
	    i = i + 1;
	}
	return tourPlanning.get(i).getDeliveryPoints();
    }

    /**
     * Find the tour containing a specific delivery.
     * 
     * @param delivery is the delivery on which to filter the results.
     * @return Tour, the tour containing the delivery.
     */
    public Tour findTourContainingDelivery(Delivery delivery) {
	for (Tour tour : tourPlanning) {
	    if (tour.getDeliveryPoints().contains(delivery)) {
		return tour;
	    }
	}
	return null;
    }
}
