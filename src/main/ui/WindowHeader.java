package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel using as header in the application, wit the application title and
 * principal buttons, such as Return and Parameters buttons.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class WindowHeader extends JPanel {

    /* Id */
    private static final long serialVersionUID = 1L;

    /* Labels */
    private static final String APPLICATION_TITLE = "Optimod";
    private static final String CHANGE_DELIVERY_MEN_COUNT = "Modifier le nombre de livreurs";
    private static final String RETURN = "Retour";

    /* Visibility of buttons */
    private boolean deliveryMenCountButtonVisibility = false;
    private boolean returnButtonVisibility = false;

    /* Button's actions */
    protected static final String ACTION_CHANGE_DELIVERY_MEN_COUNT = "PARAMETERS";
    protected static final String ACTION_RETURN = "RETURN";

    /* Button's listener */
    private ButtonListener listener;

    /* Components */
    private JButton deliveryMenCountButton;
    private JButton returnButton;
    private JLabel windowTitle;

    /**
     * Create a header with title and buttons for the specified windows.
     * 
     * @param w              is the application's graphical window.
     * @param buttonListener is the button listener to be used in the panel.
     */
    public WindowHeader(Window w, ButtonListener buttonListener) {
	super();
	setLayout(new BorderLayout());
	setBackground(Color.white);

	this.windowTitle = new JLabel(APPLICATION_TITLE, SwingConstants.CENTER);
	add(this.windowTitle, BorderLayout.CENTER);

	this.listener = buttonListener;

	this.deliveryMenCountButton = createButton(CHANGE_DELIVERY_MEN_COUNT, ACTION_CHANGE_DELIVERY_MEN_COUNT);
	this.deliveryMenCountButton.setFocusable(false);
	this.deliveryMenCountButton.setFocusPainted(false);
	this.deliveryMenCountButton.setVisible(this.deliveryMenCountButtonVisibility);
	add(this.deliveryMenCountButton, BorderLayout.EAST);

	this.returnButton = createButton(RETURN, ACTION_RETURN);
	this.returnButton.setFocusable(false);
	this.returnButton.setFocusPainted(false);
	this.returnButton.setVisible(this.returnButtonVisibility);
	add(this.returnButton, BorderLayout.WEST);
    }

    /**
     * Toggle delivery men count button's visibility.
     */
    public void toggleDeliveryMenCountButtonVisibility() {
	this.deliveryMenCountButtonVisibility = !this.deliveryMenCountButtonVisibility;
	this.deliveryMenCountButton.setVisible(this.deliveryMenCountButtonVisibility);
    }

    /**
     * Toggle return button's visibility.
     */
    public void toggleReturnButtonVisibility() {
	this.returnButtonVisibility = !this.returnButtonVisibility;
	this.returnButton.setVisible(this.returnButtonVisibility);
    }

    /**
     * Convenience method to create a new button with a given text and action, and
     * to bind it to the action listener
     * 
     * @param text   is the text of the button.
     * @param action is a string describing the action to perform when clicking on
     *                   the button.
     * 
     * @return JButton, the created button.
     */
    private JButton createButton(String text, String action) {
	JButton button = new JButton(text);
	button.setActionCommand(action);
	button.addActionListener(this.listener);
	return button;
    }

}
