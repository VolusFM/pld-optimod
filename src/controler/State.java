package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.XMLException;

public interface State {
	/**
	 * 
	 * @param controleur 
	 * @param fenetre
	 */
	public void openPlan(Controler controler)throws XMLException, ParserConfigurationException, SAXException, IOException;
	public void openDeliveries(Controler controler) throws  XMLException, ParserConfigurationException, SAXException, IOException;
	public void openParameters(Controler controler);
	public void calculateTour(Controler controler);
}
