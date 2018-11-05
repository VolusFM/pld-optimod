package main.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
		//calculateDeliveryHours(steps, depot, deliveries);
		Tour currentTour = new Tour(steps, deliveries, deliveryMan);
		tourPlanning.add(currentTour);
		return currentTour;
	}

	public static TourFactory getInstance() {
		return INSTANCE;
	}

	protected List<Tour> getTourPlanning() {
		return tourPlanning;
	}

	private static void calculateDeliveryHours(List<Step> steps, Delivery depot, List<Delivery> deliveries) {
		Calendar departureTime = depot.getHour();
		for (Step s : steps) {
			
		}
	}
}
