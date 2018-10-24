package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.XMLException;

public class DefaultState implements State{
	public void openPlan(Controler controler, Window window){}
	public void openDeliveries(Controler controler, Window window){}
	public void openParameters(Controler controler, Window window){}
	public void calculateTour(Controler controler, Window window){}
	public void addDelivery(Controler controler, Window window){}
}
