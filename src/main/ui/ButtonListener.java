package main.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controler.Controler;

/**
 * Listener catching the button events of the application.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER Léo, THOLOT Cassandre
 */
public class ButtonListener implements ActionListener {

    private Controler controler;

    /**
     * Create a button listener for the specified application controller.
     * 
     * @param c the application controller
     */
    public ButtonListener(Controler c) {
	this.controler = c;
    }

    /**
     * Function called by listener every time a button is clicked. Send the
     * corresponding message to the controler.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param e the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	case Window.ACTION_SELECTION_PLAN:
	    controler.openPlan();
	    break;
	case Window.ACTION_SELECTION_DELIVERY:
	    controler.openDeliveries();
	    break;
	case WindowHeader.ACTION_CHANGE_DELIVERY_MEN_COUNT:
	    controler.openParameters();
	    break;
	case Window.ACTION_CALCULATE_TOUR:
	    controler.calculateTour();
	    break;
	case WindowHeader.ACTION_RETURN:
	    controler.returnToState();
	    break;
	case PlanningView.ACTION_ADDING_DELIVERY_POINT:
	    controler.addDelivery();
	    break;
	case PlanningView.ACTION_SUPRESSING_DELIVERY_POINT:
	    controler.removeDelivery();
	    break;
	case PlanningView.ACTION_CANCELLING_MODIFICATIONS:
	    // TODO : controleur.cancelModification() => Giving up functionality
	    break;
	case AddingDeliveryView.ACTION_VALIDATION_ADDING_DELIVERY:
	    controler.confirmNewDelivery();
	    break;
	case AddingDeliveryView.ACTION_CANCELATION_ADDING_DELIVERY:
	    controler.cancelNewDelivery();
	    break;
	default:
	    throw new RuntimeException("Unhandled action : " + e.getActionCommand());
	}

    }

}