package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

/**
 * InitState is the initial state of the application, when it's just been
 * started.
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
class InitState extends DefaultState {

    @Override
    public void openPlan(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException {
	XMLDeserializer.load(ModelInterface.getPlan());

	window.displayPlanView();
	window.displayDeliveryRequestSelectionPanel();

	controler.setCurrentState(controler.loadedPlanState);
    }

    @Override
    public String stateToString() {
	return "initState";
    }
}
