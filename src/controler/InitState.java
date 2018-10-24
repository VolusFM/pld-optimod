package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Plan;
import xml.XMLDeserializer;
import xml.XMLException;

public class InitState extends DefaultState{
	
	@Override
	public void openPlan(Controler controler, Window window)throws XMLException, ParserConfigurationException, SAXException, IOException{
		XMLDeserializer.load(controler.getModel().getPlan());
		controler.setCurrentState(controler.loadedPlanState);
	}
	
	@Override
	public void openParameters(Controler controler, Window window){
		//TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
}
