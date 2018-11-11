package main.controler;

import main.model.Delivery;
import main.model.ModelInterface;
import main.ui.Window;

public class AddDeliveryState extends DefaultState {

	/**
	 * Cancel the new delivery and go back to planningState.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void cancelNewDelivery(Controler controler, Window window) {
		controler.setCurrentState(controler.planningState);
	}

	/**
	 * Confirms the delivery's addition.
	 * 
	 * @param controler is the application's controler.
	 * @param window    is the application's graphical window.
	 */
	public void confirmNewDelivery(Controler controler, Window window) {

		Delivery toAdd = new Delivery(0, null);
		// TODO: get actual info from window
		ModelInterface.addDelivery(toAdd);
		controler.setCurrentState(controler.planningState);
	}

	public String stateToString() {
		return "addDeliveryState";
	}

}
