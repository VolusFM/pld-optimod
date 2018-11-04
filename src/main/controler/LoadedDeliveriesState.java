package main.controler;

import main.model.TourCalculator;
import main.ui.Window;

public class LoadedDeliveriesState extends DefaultState {
	public void openParameters(Controler controler) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}

	public void calculateTour(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
		
		TourCalculator.getInstance().calculateTours(1);
		window.invalidate(); // FIXME : force window to display again with path
		
		controler.setCurrentState(controler.planningState);
	}
	
	public void moveDelivery(Controler controler, Window window) {
		//TODO
	}
	
	public void addDelivery(Controler controler, Window window) {
		//TODO : window.openNewDelivery
		controler.setCurrentState(controler.addState);
	}
	
	public String stateToString() {
		return "loadedDeliveryState";
	}
}
