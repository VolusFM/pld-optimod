package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.xml.XMLException;
import main.ui.Window;

public interface State {
	/**
	 * 
	 * @param controleur
	 * @param fenetre
	 */
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	public void openParameters(Controler controler, Window window);

	public void calculateTour(Controler controler, Window window);
}
