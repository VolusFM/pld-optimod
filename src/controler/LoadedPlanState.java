package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.XMLDeserializer;
import xml.XMLException;

public class LoadedPlanState extends DefaultState{

	public void openDeliveries(Controler controler)throws XMLException, ParserConfigurationException, SAXException, IOException{
		XMLDeserializer.load(controler.getModel().getPlan(),controler.getModel().getTourCalculator());
		controler.setCurrentState(controler.loadedDeliveriesState);
	}
	
	public void openParameters(Controler controler){
		//TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
}
