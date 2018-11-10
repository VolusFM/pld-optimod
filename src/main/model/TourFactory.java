package main.model;

import java.util.ArrayList;
import java.util.List;

public class TourFactory {

	private static List<Tour> tourPlanning;
	/** Instance unique pré-initialisée */
	private static TourFactory INSTANCE = new TourFactory();

	private TourFactory() {
		tourPlanning = new ArrayList<>();
	}

	public static Tour createTour(int deliveryMan, List<Step> steps, Delivery depot, List<Delivery> deliveries) {
		System.out.println(depot);
		Tour currentTour = new Tour(depot, steps, deliveries, deliveryMan);
		currentTour.calculateDeliveryHours();
		tourPlanning.add(currentTour);
		return currentTour;
	}

	public static TourFactory getInstance() {
		return INSTANCE;
	}

	protected List<Tour> getTourPlanning() {
		return tourPlanning;
	}

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

		System.out.println("NULLLLLLL");
		return null;
	}
	
	/**
	 * find deliveries list for a delivery man ID.
	 * @param deliveryManId
	 * @return
	 */
	public List<Delivery> getDeliveriesById(int deliveryManId){
		int i=0;
		while( (tourPlanning.get(i).getDeliveryManId()!=deliveryManId )&&(i<tourPlanning.size()) ) {
			i=i+1;
		}
		return tourPlanning.get(i).getDeliveryPoints();
	}

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
	
	public Tour findToursContainingDelivery(Delivery delivery) {
		for(Tour tour : tourPlanning) {
			for(Delivery currentDelivery : tour.getDeliveryPoints()) {
				if (currentDelivery.equals(delivery)) {
					return tour;
				}
			}
		}
		return null;
	}
}
