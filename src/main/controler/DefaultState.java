package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.RangeSelector;
import main.ui.RangeSelector.SelectionCancelledException;
import main.ui.Window;
import main.xml.XMLException;;

public class DefaultState implements State{
	
	//TODO : check @Override annotation function
	
	public void openPlan(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {
	}

	public void openDeliveries(Controler controler, Window window)
			throws XMLException, ParserConfigurationException, SAXException, IOException {}
	
	public void openParameters(Controler controler, Window window){}
	
	public void calculatePlanning(Controler controler, Window window){}
	
	public void addDelivery(Controler controler, Window window){
	}
	
	public void cancelNewDelivery(Controler controler, Window window) {}
	
	public String stateToString() {
		//TODO : remove after tests
		System.out.println("Stuck in default state");
		return null;
	}

	@Override
	public void confirmParameters(Controler controler, Window window) {}

	@Override
	public void confirmNewDelivery(Controler controler, Window window) {}

	
	/*public void openParameters(Controler controler, Window window) {
		// XXX : for testing purposes only, need to get the good range
		// TODO : move this in the appropriate state (likeli
		// LoadedDeliveryState)
		try {
			ModelInterface.setDeliveryMenCount(RangeSelector.getIntegerInRange(1, 42));
		} catch (SelectionCancelledException e) {
			System.out.println("Selection was cancelled, ignoring...");
		}
	}*/
}
