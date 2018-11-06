package main.controler;

import main.model.Intersection;
import main.model.ModelInterface;
import main.ui.Window;

public class Controler {

	private State currentState;
	public Window window; // FIXME visibility

	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();
	protected final AddDeliveryState addState = new AddDeliveryState();
	private ModelInterface model;

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
			currentState.openDeliveries(this, window);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * model s getter.
	 */

	public ModelInterface getModel() {
		return model;
	}

	/**
	 * State s setter.
	 */
	public void setCurrentState(State currentState) {
		System.out.println("Changed from " + this.currentState.stateToString() + " to " + currentState.stateToString());
		this.currentState = currentState;
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
		currentState.calculateTour(this, window);
	}

	public void clickedNearIntersection(Intersection findClosestIntersection) {
		// FIXME : do it with states ?
		window.highlightSelectedIntersection(findClosestIntersection);
	}
}
