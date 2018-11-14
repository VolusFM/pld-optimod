package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;;

/**
 * InitState is the initial state of the application, when it's just been
 * started.
 *
 */
class InitState extends DefaultState {

    /**
     * Load a plan's XML file.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void openPlan(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException {
	XMLDeserializer.load(ModelInterface.getPlan());

	window.displayPlanView();
	window.displayDeliveryRequestSelectionPanel();

	controler.setCurrentState(controler.loadedPlanState);
    }

    /**
     * Get the name of the state for debug purposes.
     * 
     * @return String, the name of the state.
     */
    public String stateToString() {
	return "initState";
    }
}
