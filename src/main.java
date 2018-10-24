/**
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Delivery;
import model.Plan;
import model.TourCalculator;
import xml.XMLDeserializer;
import xml.XMLException;

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
