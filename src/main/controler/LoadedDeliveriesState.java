package main.controler;

import main.model.ModelInterface;
import main.model.TourCalculator;
import main.ui.Window;

public class LoadedDeliveriesState extends DefaultState {
	public void openParameters(Controler controler, Window window) {
		// TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}

	public void calculatePlanning(Controler controler, Window window) {
		ModelInterface.getTourCalculator().getInstance().calculateTours(1); //TODO: ModelInterface.getDeliveryManNumber()
		window.invalidate(); // FIXME : force window to display again with path
		
		controler.setCurrentState(controler.planningState);
	}

	public String stateToString() {
		return "loadedDeliveryState";
	}
}
