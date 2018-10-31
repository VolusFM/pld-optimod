package main.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.controler.Controler;
import main.model.ModelInterface;
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
	private final String BUTTON_TOUR_CALCUL = "Planifier la tournée";
	private final String TEXT_PLANNING_BOARD = "Planning des tournées obtenu :";

	/* Button's action */
	protected static final String ACTION_SELECTION_PLAN = "LOAD_PLAN";
	protected static final String ACTION_SELECTION_DELIVERY = "LOAD_DELIVERY";
	protected static final String ACTION_CALCULATE_TOUR = "CALCULATE_TOUR";

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
		this.header = new WindowHeader(this, true, false, buttonListener);
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
		JLabel selectionText = new JLabel(TEXT_PLAN_SELECTION);
		JButton selectionButton = createButton(BUTTON_BROWSE, ACTION_SELECTION_PLAN);
		centerPanel.add(selectionText);
		centerPanel.add(selectionButton);
		/* Set content */
		add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Create the panel for selecting a delivery request.
	 */
	private void displayDeliveryRequestSelectionPanel() {
		this.rightPanel = new JPanel();
		/* Create Content */
		JLabel selectionText = new JLabel(TEXT_DELIVERY_SELECTION);
		JButton selectionButton = createButton(BUTTON_BROWSE, ACTION_SELECTION_DELIVERY);
		rightPanel.add(selectionText);
		rightPanel.add(selectionButton);
		/* Set content */
		add(rightPanel, BorderLayout.EAST);
	}

	
	/**
	 * Create the panel with the city plan.
	 */
	public void displayPlanView() {
		remove(centerPanel);
		this.centerPanel = new JPanel();
		/* Create Content */
		Plan plan = ModelInterface.getPlan();
		PlanView planPanel = new PlanView(planScale, this, plan);
		/* Set content */
		centerPanel.add(planPanel);
		add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Create the panel with the button to calculate the planning.
	 */
	public void displayCalculateTourButtonPanel() {
		rightPanel.setVisible(false);
		rightPanel = new JPanel(); // XXX : setting a property then reconstructing is not clean
		/* Create Content */
		JButton selectionButton = createButton(BUTTON_TOUR_CALCUL, ACTION_CALCULATE_TOUR);
		rightPanel.add(selectionButton);
		/* Set content */
		rightPanel.setVisible(true);
		add(rightPanel, BorderLayout.EAST);
	}

	/**
	 * Create the panel with the planning of the tour as a board of delivery
	 * men, locations, hours and list of roads.
	 */
	public void displayTourPlanningPanel() {
		rightPanel.setVisible(false);
		rightPanel = new JPanel(); // XXX : setting a property then reconstructing is not clean
		/* Create Content */
		rightPanel.setLayout(new BorderLayout());
		JLabel planningText = new JLabel(TEXT_PLANNING_BOARD);
		PlanningView planningPanel = new PlanningView(this);
		rightPanel.add(planningText, BorderLayout.NORTH);
		rightPanel.add(planningPanel, BorderLayout.CENTER);
		/* Set content */
		rightPanel.setVisible(true);
		add(rightPanel, BorderLayout.EAST);
	}

	
	/**
	 * Convenience method to create a new button with a given text and action, 
	 * and to bind it to the action listener
	 */
	private JButton createButton(String text, String action) {
		JButton button = new JButton(text);
		button.setActionCommand(action);
		button.addActionListener(buttonListener);
		return button;
		
	}
	
}
