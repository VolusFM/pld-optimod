package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.Controler;

public class ButtonListener implements ActionListener {
	
	private Controler controler;

	public ButtonListener(Controler c){
//		TODO : controleur a ajouter en parametre et set attribut
	}

	/*
	 * Function called by  listener every time a button is clicked.
	 * Send the corresponding message to the controler.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param e the event
	 */
	@Override
	public void actionPerformed(ActionEvent e) { 
		switch (e.getActionCommand()){
		case Window.ACTION_SELECTION_PLAN: /* TODO : ajouter un appel aux methode  du controleur */ ; break;
		case Window.ACTION_SELECTION_DELIVERY: /* TODO : ajouter un appel aux methode  du controleur */ ; break;
		}
	}
}