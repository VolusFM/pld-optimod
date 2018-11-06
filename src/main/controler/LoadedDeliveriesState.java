package main.controler;

import main.model.ModelInterface;
import main.model.TourCalculator;
import main.ui.RangeSelector;
import main.ui.Window;
import main.ui.RangeSelector.SelectionCancelledException;

public class LoadedDeliveriesState extends DefaultState {
	public void openParameters(Controler controler, Window window) {
		try {
			ModelInterface.setDeliveryMenCount(RangeSelector.getIntegerInRange(1, ModelInterface.getDeliveries().size(), "Please select the delivery men count", "Range selector"));
		} catch (SelectionCancelledException e) {
			System.out.println("Selection was cancelled, ignoring...");
		}

//		controler.setCurrentState(controler.parametersState);
	}

	public void calculateTour(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
		
		TourCalculator.getInstance().calculateTours();
		window.displayTourPlanningPanel();
		
		controler.setCurrentState(controler.planningState);
	}

	public void moveDelivery(Controler controler, Window window) {
		// TODO
	}

	public void addDelivery(Controler controler, Window window) {
		// TODO : window.openNewDelivery
		controler.setCurrentState(controler.addState);
	}

	public String stateToString() {
		return "loadedDeliveryState";
	}
}
