package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridLayout;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;

public class PlanningView extends JPanel {

	/* Components */
	private PlanningTable planning;
	private JButton addDeliveryPoint;

	private JButton cancelModifications;

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
		/* Initialize */
		setSize(600, 900);
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
		/* Displaying */
		JPanel totalViewPanel = new JPanel();
		JPanel buttonRangePanel = new JPanel();
		buttonRangePanel.setSize(600, 200);
		JPanel tablePanel = new JPanel();
		tablePanel.setSize(600, 600);
		buttonRangePanel.add(addDeliveryPoint, BorderLayout.WEST);
		buttonRangePanel.add(cancelModifications, BorderLayout.EAST);
		tablePanel.add(new JScrollPane(planning));
		totalViewPanel.setLayout(new GridLayout (2,1));
		totalViewPanel.add(buttonRangePanel);
		totalViewPanel.add(tablePanel);
		this.add(totalViewPanel);
	}

	public void selectRow(Intersection closestIntersection) {
		List<Delivery> deliveries = ModelInterface.getDeliveries();
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