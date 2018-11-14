package main.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Listener catching the combobox events of the application.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class ComboboxListener implements ItemListener {

    /**
     * Create a combobox listener.
     */
    public ComboboxListener() {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
	Window.getPlanningPanel().getAddingPanel().updatePreviousDeliveryCombobox();
    }

}
