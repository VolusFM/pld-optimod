package main.model;

import java.util.ArrayList;
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

	public List<Step> getSteps() {
		return steps;
	}

	public int getDeliveryManId() {
		return deliveryManId;
	}
}
