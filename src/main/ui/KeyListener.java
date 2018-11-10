package main.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.controler.Controler;
import main.model.Delivery;
import main.model.ModelInterface;
import main.model.TourCalculator;

public class KeyListener extends KeyAdapter {

	private Controler controler;

	public KeyListener(Controler controler) {
		this.controler = controler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			Delivery delivery = ModelInterface.findCorrespondingDelivery(controler.getSelectedIntersection());
			if (delivery != null) {

				TourCalculator.getInstance().removeDeliveryFromTour(delivery, ModelInterface.getTourPlanning().get(0));
				controler.getWindow().redraw();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_INSERT) {
			Delivery delivery = ModelInterface.findCorrespondingDelivery(controler.getSelectedIntersection());
			if (delivery != null) {

				Delivery newDelivery = new Delivery(0, controler.getRightClickedIntersection());
				TourCalculator.getInstance().addDeliveryAfterDelivery(newDelivery, delivery);
				controler.getWindow().redraw();
			}
		}

	}
}
