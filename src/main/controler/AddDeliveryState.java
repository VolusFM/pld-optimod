package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Step;
import main.model.Tour;
import main.ui.Window;

/**
 * AddDeliveryState is the state in which the user is trying to add a new
 * delivery to the planning.
 *
 */
class AddDeliveryState extends DefaultState {

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
	ModelInterface.addDelivery(toAdd, preceding, deliveryManTour);

	controler.getWindow().hideAddingDeliveryPanel();
	controler.setCurrentState(controler.planningState);
    }

    
    @Override
    public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection) {
	Delivery selectedDelivery = ModelInterface.findCorrespondingDelivery(closestIntersection);
	if (selectedDelivery != null) {
	    Step step = ModelInterface.findStepBeforeDelivery(selectedDelivery);
	    if (!selectedDelivery.equals(ModelInterface.getDepot())) {
		window.listSectionsOfStep(step);
	    } else {
		window.hideSectionsList();
	    }
	}
	controler.setSelectedIntersection(closestIntersection);
    }

    @Override
    public void rightClickedNearIntersection(Controler controler, Window window, Intersection intersection) {
	window.highlightRightClickedIntersection(intersection);

	controler.setRightClickedIntersection(intersection);
    }
    
    public String stateToString() {
	return "addDeliveryState";
    }

}
