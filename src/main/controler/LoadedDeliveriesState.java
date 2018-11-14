package main.controler;

import main.model.ModelInterface;
import main.ui.InputDialogSelector;
import main.ui.Window;

/**
 * LoadedDeliveriesState is the state in which a plan and a deliveries request
 * have been loaded.
 *
 */
class LoadedDeliveriesState extends DefaultState {

    /**
     * Open parameters modal.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void openParameters(Controler controler, Window window) {
	ModelInterface.setDeliveryMenCount(InputDialogSelector
		.getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
	window.displayDeliveryMenCountPanel();
    }

    /**
     * Calculate the planning for the given deliveries request and plan.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void calculatePlanning(Controler controler, Window window) {
	ModelInterface.getTourCalculator().calculateTours();
	window.displayTourPlanningPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	controler.setCurrentState(controler.planningState);
    }

    /**
     * Return to a given state.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     * @param state is the state we have to return to.
     */
    @Override
    public void returnToState(Controler controler, Window window, State returnState) {
	ModelInterface.emptyLoadedDeliveries();
	window.displayPlanView();
	window.displayDeliveryRequestSelectionPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	window.toggleReturnButtonVisibility();
	controler.setCurrentState(returnState);
    }

    /**
     * Get the name of the state for debug purposes.
     * 
     * @return String, the name of the state.
     */
    public String stateToString() {
	return "loadedDeliveryState";
    }

}
