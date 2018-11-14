package main.ui;

import javax.swing.JOptionPane;

/**
 * Helper class to encapsulate an integer from an input box.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER Léo, THOLOT Cassandre
 */
public abstract class InputDialogSelector {

    /**
     * Function what create a modal to set a integer value.
     * 
     * @param message
     * @param title
     * @return int, the value selected by the user
     * @throws SelectionCancelledException
     */
    public static int getIntegerFromInput(String message, String title) throws SelectionCancelledException {
	try {
	    return Integer.parseInt(JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE));
	} catch (NullPointerException | NumberFormatException e) {
	    throw new SelectionCancelledException();
	}
    }

    /**
     * Special exception in case of aborted selection.
     * 
     * @author Léo STERNER
     */
    private static class SelectionCancelledException extends Exception {

	/* Id */
	private static final long serialVersionUID = 1L;

    }

}
