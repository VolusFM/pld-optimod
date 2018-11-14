package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.model.Tour;
import main.model.TourFactory;
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
    private Intersection rightClickedIntersection;

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
     * Cancel addition of a new delivery.
     */
    public void cancelNewDelivery() {
	try {
	    currentState.cancelNewDelivery(this, window);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	    System.out.println(e + "controler cancelnewdelivery");
	}
    }

    /**
     * Confirm new delivery addition.
     * 
     * @param precedingDelivery
     * @param deliveryMenId
     * @param lon
     * @param lat
     * @param duration
     */
    public void confirmNewDelivery() {
	int duration = window.getPlanningPanel().getAddingPanel().getSelectedDuration();
	double lat = window.getPlanningPanel().getAddingPanel().getSelectedLat();
	double lon = window.getPlanningPanel().getAddingPanel().getSelectedLon();
	int deliveryMenId = window.getPlanningPanel().getAddingPanel().getSelectedDeliveryMen();
	System.out.println("DELIVERY MEN ID IS : " + deliveryMenId);
	Delivery precedingDelivery = window.getPlanningPanel().getAddingPanel().getSelectedPrecedingDelivery();

	try {
	    Intersection address = ModelInterface.findClosestIntersection(lat, lon);
	    Delivery toAdd = new Delivery(duration, address);
	    Tour deliveryManTour = TourFactory.getInstance().findTourFromDeliveryManId(deliveryMenId);
	    currentState.confirmNewDelivery(this, window, toAdd, deliveryManTour, precedingDelivery);
	} catch (Exception e) {
	    ExceptionModal.showErrorModal(e);
	}
    }

    /**
     * State setter.
     * 
     * @param newState is the new state to give to the controller.
     */
    public void setCurrentState(State newState) {
	System.out.println("Changed from " + this.currentState.stateToString() + " to " + newState.stateToString());
	this.currentState = newState;
    }

    /**
     * Remove a delivery from a tour.
     */
    public void removeDelivery() {
	currentState.removeDelivery(this, window);
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
     * Return before any planning was calculate
     */
    public void returnToState() {
	if(currentState.equals(planningState)){
	    currentState.returnToState(this, window, loadedDeliveriesState);
	} else if (currentState.equals(loadedDeliveriesState)){
	    currentState.returnToState(this, window, loadedPlanState);
	}
    }

    /**
     * XXX : what's the use of this ?
     */
    public void calculateTour() {
	currentState.calculatePlanning(this, window);
    }

    /**
     * TODO doc
     * 
     * @param closestIntersection
     */
    public void clickedNearIntersection(Intersection closestIntersection) {
	currentState.clickedNearIntersection(this, window, closestIntersection);
    }

    /**
     * TODO doc
     * 
     * @param closestSection
     */
    public void clickedNearSection(Section closestSection) {
	currentState.clickedNearSection(this, window, closestSection);
    }

    /**
     * TODO doc
     * 
     * @param intersection
     */
    public void rightClickedNearIntersection(Intersection intersection) {
	currentState.rightClickedNearIntersection(this, window, intersection);
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
     * Get the last Intersection that was right clicked on.
     * 
     * @return Intersection, the last intersection the user right clicked on.
     */
    public Intersection getRightClickedIntersection() {
	return rightClickedIntersection;
    }

    /**
     * @param rightClickedIntersection
     */
    public void setRightClickedIntersection(Intersection rightClickedIntersection) {
	this.rightClickedIntersection = rightClickedIntersection;
    }

    // XXX
    /**
     * Window getter.
     * 
     * @return Window, the current window of the application.
     */
    public Window getWindow() {
	return window;
    }

}
