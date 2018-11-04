package main.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.controler.Controler;
import main.model.Plan;

public class Window extends JFrame {

	/* Attributes */
	private Controler controler;
	private int planScale = 1;

	/* Components */
	private WindowHeader header;
	private JPanel centerPanel;
	private JPanel rightPanel;

	/* Listeners */
	private ButtonListener buttonListener;

	/* Components visibility */
	private boolean headerVisibility = true;

	/* Component's text */
	private final String WINDOW_TITLE = "Optimod";
	private final String TEXT_DELIVERY_SELECTION = "Sélectionnez un fichier de demande de livraison au format XML :";
	private final String TEXT_PLAN_SELECTION = "Sélectionnez un fichier de plan au format XML :";
	private final String BUTTON_BROWSE = "Parcourir";

	/* Button's action */
	protected static final String ACTION_SELECTION_PLAN = "LOAD_PLAN";
	protected static final String ACTION_SELECTION_DELIVERY = "LOAD_DELIVERY";

	/**
	 * Create a window with a header (with a title and "parameters" button), a
	 * choice of file component and a validation button.
	 */

	public Window(Controler controler) {
		setLayout(new BorderLayout());
		/* Initialize */
		this.controler = controler;
		buttonListener = new ButtonListener(controler);
		/* Header */
		this.header = new WindowHeader(this, true, false);
		this.header.setVisible(headerVisibility);
		getContentPane().add(header, BorderLayout.NORTH);
		/* Plan Selection Panel */
		displayPlanSelectionPanel();
		/* Delivery Request Selection Panel */
		displayDeliveryRequestSelectionPanel();
		/* Display */
		this.setTitle(WINDOW_TITLE);
		setWindowDimensions();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Defined the window and components size.
	 */
	private void setWindowDimensions() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		header.setLocation(0, 0);
	}

	/**
	 * Create the panel for selecting a plan.
	 */
	private void displayPlanSelectionPanel() {
		this.centerPanel = new JPanel();
		/* Create Content */
		JPanel container = new JPanel();
		JLabel selectionText = new JLabel(TEXT_PLAN_SELECTION);
		container.add(selectionText, BorderLayout.NORTH);
		JButton selectionButton = new JButton(BUTTON_BROWSE);
		selectionButton.setActionCommand(ACTION_SELECTION_PLAN);
		selectionButton.addActionListener(buttonListener);
		container.add(selectionButton, BorderLayout.CENTER);
		/* Set content */
		centerPanel.add(container, BorderLayout.CENTER);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Create the panel for selecting a delivery request.
	 */
	private void displayDeliveryRequestSelectionPanel() {
		this.rightPanel = new JPanel();
		/* Create Content */
		JPanel container = new JPanel();
		JLabel selectionText = new JLabel(TEXT_DELIVERY_SELECTION);
		container.add(selectionText, BorderLayout.NORTH);
		JButton selectionButton = new JButton(BUTTON_BROWSE);
		selectionButton.setActionCommand(ACTION_SELECTION_DELIVERY);
		selectionButton.addActionListener(buttonListener);
		container.add(selectionButton, BorderLayout.CENTER);
		/* Set content */
		rightPanel.add(container, BorderLayout.CENTER);
		getContentPane().add(rightPanel, BorderLayout.EAST);
	}

	/**
	 * Create the panel with the city plan.
	 */
	public void displayPlanView() {
		this.centerPanel = new JPanel();
		/* Create Content */
		Plan plan = controler.getModel().getPlan();
		PlanView planPanel = new PlanView(planScale, this, plan);
		/* Set content */
		centerPanel.add(planPanel, BorderLayout.CENTER);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

}
