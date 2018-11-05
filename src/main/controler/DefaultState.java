package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.ui.Window;
import main.xml.XMLException;;

public class DefaultState implements State{
	
	//TODO : check @Override annotation function
	
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {
	}

	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {}
	
	public void openParameters(Controler controler, Window window){}
	
	public void calculatePlanning(Controler controler, Window window){}
	
	public void addDelivery(Controler controler, Window window){
	}
	
	public void cancelNewDelivery(Controler controler, Window window) {}
	
	public String stateToString() {
		//TODO : remove after tests
		System.out.println("Stuck in default state");
		return null;
	}
	public void deleteDelivery(Controler controler, Window window){}

	@Override
	public void confirmParameters(Controler controler, Window window) {}

	@Override
	public void confirmNewDelivery(Controler controler, Window window) {}
}
