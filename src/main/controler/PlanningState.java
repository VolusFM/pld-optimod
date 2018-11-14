package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.model.Step;
import main.ui.InputDialogSelector;
import main.ui.Window;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * PlanningState is the state in which a tour planning has been calculated.
 *
 */
class PlanningState extends DefaultState {

    /**
     * Calculate the planning for the given deliveries request and plan.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void calculatePlanning(Controler controler, Window window) {
	ModelInterface.getTourCalculator().calculateTours();
	window.displayTourPlanningPanel();
    }

    /**
     * Open parameters modal.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void openParameters(Controler controler, Window window) {
	ModelInterface.setDeliveryMenCount(InputDialogSelector
		.getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
    }

    /**
     * Move a delivery from one tour to another.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void moveDelivery(Controler controler, Window window) {
	throw new NotImplementedException();
    }

    /**
     * Add a delivery to a tour.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void addDelivery(Controler controler, Window window) {
	window.displayAddingDeliveryPanel();
	controler.setCurrentState(controler.addState);
    }

    /**
     * Remove a delivery from a tour.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void removeDelivery(Controler controler, Window window) {
	Intersection i = controler.getSelectedIntersection();
	Delivery toRemove = ModelInterface.findCorrespondingDelivery(i);
	if (toRemove != null) {
	    ModelInterface.removeDelivery(toRemove);
	    window.redraw();
	    window.redrawTable();
	}
    }

    @Override
    public void returnToState(Controler controler, Window window, State returnState) {
	ModelInterface.emptyTourFactory();
	ModelInterface.initializeTourCalculator();
	window.displayPlanView();
	window.displayCalculateTourButtonPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	controler.setCurrentState(returnState);
    }

    @Override
    public void clickedNearSection(Controler controler, Window window, Section section) {
	window.highlightSelectedSection(section);
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
     * Get the name of the state for debug purposes.
     * 
     * @return String, the name of the state.
     */
    public String stateToString() {
	return "planningState";
    }
}
