package main.model;

public abstract class ModelInterface {
	
	private static Plan plan;
	private static TourCalculator tourCalculator;
	
	public static Plan getPlan(){
		return plan;
	}
	
	public static TourCalculator getTourCalculator(){
		return tourCalculator;
	}
	
	public static void setPlan(Plan p){
		plan = p;
	}
	
	public static void setTourCalculator(TourCalculator c){
		tourCalculator = c;
	}
}
