package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Step;
import main.model.Tour;
import main.model.TourFactory;
import main.ui.Window;

/**
 * AddDeliveryState is the state in which the user is trying to add a new
 * delivery to the planning.
 *
 */
class AddDeliveryState extends DefaultState {

    @Override
    public void cancelNewDelivery(Controler controler, Window window) {
	controler.getWindow().hideAddingDeliveryPanel();
	controler.setCurrentState(controler.planningState);
    }

    @Override
    public void confirmNewDelivery(Controler controler, Window window) {

	int duration = Window.getPlanningPanel().getAddingPanel().getSelectedDuration();
	double lat = Window.getPlanningPanel().getAddingPanel().getSelectedLat();
	double lon = Window.getPlanningPanel().getAddingPanel().getSelectedLon();
	int deliveryMenId = Window.getPlanningPanel().getAddingPanel().getSelectedDeliveryMen();
	Delivery preceding = Window.getPlanningPanel().getAddingPanel().getSelectedPrecedingDelivery();
	Intersection address = ModelInterface.findClosestIntersection(lat, lon);
	Delivery toAdd = new Delivery(duration, address);
	Tour deliveryManTour = TourFactory.getInstance().findTourFromDeliveryManId(deliveryMenId);
	ModelInterface.addDelivery(toAdd, preceding, deliveryManTour);

	controler.getWindow().hideAddingDeliveryPanel();
	controler.setCurrentState(controler.planningState);
	window.redraw();
	window.redrawTable();
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

    /**
     * Get string description of this state.
     */
    public String stateToString() {
	return "addDeliveryState";
    }

}
