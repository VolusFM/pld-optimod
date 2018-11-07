package main.controler;

import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.ui.RangeSelector;
import main.ui.RangeSelector.SelectionCancelledException;
import main.ui.Window;

public class PlanningState extends DefaultState {

	// TODO : check if planning can be recalculated
	public void calculatePlanning(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
		controler.setCurrentState(controler.planningState);
	}

	public void openParameters(Controler controler, Window window) {
		try {
			ModelInterface.setDeliveryMenCount(RangeSelector.getIntegerInRange(1, ModelInterface.getDeliveries().size(),
					"Please select the delivery men count", "Range selector"));
		} catch (SelectionCancelledException e) {
			System.out.println("Selection was cancelled, ignoring...");
		}
		// controler.setCurrentState(controler.parametersState);
	}

	public void moveDelivery(Controler controler, Window window) {
		// int newTourId = window.getNewTourId();
		// Delivery movedDelivery = window.getMovedDelivery();
		// ModelInterface.moveDelivery(newTourId, movedDelivery);
	}

	public void deleteDelivery(Controler controler, Window window) {
		// ModelInterface.deleteDelivery(window.getDeletedDelivery());
	}

	public void addDelivery(Controler controler, Window window) {
		// TODO : window.openNewDelivery()
		controler.setCurrentState(controler.addState);
	}
	
	
	@Override
	public void clickedNearSection(Controler controler, Window window, Section section) {
		window.highlightSelectedSection(section);
	}

	@Override
	public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection) {
		window.highlightSelectedIntersection(closestIntersection);
	}

	public String stateToString() {
		return "planningState";
	}
}
