package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WindowHeader extends JPanel {

	/* Title label */
	private static final String APPLICATION_TITLE = "Optimod";

	/* Label of buttons */
	private static final String PARAMETERS = "Parametres";
	private static final String RETURN = "Retour";

	/* Visibility of buttons */
	private boolean parametersButtonVisibility = true;
	private boolean returnButtonVisibility = true;

	/* Button's action */
	protected static final String ACTION_PARAMETERS = "PARAMETERS";
	protected static final String ACTION_RETURN = "RETURN";

	/* Components */
	private JButton parametersButton;
	private JButton returnButton;
	private JLabel windowTitle;

	/**
	 * Create a header with title and buttons for the specified windows.
	 * 
	 * @param w the window
	 */
	public WindowHeader(Window w, boolean parametersButtonVisibility, boolean returnButtonVisibility,
			ButtonListener buttonListener) {
		super();
		setLayout(new BorderLayout());
		setBackground(Color.white);

		this.windowTitle = new JLabel(APPLICATION_TITLE, SwingConstants.CENTER);
		add(windowTitle, BorderLayout.CENTER);

		this.parametersButton = new JButton(PARAMETERS);
		this.parametersButton.setFocusable(false);
		this.parametersButton.setFocusPainted(false);
		this.parametersButtonVisibility = parametersButtonVisibility;
		this.parametersButton.setVisible(this.parametersButtonVisibility);
		this.parametersButton.setActionCommand(ACTION_PARAMETERS);
		this.parametersButton.addActionListener(buttonListener);
		add(parametersButton, BorderLayout.EAST);

		this.returnButton = new JButton(RETURN);
		this.returnButton.setFocusable(false);
		this.returnButton.setFocusPainted(false);
		this.returnButtonVisibility = returnButtonVisibility;
		this.returnButton.setVisible(this.returnButtonVisibility);
		this.returnButton.setActionCommand(ACTION_RETURN);
		this.returnButton.addActionListener(buttonListener);
		add(returnButton, BorderLayout.WEST);
	}

}
