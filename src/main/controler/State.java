package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.Intersection;
import main.model.Section;
import main.ui.Window;
import main.xml.XMLException;

public interface State {
	/**
	 * Open the map
	 * 
	 * @param controler
	 * @param window
	 */
	public void openPlan(Controler controler, Window window) throws XMLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Open the deliveries request
	 * 
	 * @param controler
	 * @param window
	 * @throws XMLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void openDeliveries(Controler controler, Window window) throws XMLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Open parameters window
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void openParameters(Controler controler, Window window);

	/**
	 * Calculate a tour planning with a given number of tour
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void calculatePlanning(Controler controler, Window window);

	/**
	 * Cancel the creation of a new delivery and go back to previous screen
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void cancelNewDelivery(Controler controler, Window window);

	/**
	 * Open the Add Delivery Window
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void addDelivery(Controler controler, Window window);

	/**
	 * Confims parameters change
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void confirmParameters(Controler controler, Window window);

	/**
	 * Delete delivery
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void deleteDelivery(Controler controler, Window window);

	/**
	 * Confirm the addition of a new delivery
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void confirmNewDelivery(Controler controler, Window window);

	/**
	 * Clicked near an Intersection
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 * @param intersection
	 *            the closest intersection from the click
	 */
	public void clickedNearIntersection(Controler controler, Window window, Intersection intersection);

	/**
	 * Clicked near a section
	 * 
	 * @param controler
	 *            the current controller
	 * @param window
	 *            the application window
	 */
	public void clickedNearSection(Controler controler, Window window, Section section);

	/**
	 * Get the state to a string for test use.
	 */
	public String stateToString();
}
