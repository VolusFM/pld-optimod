package main.controler;

import main.model.Delivery;
import main.model.ModelInterface;
import main.model.Tour;
import main.ui.Window;

/**
 * AddDeliveryState is the state in which the user is trying to add a new
 * delivery to the planning.
 *
 */
public class AddDeliveryState extends DefaultState {

    /**
     * Cancel the new delivery and go back to planningState.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    @Override
    public void cancelNewDelivery(Controler controler, Window window) {
	controler.getWindow().hideAddingDeliveryPanel();
	controler.setCurrentState(controler.planningState);
    }

    /**
     * Confirms the delivery's addition.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    @Override
    public void confirmNewDelivery(Controler controler, Window window, Delivery toAdd, Tour deliveryManTour, Delivery preceding) {
//	TODO Léo : change addDelivery(toAdd, deliveryManTour, preceding)
//	ModelInterface.addDelivery(toAdd);
	controler.getWindow().hideAddingDeliveryPanel();
	controler.setCurrentState(controler.planningState);
    }

    public String stateToString() {
	return "addDeliveryState";
    }

}
