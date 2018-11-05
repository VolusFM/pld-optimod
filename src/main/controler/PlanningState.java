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
		// int newTourId = window.getNewTourId();
		//Delivery movedDelivery = window.getMovedDelivery();
		//ModelInterface.moveDelivery(newTourId, movedDelivery);
	}
	public void deleteDelivery(Controler controler, Window window){
		//ModelInterface.deleteDelivery(window.getDeletedDelivery());
	}
	public void addDelivery(Controler controler, Window window) {
		//TODO : window.openNewDelivery()
		controler.setCurrentState(controler.addState);
	}
	
	public String stateToString() {
		return "planningState";
	}
}
