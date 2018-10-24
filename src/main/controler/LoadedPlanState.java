package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

public class LoadedPlanState extends DefaultState {

	public void openDeliveries(Controler controler)
			throws XMLException, ParserConfigurationException, SAXException, IOException {
		XMLDeserializer.load(ModelInterface.getPlan(), ModelInterface.getTourCalculator());		
		controler.setCurrentState(controler.loadedDeliveriesState);
	}

	public void openParameters(Controler controler) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
}
