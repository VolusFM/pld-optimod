package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

/**
 * LoadedPlanState is the state in which only the plan has been loaded
 *
 */
public class LoadedPlanState extends DefaultState {

    public void openDeliveries(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException {

	XMLDeserializer.load(ModelInterface.getPlan(), ModelInterface.getTourCalculator());
	window.displayCalculateTourButtonPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	window.toggleReturnButtonVisibility();
	controler.setCurrentState(controler.loadedDeliveriesState);
    }

    public String stateToString() {
	return "loadedPlanState";
    }
}
