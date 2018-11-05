package main.model;

import java.util.ArrayList;
import java.util.List;

public class Tour {

	private List<Step> steps;
	private List<Delivery> deliveryPoints;
	private int deliveryManId;

	public Tour(List<Step> steps, List<Delivery> deliveryPoints, int deliveryManId) {
		this.steps = new ArrayList<>(steps);
		this.deliveryPoints = new ArrayList<>(deliveryPoints);

		this.deliveryManId = deliveryManId;
	}

	public List<Delivery> getDeliveryPoints() {
		return deliveryPoints;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public int getDeliveryManId() {
		return deliveryManId;
	}
}
