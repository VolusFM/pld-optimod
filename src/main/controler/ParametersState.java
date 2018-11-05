package main.controler;

import main.model.ModelInterface;
import main.ui.RangeSelector;
import main.ui.RangeSelector.SelectionCancelledException;
import main.ui.Window;

public class ParametersState extends DefaultState {
	
	//Only go back to loadedDeliveryState
	public void confirmParameters(Controler controler, Window window) {
		ModelInterface.setDeliveryMenCount(10); //window.getNumberDeliveryMan()
		//TODO : display previous state
		controler.setCurrentState(controler.loadedDeliveriesState);
	}

	public String stateToString() {
		return "parametersState";
	}
}