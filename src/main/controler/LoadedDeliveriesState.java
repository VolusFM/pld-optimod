package main.controler;

import main.model.ModelInterface;
import main.ui.InputDialogSelector;
import main.ui.InputDialogSelector.SelectionCancelledException;
import main.ui.Window;

/**
 * LoadedDeliveriesState is the state in which a plan and a deliveries request
 * have been loaded.
 *
 */
public class LoadedDeliveriesState extends DefaultState {

    /**
     * Open parameters modal.
     * 
     * @param controler is the application's controler.
     * @param window is the application's graphical window.
     */
    public void openParameters(Controler controler, Window window) {

	try {
	    ModelInterface.setDeliveryMenCount(InputDialogSelector
		    .getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
	} catch (SelectionCancelledException e) {
	    System.out.println("Selection was cancelled, ignoring...");
	}
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
	
	controler.setCurrentState(controler.planningState);
    }

    public String stateToString() {
	return "loadedDeliveryState";
    }

}
