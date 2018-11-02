package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.ui.Window;
import main.xml.XMLException;

public interface State {
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException;

	public void openParameters(Controler controler, Window window);

	public void calculateTour(Controler controler, Window window);
}
