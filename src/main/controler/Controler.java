package main.controler;

import main.model.Intersection;
import main.model.Section;
import main.ui.Window;

public class Controler {

	private State currentState;
	private State previousState;
	public Window window; // FIXME visibility
	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();
	protected final AddDeliveryState addState = new AddDeliveryState();

	/* Selected elements in model */
	// XXX : is this the right place ?
	private Intersection selectedIntersection;

	/**
	 * Create application's controler
	 * 
	 * @param model,
	 *            model package s entry point
	 * @param window
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
			System.out.println(e);
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
			System.out.println(e);
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
			// TODO check exeption's handling
			System.out.println(e);
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
			// TODO : check exception handling + remove string when test done
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
			// TODO check handling + remove message
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
			System.out.println(e);
		}
	}

	/**
	 * Confirm new delivery addition
	 */
	public void confirmNewDelivery() {
		try {
			currentState.confirmNewDelivery(this, window);
		} catch (Exception e) {
			System.out.println(e);
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
		currentState.openParameters(this, window);
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

	/**
	 * get previous state.
	 * 
	 * @return
	 */
	public State getPreviousState() {
		return previousState;
	}

	///
	public Intersection getSelectedIntersection() {
		return selectedIntersection;
	}

	public void setSelectedIntersection(Intersection selectedIntersection) {
		this.selectedIntersection = selectedIntersection;
	}

	// XXX
	public Window getWindow() {
		return window;
	}
}
