package main.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
		calculateDeliveryHours(steps, depot, deliveries);
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
			List<Section> sections = s.getSections();
			long lastIntersectionId = sections.get(sections.size() - 1).getEnd().getId();
			boolean foundDelivery = false;

			Iterator<Delivery> it = deliveries.iterator();
			Delivery delivery = depot; // If we don't find the delivery, it's
										// the depot

			while (!foundDelivery && it.hasNext()) {
				Delivery d = it.next();

				if (lastIntersectionId == d.getAddress().getId()) {
					delivery = d;
					foundDelivery = true;
				}
			}

			long travelTimeInSeconds = Math.round(s.calculateLength() * 3600 / 15000);

			departureTime.add(Calendar.SECOND, (int) travelTimeInSeconds);
			Calendar deliveryTime = (Calendar) departureTime.clone();
			delivery.setHour(deliveryTime);

			departureTime.add(Calendar.SECOND, delivery.getDuration());

			System.out.println(travelTimeInSeconds);
			System.out.println(s.calculateLength());

		}
	}

	public Step findStepBeforeDelivery(Delivery delivery) {
		for (Tour tour : tourPlanning) {
			if (tour.getDeliveryPoints().contains(delivery)) {
				for (Step step : tour.getSteps()) {
					 // XXX == or equals ?
					if (step.getEndDelivery() == delivery) {
						return step;
					}
				}
			}
		}
		return null;
	}

	// public List<Tour> findToursContainingSection(Section section) {
	// ArrayList<Tour> tours = new ArrayList<>();
	// for (Tour tour : tourPlanning) {
	// for (Step st : tour.getSteps()) {
	// for (Section s : st.getSections()) {
	// if (section.equals(s)) {
	// tours.add(tour);
	// }
	// }
	// }
	// }
	//
	// if (tours.size() == 0) {
	// System.out.println("NO TOUR FOUND");
	// tours.add(tourPlanning.get(0));
	// }
	//
	// return tours;
	// }
}
