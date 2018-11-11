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
	 * Open the plan.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Open the deliveries request.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 * @throws XMLException                 if the file's contents are invalid.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	/**
	 * Open parameters window.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void openParameters(Controler controler, Window window);

	/**
	 * Calculate a tour planning with a given number of delivery men.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void calculatePlanning(Controler controler, Window window);

	/**
	 * Cancel the creation of a new delivery and go back to previous screen.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void cancelNewDelivery(Controler controler, Window window);

	/**
	 * Open the Add Delivery Window.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void addDelivery(Controler controler, Window window);

	/**
	 * Confim parameters change.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void confirmParameters(Controler controler, Window window);

	/**
	 * Delete a delivery.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void deleteDelivery(Controler controler, Window window);

	/**
	 * Confirm the addition of a new delivery.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void confirmNewDelivery(Controler controler, Window window);

	/**
	 * Handle a click near an Intersection.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 * @param intersection the intersection closest to the click.
	 */
	public void clickedNearIntersection(Controler controler, Window window, Intersection intersection);

	/**
	 * Handle a click near a Section.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 * @param section the section closest to the click.
	 */
	public void clickedNearSection(Controler controler, Window window, Section section);

	/**
	 * XXX ?
	 * @param controler
	 * @param window
	 * @param intersection
	 */
	public void rightClickedNearIntersection(Controler controler, Window window, Intersection intersection);

	/**
	 * Get the state to a string for test use.
	 */
	public String stateToString();
}
