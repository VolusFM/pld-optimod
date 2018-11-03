package main.controler;

import main.ui.Window;

public class LoadedDeliveriesState extends DefaultState {
	public void openParameters(Controler controler, Window window) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}

	public void calculatePlanning(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
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
