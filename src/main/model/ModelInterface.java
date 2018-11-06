package main.model;

import java.util.List;

public abstract class ModelInterface {


	private static Plan plan = new Plan();
	private static TourCalculator tourCalculator = TourCalculator.getInstance();
	private static TourFactory tourFactory = TourFactory.getInstance();

	public static Plan getPlan() {
		return plan;
	}

	public static TourCalculator getTourCalculator() {
		return tourCalculator;
	}

	public static void setPlan(Plan p) {
		plan = p;
	}

	public static void setTourCalculator(TourCalculator c) {
		tourCalculator = c;
	}

	public static void setTourFactory(TourFactory f) {
		tourFactory = f;
	}

	public static void setDeliveryMenCount(int count) {
		System.out.println("setDeliveryMenCount to " + count); // FIXME do actual implementation
		throw new RuntimeException("NYI");
	}

	public static List<Delivery> getDeliveries() {
		return tourCalculator.getDeliveries();
	}

	public static void addDelivery(Delivery toAdd) {
		setTourCalculator(TourCalculator.getInstance());
		tourCalculator.addDelivery(toAdd);
	}

	public static List<Tour> getTourPlanning() {
		return tourFactory.getTourPlanning();
	}

	public static void createTour(int deliveryMan, List<Step> steps, List<Delivery> deliveryPoints) {
		setTourFactory(TourFactory.getInstance());
		TourFactory.createTour(deliveryMan, steps, deliveryPoints);
	}
	
	public static Delivery getDepot() {
		return tourCalculator.getDepot();
	}
	
	public static List<Cluster> kMeans(int clusterNb, List<Delivery> deliveries, double epsilon){
		return tourCalculator.kMeans(clusterNb, deliveries, epsilon);
	}
}
