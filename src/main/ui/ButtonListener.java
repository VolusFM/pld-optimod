package main.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controler.Controler;
import main.model.Intersection;

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
			controler.window.displayAddingDeliveryPanel();
			// TODO : controleur.addDeleveryPoint() qui appelle l'IHM
			break;
		case PlanningView.ACTION_SUPRESSING_DELIVERY_POINT :
			// Intersection toSupress = controler.getSelectedIntersection();
			// TODO Controleur: controloeur.suppressDelivery(toSupress)
			break;
		case PlanningView.ACTION_CANCELLING_MODIFICATIONS :			
			// TODO Controleur : controleur.cancelModification()
			break; 
		case AddingDeliveryView.ACTION_VALIDATION_ADDING_DELIVERY :
			// TODO UI : get (int livreur, double lat, double long, int duree, Delivery/ou int precedente)
//			int deliveryMen = 
			System.out.println(controler.window.planningPanel.addingPanel.getSelectedDeliveryMen());
//			int duration = 
			System.out.println(controler.window.planningPanel.addingPanel.getSelectedDuration());
//			double lat = 
			System.out.println(controler.window.planningPanel.addingPanel.getSelectedLat());
//			double lon = 
			System.out.println(controler.window.planningPanel.addingPanel.getSelectedLon());
			// TODO : selected delivery  = previous
//			if (   lat != -1 
//				&& lon != -1 
//				&& duration != -1 
//				&& deliveryMen != -1 
//					/* TODO :&& delivery previous is correct*/){
//					// TODO Controleur : controleur.validateAddingDeleveryPoint(int livreur, double lat, double long, int duree, Delivery/ou int precedente)
//					// qui crée la nouvelle livraison et maj l'affichage (et enleve la fenetre d'ajout)
//			}else{
				controler.window.displayAddingDeliveryPanel();
//			}
		case AddingDeliveryView.ACTION_CANCELATION_ADDING_DELIVERY :
			controler.window.hideAddingDeliveryPanel();
			// TODO Controleur : controleur.cancelAddingDeliveryPoint()
			break;			
		default:
			throw new RuntimeException("Unhandled action : " + e.getActionCommand());
		}
	}
}