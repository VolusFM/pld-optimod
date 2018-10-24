package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.XMLException;
//TODO window change must be handled by the controler -> parameter / state methods.
public interface State {
	/**
	 * Method called when the "accueil" screen s button "Valider" is pushed.
	 * @param controler 
	 * @param window
	 */
	public void openPlan(Controler controler, Window window);
	/**
	 * Method called when the "plan" screen s button "Valider" is pushed.
	 * @param controler 
	 * @param window
	 */
	public void openDeliveries(Controler controler, Window window);
	/**
	 * Method called when the button "Parametres" is pushed.
	 * @param controler 
	 * @param window
	 */
	public void openParameters(Controler controler, Window window);
	/**
	 * Method called when the button "planifier des tournees" is pushed.
	 * @param controler 
	 * @param window
	 */
	public void calculateTour(Controler controler, Window window);
	/**
	 * Method called when the "+" button is pushed.
	 * @param controler 
	 * @param window
	 */
	public void addDelivery(Controler controler, Window window);
}
