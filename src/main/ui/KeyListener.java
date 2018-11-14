package main.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.controler.Controler;
import main.model.Delivery;
import main.model.ModelInterface;

/**
 * Listener catching the keyboard events of the application.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class KeyListener extends KeyAdapter {

    private Controler controler;

    /**
     * Create a keyboard listener.
     * 
     * @param controler is the application's controller.
     */
    public KeyListener(Controler controler) {
	this.controler = controler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_DELETE) {
	    Delivery delivery = ModelInterface.findCorrespondingDelivery(this.controler.getSelectedIntersection());
	    if (delivery != null) {
		this.controler.removeDelivery();
	    }
	}

	if (e.getKeyCode() == KeyEvent.VK_INSERT) {
	    Delivery delivery = ModelInterface.findCorrespondingDelivery(this.controler.getSelectedIntersection());
	    if (delivery != null) {
		this.controler.addDelivery();
	    }
	}
    }
}
