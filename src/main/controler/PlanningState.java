package main.controler;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Section;
import main.model.Step;
import main.ui.InputDialogSelector;
import main.ui.Window;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * PlanningState is the state in which a tour planning has been calculated.
 *
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
class PlanningState extends DefaultState {

    @Override
    public void calculatePlanning(Controler controler, Window window) {
	ModelInterface.getTourCalculator().calculateTours();
	window.displayTourPlanningPanel();
    }

    @Override
    public void openParameters(Controler controler, Window window) {
	ModelInterface.setDeliveryMenCount(InputDialogSelector
		.getIntegerFromInput("Veuillez choisir le nombre de livreurs", "Nombre de livreurs"));
    }

    @Override
    public void addDelivery(Controler controler, Window window) {
	window.displayAddingDeliveryPanel();
	controler.setCurrentState(controler.addState);
    }

    @Override
    public void removeDelivery(Controler controler, Window window) {
	Intersection i = controler.getSelectedIntersection();
	Delivery toRemove = ModelInterface.findCorrespondingDelivery(i);
	if (toRemove != null) {
	    ModelInterface.removeDelivery(toRemove);
	    window.redraw();
	    window.redrawTable();
	}
    }

    @Override
    public void moveDelivery(Controler controler, Window window) {
	throw new NotImplementedException();
    }

    @Override
    public void returnToState(Controler controler, Window window, State returnState) {
	ModelInterface.emptyTourFactory();
	ModelInterface.initializeTourCalculator();
	window.displayPlanView();
	window.displayCalculateTourButtonPanel();
	window.toggleDeliveryMenCountButtonVisiblity();
	controler.setCurrentState(returnState);
    }

    @Override
    public void clickedNearSection(Controler controler, Window window, Section section) {
	window.highlightSelectedSection(section);
    }

    @Override
    public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection) {
	Delivery selectedDelivery = ModelInterface.findCorrespondingDelivery(closestIntersection);
	if (selectedDelivery != null) {
	    Step step = ModelInterface.findStepBeforeDelivery(selectedDelivery);
	    if (!selectedDelivery.equals(ModelInterface.getDepot())) {
		window.listSectionsOfStep(step);
	    } else {
		window.hideSectionsList();
	    }
	}
	controler.setSelectedIntersection(closestIntersection);
    }

    @Override
    public String stateToString() {
	return "planningState";
    }
}
