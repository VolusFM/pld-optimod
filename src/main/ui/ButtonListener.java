package main.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.media.sound.ModelIdentifier;

import main.controler.Controler;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;

public class ButtonListener implements ActionListener {

    private Controler controler;

    public ButtonListener(Controler c) {
	this.controler = c;
    }

	/*
	 * Function called by listener every time a button is clicked. Send the
	 * corresponding message to the controler.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * @param e the event
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
		case PlanningView.ACTION_ADDING_DELIVERY_POINT :
			controler.getWindow().displayAddingDeliveryPanel();
			// TODO : controleur.addDeleveryPoint() qui appelle l'IHM
			break;
		case PlanningView.ACTION_SUPRESSING_DELIVERY_POINT :
			Intersection i = controler.getSelectedIntersection();
			Delivery toSuppress = ModelInterface.findCorrespondingDelivery(i);
			controler.removeDelivery();
			break;
		case PlanningView.ACTION_CANCELLING_MODIFICATIONS :			
			// TODO Controleur : controleur.cancelModification()
			break; 
		case AddingDeliveryView.ACTION_VALIDATION_ADDING_DELIVERY :
			int duration = controler.getWindow().planningPanel.addingPanel.getSelectedDuration();
			double lat = controler.getWindow().planningPanel.addingPanel.getSelectedLat();
			double lon = controler.getWindow().planningPanel.addingPanel.getSelectedLon();
			int deliveryMenId = controler.getWindow().planningPanel.addingPanel.getSelectedDeliveryMen();
			System.out.println(deliveryMenId);
			Delivery precedingDelivery = controler.getWindow().planningPanel.addingPanel.getSelectedPrecedingDelivery();
			System.out.println(precedingDelivery);
			// TODO Controleur : controleur.validateAddingDeleveryPoint(deliveryMenId, lat, lon, duration, precedingDelivery)
			// qui crée la nouvelle livraison et maj l'affichage (et enleve la fenetre d'ajout)
		case AddingDeliveryView.ACTION_CANCELATION_ADDING_DELIVERY :
			controler.getWindow().hideAddingDeliveryPanel();
			// TODO Controleur : controleur.cancelAddingDeliveryPoint()
			break;			
		default:
			throw new RuntimeException("Unhandled action : " + e.getActionCommand());
		}

	}
    
}