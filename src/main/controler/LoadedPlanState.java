package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

public class LoadedPlanState extends DefaultState {

	public void openDeliveries(Controler controler, Window window) throws XMLException, ParserConfigurationException, SAXException, IOException {

		XMLDeserializer.load(ModelInterface.getPlan(), ModelInterface.getTourCalculator());
		window.displayCalculateTourButtonPanel();
		window.toggleDeliveryMenCountButtonVisiblity();

		controler.setCurrentState(controler.loadedDeliveriesState);
	}

	/**
	 * Open parameters modal.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void openParameters(Controler controler, Window window) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}

	public String stateToString() {
		return "loadedPlanState";
	}
}
