package model;

import java.util.List;

public class TourCalculator {

	public Tour createTour(List <Section> stepList, List <Delivery> deliveryPoints){
		Tour currentTour = new Tour (stepList, deliveryPoints) ;
		return currentTour;
	}
}
