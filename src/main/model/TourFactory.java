package main.model;

import java.util.ArrayList;
import java.util.List;

public class TourFactory {

	private static List<Tour> tourPlanning;
	/** Instance unique pré-initialisée */
	private static TourFactory INSTANCE = new TourFactory();

	private TourFactory() {
		this.tourPlanning = new ArrayList<>();
	}

	public static Tour createTour(int deliveryMan, List<Step> steps, List<Delivery> deliveryPoints) {
		Tour currentTour = new Tour(steps, deliveryPoints, deliveryMan);
		tourPlanning.add(currentTour);
		return currentTour;
	}

	public static TourFactory getInstance() {
		return INSTANCE;
	}

	protected List<Tour> getTourPlanning() {
		return tourPlanning;
	}

}
