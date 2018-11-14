package main.controler;

import main.model.Intersection;
import main.model.Section;
import main.ui.ExceptionModal;
import main.ui.Window;

/**
 * Controler handles the interactions between the ui and model packages.
 *
 */
public class Controler {

    private State currentState;
    private State previousState;
    private Window window;
    protected final InitState initState = new InitState();
    protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
    protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
    protected final PlanningState planningState = new PlanningState();
    protected final AddDeliveryState addState = new AddDeliveryState();

    /* Selected elements in model */
    private Intersection selectedIntersection;

    /**
     * Create the application's controler and window.
     */
    public Controler() {
	this.currentState = initState;
	this.window = new Window(this);
    }

    /**
     * Load the xml formatted plan. Called when the welcome screen's button
     * "Valider" is pushed.
     */
    public void openPlan() {
	try {
	    previousState = currentState;
	    currentState.openPlan(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Load the xml formatted delivery request. Called when the plan screen's
     * button "Valider" is pushed.
     */
    public void openDeliveries() {
	try {
	    previousState = currentState;
	    currentState.openDeliveries(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Calculate planning for the given delivery men count.
     */
    public void calculatePlanning() {
	try {
	    previousState = currentState;
	    currentState.calculatePlanning(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Add a new delivery to a tour.
     */
    public void addDelivery() {
	try {
	    previousState = currentState;
	    currentState.addDelivery(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Confirm new delivery's addition.
     */
    public void confirmNewDelivery() {
	try {
	    currentState.confirmNewDelivery(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Cancel addition of a new delivery.
     */
    public void cancelNewDelivery() {
	try {
	    currentState.cancelNewDelivery(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Remove a delivery from a tour.
     */
    public void removeDelivery() {
	currentState.removeDelivery(this, window);
    }

    /**
     * Open delivery men count selection modal.
     */
    public void openParameters() {
	try {
	    currentState.openParameters(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * Return to a given state.
     */
    public void returnToState() {
	if (currentState.equals(planningState)) {
	    currentState.returnToState(this, window, loadedDeliveriesState);
	} else if (currentState.equals(loadedDeliveriesState)) {
	    currentState.returnToState(this, window, loadedPlanState);
	}
    }

    /**
     * Handle a click near an intersection.
     * 
     * @param closestIntersection is the Intersection closest to the click.
     */
    public void clickedNearIntersection(Intersection closestIntersection) {
	currentState.clickedNearIntersection(this, window, closestIntersection);
    }

    /**
     * Handle a click near a section.
     * 
     * @param closestSection is the Section closest to the click.
     */
    public void clickedNearSection(Section closestSection) {
	currentState.clickedNearSection(this, window, closestSection);
    }

    /**
     * Get the controler's current state.
     * 
     * @return State, the current state of the controler.
     */
    public State getCurrentState() {
	return currentState;
    }

    /**
     * State setter.
     * 
     * @param newState is the new state to give to the controller.
     */
    public void setCurrentState(State newState) {
	this.currentState = newState;
    }

    /**
     * Get previous state.
     * 
     * @return State, the previous state.
     */
    public State getPreviousState() {
	return previousState;
    }

    /**
     * Get the intersection which is selected in the view.
     * 
     * @return the selected Intersection, or null if none was selected.
     */
    public Intersection getSelectedIntersection() {
	return selectedIntersection;
    }

    /**
     * @param selectedIntersection
     */
    public void setSelectedIntersection(Intersection selectedIntersection) {
	this.selectedIntersection = selectedIntersection;
	window.highlightSelectedIntersection(selectedIntersection);

    }

    /**
     * Get the window..
     * 
     * @return Window, the current window of the application.
     */
    public Window getWindow() {
	return window;
    }

}
