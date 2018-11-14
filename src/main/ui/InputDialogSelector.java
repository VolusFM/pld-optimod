package main.ui;

import javax.swing.JOptionPane;

/**
 * Helper class to encapsulate an integer from an input box.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public abstract class InputDialogSelector {

    /**
     * Create a modal to set a integer value.
     * 
     * @param message is the message to display.
     * @param title   is the title of the modal.
     * @return Integer, the value selected by the user.
     */
    public static int getIntegerFromInput(String message, String title) {
	return Integer.parseInt(JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE));
    }

}
