package model;

import java.util.ArrayList;
import java.util.List;

public class TourCalculator {

	private TourFactory tourFactory;
	private Plan map;
	private List<Delivery> deliveryRequest;
    /** Instance unique pré-initialisée */
	private static TourCalculator INSTANCE = new TourCalculator();
	
	private TourCalculator(){
		tourFactory = TourFactory.getInstance();
		deliveryRequest = new ArrayList<>();
	}
	
	public static TourCalculator initialiseTourCalculator(Plan map, List<Delivery> deliveryRequest){
		INSTANCE.deliveryRequest = deliveryRequest;
		INSTANCE.map = map;
		return INSTANCE;
	}
	
	public static TourCalculator getInstance(){
		return INSTANCE;
	}
}
