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
		
//		tourCalculator.setDeliveryMenCount(count);
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
	
	public static Delivery getDepot() {
		return tourCalculator.getDepot();
	}

	public static Section findClosestSection(double latitude, double longitude) {
		return plan.findClosestSection(latitude, longitude);
	}

	public static Intersection findClosestIntersection(double latitude, double longitude) {
		return plan.findClosestIntersection(latitude, longitude);
	}
}
