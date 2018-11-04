package main.controler;

import main.ui.Window;

public class AddDeliveryState extends DefaultState{
	
	/**
	 * Confirms the delivery's addition
	 * @param controler
	 * @param window
	 */
	public void addDelivery(Controler controler, Window window) {
		
	}
	/**
	 * Cancel the new delivery and go back to TourCalculatedState
	 * @param controler
	 * @param window
	 */
	public void cancelNewDelivery(Controler controler, Window window) {
	//For now, the state is set to tourCalculated
		controler.setCurrentState(controler.planningState);
	}
	
	public String stateToString() {
		return "AddDeliveryState";
	}

}
