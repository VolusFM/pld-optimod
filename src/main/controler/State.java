package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.xml.XMLException;
import main.ui.Window;

public interface State {
	/**
	 * Open the map
	 * @param controleur
	 * @param fenetre
	 */
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Open the deliveries request
	 * @param controler
	 * @param window
	 * @throws XMLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;
	
/**
 * Open parameters window
 * @param controler
 * @param window
 */
	public void openParameters(Controler controler, Window window);
	
/**
 * Calculate a tour planning with a given number of tour
 * @param controler
 * @param window
 */
	public void calculateTour(Controler controler, Window window);
	
	/**
	 * Cancel the creation of a new delivery and go back to previous screen
	 * @param controler
	 * @param window
	 */
	public void cancelNewDelivery(Controler controler, Window window);
	
	/**
	 * Open the Add Delivery Window
	 * @param controler
	 * @param window
	 */
	public void addDelivery(Controler controler, Window window);
	
	
	/**
	 * Get the state to a string for test use.
	 */
	public String stateToString();
}
