package main.model;

import java.util.ArrayList;
import java.util.List;

public class TourCalculator {

	private TourFactory tourFactory;
	private Plan map;
	private List<Delivery> deliveries;
	/* Unique instance */
	private static TourCalculator instance = null;

	private TourCalculator() {
		tourFactory = TourFactory.getInstance();
		deliveries = new ArrayList<>();
	}

	public static TourCalculator init(Plan map, List<Delivery> deliveries) {
		instance.deliveries = deliveries;
		instance.map = map;
		return instance;
	}

	public static TourCalculator getInstance() {
		if (instance == null) {
			instance = new TourCalculator();
		}
		return instance;
	}

	public void addDelivery(Delivery d) {
		this.deliveries.add(d);
	}

	public void print() {
		for (Delivery d : deliveries) {
			System.out.println("got delivery with intersection id :" + d.getAddress().getId());
		}
	}

	protected List<Delivery> getDeliveries() {
		return deliveries;
	}

}
