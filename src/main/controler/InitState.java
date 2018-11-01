package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.ui.Window;
import main.xml.XMLDeserializer;
import main.xml.XMLException;;

public class InitState extends DefaultState {
	
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException 
	{
		XMLDeserializer.load(controler.getModel().getPlan());
		window.displayPlanView();
		controler.setCurrentState(controler.loadedPlanState);
	}

	public void openParameters(Controler controler) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
}
