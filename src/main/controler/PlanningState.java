package main.controler;

import main.ui.Window;

public class PlanningState extends DefaultState {
	
	//TODO : check if planning can be recalculated
	public void calculatePlanning(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
		controler.setCurrentState(controler.planningState);
	}
	
	public void openParameters(Controler controler) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
	
	public void moveDelivery(Controler controler, Window window) {
		//TODO
	}
	
	public void addDelivery(Controler controler, Window window) {
		//TODO : window.openNewDelivery
		controler.setCurrentState(controler.addState);
	}
	
	public String stateToString() {
		return "planningState";
	}
}
