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

    @Override
    public void openParameters(Controler controler, Window window) {
	ModelInterface.setDeliveryMenCount(InputDialogSelector
		.getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
	window.displayDeliveryMenCountPanel();
    }

    @Override
    public void calculatePlanning(Controler controler, Window window) {
	ModelInterface.getTourCalculator().calculateTours();
	window.displayTourPlanningPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	controler.setCurrentState(controler.planningState);
    }

    @Override
    public void returnToState(Controler controler, Window window, State returnState) {
	ModelInterface.emptyLoadedDeliveries();
	window.displayPlanView();
	window.displayDeliveryRequestSelectionPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	window.toggleReturnButtonVisibility();
	controler.setCurrentState(returnState);
    }

    @Override
    public String stateToString() {
	return "loadedDeliveryState";
    }

}
