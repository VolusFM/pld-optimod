package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Tour;

public class PlanningView extends JPanel {

	/* Components */
	private PlanningTable planning;
	private JButton addDeliveryPoint;
	private JButton cancelModifications;
	private JPanel totalViewPanel;
	private AddingDeliveryView addingPanel;

	/* Listener */
	private ButtonListener buttonListener;

	/* Components texts */
	private final String ADD_DELIVERY_POINT_BUTTON = "Ajouter un point de livraison";
	private final String CANCEL_MODIFICATIONS_BUTTON = "Annuler la dernière modification";

	/* Actions */
	protected final static String ACTION_ADDING_DELIVERY_POINT = "ADD_DELIVERY_POINT";
	protected final static String ACTION_CANCELLING_MODIFICATIONS = "CANCEL_MODIFICATIONS";

	/* Board attributes */
	private final int columnsNumber = 4;
	private final String[] boardTitle = { "Livreur", "Adresse", "Heure de passage", "Trajet" };

	/* Graphic components */
	private Window window;
	private GridBagConstraints displayConstraint;

	/**
	 * Create the graphical view for drawing the loaded plan with the scale s in
	 * the specified window w.
	 * 
	 * @param s
	 *            the scale
	 * @param w
	 *            the window
	 * @param p
	 *            the plan to print
	 */
	// FIXME : doc is not matching constructor
	public PlanningView(Window w) {
		super();
		this.buttonListener = w.buttonListener;
		this.window = w;
		/* Display */
		setBackground(Color.WHITE);
		createBoardPanel();
	}
	// TODO : mettre l'observer

	/**
	 * Function called to create the planning panel.
	 */
	public void createBoardPanel() {
		/* Building board */
		planning = new PlanningTable();
		PlanningListener planningListener = new PlanningListener(planning, window);
		planning.getSelectionModel().addListSelectionListener(planningListener);		
		/* Buttons */
		addDeliveryPoint = new JButton(ADD_DELIVERY_POINT_BUTTON);
		addDeliveryPoint.setActionCommand(ACTION_ADDING_DELIVERY_POINT);
		addDeliveryPoint.addActionListener(buttonListener);
		cancelModifications  = new JButton(CANCEL_MODIFICATIONS_BUTTON);
		cancelModifications.setActionCommand(ACTION_CANCELLING_MODIFICATIONS);
		cancelModifications.addActionListener(buttonListener);
		/* Panels */
		totalViewPanel = new JPanel();
		JPanel buttonRangePanel = new JPanel();
		JPanel tablePanel = new JPanel();
		buttonRangePanel.add(addDeliveryPoint, BorderLayout.WEST);
		buttonRangePanel.add(cancelModifications, BorderLayout.EAST);
		tablePanel.add(new JScrollPane(planning));

		//totalViewPanel.setLayout(new GridLayout(2,1));
		//totalViewPanel.add(buttonRangePanel);
		//totalViewPanel.add(tablePanel);

		totalViewPanel.setLayout(new GridBagLayout());
		/* GridBagLayout Displaying */
		displayConstraint = new GridBagConstraints();
		// Buttons Panels upper and larger
		displayConstraint.gridx = displayConstraint.gridy = 0; 
		displayConstraint.gridwidth = GridBagConstraints.REMAINDER; 
		displayConstraint.gridheight = 1; 
		displayConstraint.anchor = GridBagConstraints.LINE_START; 
		displayConstraint.insets = new Insets(5, 0, 5, 0); 
		totalViewPanel.add(buttonRangePanel, displayConstraint);
		// Table just behind and fill the place
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 1;
		displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
		displayConstraint.gridheight = 1; 
		displayConstraint.weightx = 1.;
		displayConstraint.weighty = 1.;
		displayConstraint.fill = GridBagConstraints.BOTH;
		displayConstraint.anchor = GridBagConstraints.LINE_START;
		displayConstraint.insets = new Insets(5, 0, 5, 0);
		totalViewPanel.add(tablePanel, displayConstraint);

		this.add(totalViewPanel);
	}
	
	/** 
	 * Method displaying the elements to add a new delivery point 
	 */
	protected void displayAddingDeliveryPanel(){
		totalViewPanel.setVisible(false);
		addingPanel = new AddingDeliveryView(window);
		/* GridBagLayout Displaying */
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 2;
		displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
		displayConstraint.gridheight = 1; 
		displayConstraint.weightx = 1.;
		displayConstraint.weighty = 1.;
		displayConstraint.fill = GridBagConstraints.BOTH;
		displayConstraint.anchor = GridBagConstraints.LINE_START;
		displayConstraint.insets = new Insets(5, 0, 5, 0);
		/* Displaying */
		totalViewPanel.add(addingPanel, displayConstraint);
		totalViewPanel.setVisible(true);
	}
	
	/** 
	 * Method hiding the elements to add a new delivery point 
	 */
	protected void hideAddingDeliveryPanel(){
		totalViewPanel.setVisible(false);
		addingPanel.setVisible(false);
		addingPanel.removeAll();
		totalViewPanel.setVisible(true);
	}
	
	/**
	 * Method used to synchronize the textual view with the plan 
	 * A click in the plan select the corresponding rox of the planning
	 * if existing
	 * @param closestIntersection
	 */
	public void selectRow(Intersection closestIntersection) {
		List<Tour> tours = ModelInterface.getTourPlanning();
		List<Delivery> deliveries = new ArrayList<>();

		for (Tour tour : tours) {
			for (Delivery delivery : tour.getDeliveryPoints()) {
				deliveries.add(delivery);
			}
		}
		
		Iterator<Delivery> it = deliveries.iterator();
		boolean found = false;
		int i = 0;
		while (!found && it.hasNext()) {
			if (it.next().getAddress().getId() == closestIntersection.getId()) {
				planning.selectRow(i);
				found = true;
			}
			i++;
		}
	}

}