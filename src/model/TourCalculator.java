package model;

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

	private static TourCalculator init(Plan map, List<Delivery> deliveries) {
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
}
