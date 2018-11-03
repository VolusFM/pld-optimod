package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

public class LoadedPlanState extends DefaultState {

	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {
		XMLDeserializer.load(controler.getModel().getPlan(), controler.getModel().getTourCalculator());		
		controler.setCurrentState(controler.loadedDeliveriesState);
	}

	public void openParameters(Controler controler,Window window) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
	
	public String stateToString() {
		return "loadedPlanState";
	}
}
