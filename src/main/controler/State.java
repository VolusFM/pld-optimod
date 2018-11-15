package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.Intersection;
import main.model.Section;
import main.ui.Window;
import main.xml.XMLException;

/**
 * State provides an interface for all classes representing a possible state of
 * the controler.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
interface State {

    /**
     * Open the plan.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     * @throws XMLException                 if the file's contents are invalid.
     * @throws ParserConfigurationException if there's a problem when configuring
     *                                          the parser.
     * @throws SAXException                 if there's a problem when parsing.
     * @throws IOException                  if there's a problem during a reading
     *                                          operation.
     */
    public void openPlan(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Open the deliveries request.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     * @throws XMLException                 if the file's contents are invalid.
     * @throws ParserConfigurationException if there's a problem when configuring
     *                                          the parser.
     * @throws SAXException                 if there's a problem when parsing.
     * @throws IOException                  if there's a problem during a reading
     *                                          operation.
     */
    public void openDeliveries(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Calculate a tour planning with a given number of delivery men.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void calculatePlanning(Controler controler, Window window);

    /**
     * Open the Add Delivery Window.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void addDelivery(Controler controler, Window window);

    /**
     * Confirm the addition of a new delivery.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void confirmNewDelivery(Controler controler, Window window);

    /**
     * Cancel the creation of a new delivery and go back to previous screen.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void cancelNewDelivery(Controler controler, Window window);

    /**
     * Delete a delivery.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void removeDelivery(Controler controler, Window window);

    /**
     * Move a delivery from a tour to another.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void moveDelivery(Controler controler, Window window);

    /**
     * Open parameters window.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void openParameters(Controler controler, Window window);

    /**
     * Handle a click near an Intersection.
     * 
     * @param controler           is the application's controler.
     * @param window              is the application's graphical window.
     * @param closestIntersection the intersection closest to the click.
     */
    public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection);

    /**
     * Handle a click near a Section.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     * @param section   the section closest to the click.
     */
    public void clickedNearSection(Controler controler, Window window, Section section);

    /**
     * Return to a given state.
     * 
     * @param controler   is the application's controler.
     * @param window      is the application's graphical window.
     * @param returnState is the state to which we want to return.
     */
    public void returnToState(Controler controler, Window window, State returnState);

    /**
     * Get the name of the state for debug purposes.
     * 
     * @return String, the name of the state.
     */
    public String stateToString();

}
