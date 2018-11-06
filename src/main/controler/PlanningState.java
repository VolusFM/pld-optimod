package main.controler;

import main.model.ModelInterface;
import main.ui.RangeSelector;
import main.ui.RangeSelector.SelectionCancelledException;
import main.ui.Window;

public class PlanningState extends DefaultState {

	public void openParameters(Controler controler, Window window) {
		try {
			ModelInterface.setDeliveryMenCount(RangeSelector.getIntegerInRange(1, ModelInterface.getDeliveries().size(), "Please select the delivery men count", "Range selector"));
		} catch (SelectionCancelledException e) {
			System.out.println("Selection was cancelled, ignoring...");
		}
//		controler.setCurrentState(controler.parametersState);
	}
	
	public String stateToString() {
		return "planningState";
	}
}
