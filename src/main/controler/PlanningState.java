package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.model.Step;
import main.ui.InputDialogSelector;
import main.ui.InputDialogSelector.SelectionCancelledException;
import main.ui.Window;

/**
 * PlanningState is the state in which a tour planning has been calculated.
 *
 */
public class PlanningState extends DefaultState {

    // TODO : check if planning can be recalculated
    /**
     * Calculate the planning for the given deliveries request and plan.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void calculatePlanning(Controler controler, Window window) {
	// TODO: add code to call tourCalculator
	controler.setCurrentState(controler.planningState);
    }

    /**
     * Open parameters modal.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void openParameters(Controler controler, Window window) {
	try {
	    ModelInterface.setDeliveryMenCount(InputDialogSelector
		    .getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
	} catch (SelectionCancelledException e) {
	    System.out.println("Selection was cancelled, ignoring...");
	}
	// controler.setCurrentState(controler.parametersState);
    }

    /**
     * Move a delivery from one tour to another.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void moveDelivery(Controler controler, Window window) {
	// int newTourId = window.getNewTourId();
	// Delivery movedDelivery = window.getMovedDelivery();
	// ModelInterface.moveDelivery(newTourId, movedDelivery);
    }

    /**
     * Remove a delivery from a tour.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void deleteDelivery(Controler controler, Window window) {
	// ModelInterface.deleteDelivery(window.getDeletedDelivery());
    }

    /**
     * Add a delivery to a tour.
     * 
     * @param controler is the application's controler.
     * @param window    is the application's graphical window.
     */
    public void addDelivery(Controler controler, Window window) {
	// TODO : window.openNewDelivery()
	controler.setCurrentState(controler.addState);
    }

    @Override
    public void clickedNearSection(Controler controler, Window window, Section section) {
	window.highlightSelectedSection(section);
    }

    @Override
    public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection) {
	window.highlightSelectedIntersection(closestIntersection);

	Delivery selectedDelivery = ModelInterface.findCorrespondingDelivery(closestIntersection);
	if (selectedDelivery != null) {
	    Step step = ModelInterface.findStepBeforeDelivery(selectedDelivery);
	    window.listSectionsOfStep(step);
	}

	controler.setSelectedIntersection(closestIntersection);
    }

    @Override
    public void rightClickedNearIntersection(Controler controler, Window window, Intersection intersection) {
	window.highlightRightClickedIntersection(intersection);

	controler.setRightClickedIntersection(intersection);
    }

    public String stateToString() {
	return "planningState";
    }
}
