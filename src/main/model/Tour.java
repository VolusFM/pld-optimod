package main.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Tour {

	private Delivery depot;
	private List<Step> steps;
	private List<Delivery> deliveryPoints;
	private int deliveryManId;

	public Tour(Delivery depot, List<Step> steps, List<Delivery> deliveryPoints, int deliveryManId) {
		this.depot = depot;
		this.steps = new ArrayList<>(steps);
		this.deliveryPoints = new ArrayList<>(deliveryPoints);

		this.deliveryManId = deliveryManId;
	}

	public List<Delivery> getDeliveryPoints() {
		List<Delivery> deliveriesAndDepot = new ArrayList<>(deliveryPoints);
		deliveriesAndDepot.add(depot);
		return deliveriesAndDepot;
	}

	public void removeDelivery(Delivery delivery) {
		deliveryPoints.remove(delivery);
	}

	public List<Step> getSteps() {
		return steps;
	}

	public int getDeliveryManId() {
		return deliveryManId;
	}

	public void calculateDeliveryHours() {
		Calendar departureTime = (Calendar) depot.getHour().clone();

		for (Step s : steps) {
			List<Section> sections = s.getSections();
			long lastIntersectionId = sections.get(sections.size() - 1).getEnd().getId();
			boolean foundDelivery = false;

			Iterator<Delivery> it = deliveryPoints.iterator();
			Delivery delivery = depot;
			// If we don't find the delivery, it's the depot

			while (!foundDelivery && it.hasNext()) {
				Delivery d = it.next();

				if (lastIntersectionId == d.getAddress().getId()) {
					delivery = d;
					foundDelivery = true;
				}
			}

			if (delivery.equals(depot)) {
				// XXX : we don't update the depot, it messes stuff up if we
				// recalculate durations
				continue;
			}

			long travelTimeInSeconds = Math.round(s.calculateLength() * 3600 / 15000);

			departureTime.add(Calendar.SECOND, (int) travelTimeInSeconds);
			Calendar deliveryTime = (Calendar) departureTime.clone();
			delivery.setHour(deliveryTime);

			System.out.println(delivery.getAddress().getId() + ":" + deliveryTime.getTime().toString());

			departureTime.add(Calendar.SECOND, delivery.getDuration());
		}

		// FIXME : doesnt work well when tour ends the next day
	}
}
