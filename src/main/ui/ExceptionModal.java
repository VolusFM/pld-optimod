package main.ui;

import javax.swing.JOptionPane;

/**
 * Abstract Class what create a modal used to inform the UI user of exception
 * that occurred.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public abstract class ExceptionModal {

    /**
     * Function what create the error modal for the specified exception.
     * 
     * @param exception the catch exception to show.
     */
    public static void showErrorModal(Exception exception) {
	JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
