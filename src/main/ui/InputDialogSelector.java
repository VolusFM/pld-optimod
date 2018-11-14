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
     * Function what create a modal to set a integer value.
     * 
     * @param message
     * @param title
     * @return int, the value selected by the user
     */
    public static int getIntegerFromInput(String message, String title) {
	return Integer.parseInt(JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE));
    }

}
