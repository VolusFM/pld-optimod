/**
 * 
 */
package main;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.Delivery;
import main.model.Plan;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

/**
 * @author Montigny
 *
 */
public class main {

	/**
	 * @param args
	 * @throws XMLException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws XMLException, ParserConfigurationException, SAXException, IOException {

	
		Plan plan = new Plan();
		XMLDeserializer.load(plan);
		TourCalculator calculator = TourCalculator.getInstance();
		TourCalculator.init(plan, new ArrayList<Delivery>());
		XMLDeserializer.load(plan, calculator);
		plan.print();
		calculator.print();
	}

}
