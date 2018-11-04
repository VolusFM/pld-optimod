package main;

import main.model.Plan;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;

/**
 * 
 */

/**
 * @author Montigny
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		
		Plan plan = new Plan();
		XMLDeserializer.load(plan);
//		TourCalculator.init(plan, null, null);
		
		
		XMLDeserializer.load(plan, TourCalculator.getInstance());
		TourCalculator.getInstance().setMap(plan);
		
		TourCalculator.getInstance().calculateTours(1);
	}

}
