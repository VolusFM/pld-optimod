package main.controler;

import main.model.Intersection;
import main.model.Section;
import main.ui.ExceptionModal;
import main.ui.Window;

public class Controler {

	private State currentState;
	private State previousState;
	private Window window;
	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();
	protected final AddDeliveryState addState = new AddDeliveryState();

	/* Selected elements in model */
	private Intersection selectedIntersection;
	private Intersection rightClickedIntersection;

	/**
	 * Create application's controler
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
	 * Load the xml formatted delivery request. Called when the "plan" screen s
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
	 * Calculate planning for the asked tour number
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
	 * Add a new delivery to a tour
	 */
	public void addDelivery() {
		try {
			previousState = currentState;
			currentState.addDelivery(this, window);
		} catch (Exception e) {
			ExceptionModal.showErrorModal(e);
			System.out.println(e + "controler adddelivery");
		}
	}

	/**
	 * Cancel addition of a new delivery
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
	 * Confirms new number of tour
	 */
	public void confirmParameters() {
		try {
			currentState.confirmParameters(this, window);
		} catch (Exception e) {
			ExceptionModal.showErrorModal(e);
		}
	}

	/**
	 * Confirm new delivery addition
	 */
	public void confirmNewDelivery() {
		try {
			currentState.confirmNewDelivery(this, window);
		} catch (Exception e) {
			ExceptionModal.showErrorModal(e);
		}
	}

	/**
	 * State s setter.
	 */
	public void setCurrentState(State currentState) {
		System.out.println("Changed from " + this.currentState.stateToString() + " to " + currentState.stateToString());
		this.currentState = currentState;
	}

	public void deleteDelivery() {
		currentState.deleteDelivery(this, window);
	}

	/**
	 * get the controler current state
	 * 
	 * @return currentState
	 */
	public State getCurrentState() {
		return currentState;
	}

	public void openParameters() {
		try {
			currentState.openParameters(this, window);
		} catch (Exception e) {
			ExceptionModal.showErrorModal(e);
		}
	}

	public void calculateTour() {
		currentState.calculatePlanning(this, window);
	}

	public void clickedNearIntersection(Intersection closestIntersection) {
		currentState.clickedNearIntersection(this, window, closestIntersection);
	}

	public void clickedNearSection(Section closestSection) {
		currentState.clickedNearSection(this, window, closestSection);
	}

	public void rightClickedNearIntersection(Intersection intersection) {
		currentState.rightClickedNearIntersection(this, window, intersection);
	}

	/**
	 * get previous state.
	 * 
	 * @return the previous state, which was set before the current state
	 */
	public State getPreviousState() {
		return previousState;
	}

	/**
	 * Get the intersection which is selected if the view
	 * 
	 * @return the selected Intersection, or null if none is selected
	 */
	public Intersection getSelectedIntersection() {
		return selectedIntersection;
	}

	public void setSelectedIntersection(Intersection selectedIntersection) {
		this.selectedIntersection = selectedIntersection;
	}

	public Intersection getRightClickedIntersection() {
		return rightClickedIntersection;
	}

	public void setRightClickedIntersection(Intersection rightClickedIntersection) {
		this.rightClickedIntersection = rightClickedIntersection;
	}

	// XXX
	public Window getWindow() {
		return window;
	}
}
