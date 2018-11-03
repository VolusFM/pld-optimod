package main.controler;

import main.ui.Window;

public class AddDeliveryState extends DefaultState{
	//TODO : check in which states a delivery can be added
	
	/**
	 * Confirms the delivery's addition
	 * @param controler
	 * @param window
	 */
	public void addDelivery(Controler controler, Window window) {
		
	}
	/**
	 * Cancel the new delivery and go back to planningState
	 * @param controler
	 * @param window
	 */
	public void cancelNewDelivery(Controler controler, Window window) {
	//For now, the state is set to tourCalculated
		controler.setCurrentState(controler.planningState);
	}
	
	public void confirmNewDelivery(Controler controler, Window window) {
		//TODO: forward changes to model
		controler.setCurrentState(controler.planningState);
	}
	
	public String stateToString() {
		return "addDeliveryState";
	}

}
