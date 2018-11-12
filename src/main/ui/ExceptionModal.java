package main.ui;

import javax.swing.JOptionPane;

public abstract class ExceptionModal {

    public static void showErrorModal(Exception exception) {
	JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
