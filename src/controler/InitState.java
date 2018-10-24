package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Plan;
import ui.Window;
import xml.XMLDeserializer;
import xml.XMLException;

public class InitState extends DefaultState {
	
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {
		XMLDeserializer.load(controler.getModel().getPlan());
		window.displayPlanView();
		controler.setCurrentState(controler.loadedPlanState);
	}

	public void openParameters(Controler controler) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
}
