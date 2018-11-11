package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.model.Step;
import main.ui.InputDialogSelector;
import main.ui.InputDialogSelector.SelectionCancelledException;
import main.ui.Window;

public class PlanningState extends DefaultState {

	// TODO : check if planning can be recalculated
	public void calculatePlanning(Controler controler, Window window) {
		// TODO: add code to call tourCalculator
		controler.setCurrentState(controler.planningState);
	}

	public void openParameters(Controler controler, Window window) {
		try {
			ModelInterface.setDeliveryMenCount(InputDialogSelector.getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
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

		Delivery selectedDelivery = ModelInterface.findCorrespondingDelivery(closestIntersection);
		if (selectedDelivery != null) {
			Step step = ModelInterface.findStepBeforeDelivery(selectedDelivery);
			window.listSectionsOfStep(step);
		}

		controler.setSelectedIntersection(closestIntersection);
	}

	@Override
	public void rightClickedNearIntersection(Controler controler, Window window, Intersection intersection) {
		window.highlightRightClickedIntersection(intersection);

		controler.setRightClickedIntersection(intersection);
	}

	public String stateToString() {
		return "planningState";
	}
}
