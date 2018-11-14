package main.ui;

import javax.swing.JOptionPane;

/**
 * Helper class to encapsulate an integer from an input box.
 */
public abstract class InputDialogSelector {

    public static int getIntegerFromInput(String message, String title) {
	return Integer.parseInt(JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE));
    }
}
