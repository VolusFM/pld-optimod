package main;

import main.controler.Controler;

/**
 * @author Montigny
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {

//		Plan plan = new Plan();
//		XMLDeserializer.load(plan);
////		TourCalculator.init(plan, null, null);
//		TourCalculator.getInstance().setMap(plan);
//
//		XMLDeserializer.load(plan, TourCalculator.getInstance());
//
//		TourCalculator.getInstance().calculateTours(1);
		
		new Controler();
	}

}
