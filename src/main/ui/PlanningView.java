package main.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.controler.Controler;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Tour;

/**
 * Panel including the planning table and buttons.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class PlanningView extends JPanel {

    /* Id */
    private static final long serialVersionUID = 1L;

    /* Components */
    private PlanningTable planning;
    private Controler controler;
    private JButton addDeliveryPoint;
    private JButton supressDelivery;
    private JButton cancelModifications;
    private JPanel totalViewPanel;
    private AddingDeliveryView addingPanel;

    /* Listener */
    private ButtonListener buttonListener;

    /* Components texts */
    private final static String ADD_DELIVERY_POINT_BUTTON = "Ajouter une livraison";
    private final static String SUPRESS_DELIVERY_POINT_BUTTON = "Supprimer la livraison sélectionnée";
    private final static String CANCEL_MODIFICATIONS_BUTTON = "Annuler";

    /* Actions */
    protected final static String ACTION_ADDING_DELIVERY_POINT = "ADD_DELIVERY_POINT";
    protected final static String ACTION_SUPRESSING_DELIVERY_POINT = "SUPPRES_DELIVERY_POINT";
    protected final static String ACTION_CANCELLING_MODIFICATIONS = "CANCEL_MODIFICATIONS";

    /* Graphic components */
    private Window window;
    private GridBagConstraints displayConstraint;

    /**
     * Create the textual view for printing the panning as a table in the specified
     * window of the specified controller.
     * 
     * @param c is the application's controller.
     * @param w is the application's graphical window.
     */
    public PlanningView(Controler c, Window w) {
	super();
	this.buttonListener = w.buttonListener;
	this.controler = c;
	this.window = w;
	/* Display */
	createBoardPanel();
    }

    /**
     * Create the planning panel.
     */
    public void createBoardPanel() {
	/* Building board */
	this.planning = new PlanningTable();
	PlanningListener planningListener = new PlanningListener(this.planning, this.controler);
	this.planning.getSelectionModel().addListSelectionListener(planningListener);
	/* Buttons */
	this.addDeliveryPoint = new JButton(ADD_DELIVERY_POINT_BUTTON);
	this.addDeliveryPoint.setActionCommand(ACTION_ADDING_DELIVERY_POINT);
	this.addDeliveryPoint.addActionListener(this.buttonListener);
	this.supressDelivery = new JButton(SUPRESS_DELIVERY_POINT_BUTTON);
	this.supressDelivery.setActionCommand(ACTION_SUPRESSING_DELIVERY_POINT);
	this.supressDelivery.addActionListener(this.buttonListener);
	this.cancelModifications = new JButton(CANCEL_MODIFICATIONS_BUTTON);
	this.cancelModifications.setActionCommand(ACTION_CANCELLING_MODIFICATIONS);
	this.cancelModifications.addActionListener(this.buttonListener);
	this.cancelModifications.setVisible(false);
	/* Button Panel */
	JPanel buttonRangePanel = new JPanel();
	buttonRangePanel.setPreferredSize(new Dimension(600, 30));
	this.displayConstraint = new GridBagConstraints();
	// 3 buttons aligned and filling the line
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 0;
	this.displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	this.displayConstraint.gridheight = 1;
	this.displayConstraint.anchor = GridBagConstraints.LINE_START;
	this.displayConstraint.insets = new Insets(5, 0, 5, 0);
	buttonRangePanel.add(this.addDeliveryPoint, this.displayConstraint);
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 1;
	buttonRangePanel.add(this.supressDelivery, this.displayConstraint);
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 2;
	buttonRangePanel.add(this.cancelModifications, this.displayConstraint);
	/* Table Panel */
	JPanel tablePanel = new JPanel();
	tablePanel.add(new JScrollPane(this.planning));
	/* Total Panel */
	this.totalViewPanel = new JPanel();
	this.totalViewPanel.setLayout(new GridBagLayout());
	this.displayConstraint = new GridBagConstraints();
	// Buttons Panels upper and larger
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 0;
	this.displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	this.displayConstraint.gridheight = 1;
	this.displayConstraint.fill = GridBagConstraints.BOTH;
	this.displayConstraint.anchor = GridBagConstraints.LINE_START;
	this.displayConstraint.insets = new Insets(5, 0, 5, 0);
	this.totalViewPanel.add(buttonRangePanel, this.displayConstraint);
	// Table just behind and fill the place
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 1;
	this.displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	this.displayConstraint.gridheight = 1;
	this.displayConstraint.weightx = 1.;
	this.displayConstraint.weighty = 1.;
	this.displayConstraint.fill = GridBagConstraints.BOTH;
	this.displayConstraint.anchor = GridBagConstraints.LINE_START;
	this.displayConstraint.insets = new Insets(5, 0, 5, 0);
	this.totalViewPanel.add(tablePanel, this.displayConstraint);
	this.add(this.totalViewPanel);
    }

    /**
     * Display the elements to add a new delivery point
     */
    protected void displayAddingDeliveryPanel() {
	this.totalViewPanel.setVisible(false);
	setAddingPanel(new AddingDeliveryView(this.window));
	/* GridBagLayout Displaying */
	this.displayConstraint.gridx = 0;
	this.displayConstraint.gridy = 2;
	this.displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	this.displayConstraint.gridheight = 1;
	this.displayConstraint.weightx = 1.;
	this.displayConstraint.weighty = 1.;
	this.displayConstraint.fill = GridBagConstraints.BOTH;
	this.displayConstraint.anchor = GridBagConstraints.LINE_START;
	this.displayConstraint.insets = new Insets(5, 0, 5, 0);
	/* Displaying */
	this.totalViewPanel.add(getAddingPanel(), this.displayConstraint);
	this.totalViewPanel.setVisible(true);
    }

    /**
     * Hide the elements to add a new delivery point
     */
    protected void hideAddingDeliveryPanel() {
	this.totalViewPanel.setVisible(false);
	getAddingPanel().setVisible(false);
	getAddingPanel().removeAll();
	this.totalViewPanel.setVisible(true);
    }

    /**
     * Synchronize the textual view with the plan : a click in the plan selects the
     * corresponding row of the planning if it exists.
     * 
     * @param selectedIntersection is the intersection selected in the plan.
     */
    public void selectRow(Intersection selectedIntersection) {
	List<Tour> tours = ModelInterface.getTourPlanning();
	List<Delivery> deliveries = new ArrayList<>();
	for (Tour tour : tours) {
	    for (Delivery delivery : tour.getDeliveryPoints()) {
		deliveries.add(delivery);
	    }
	}
	Iterator<Delivery> it = deliveries.iterator();
	List<Integer> indexes = new ArrayList<>();
	int i = 0;
	while (it.hasNext()) {
	    if (it.next().getAddress().getId() == selectedIntersection.getId()) {
		indexes.add(i);
	    }
	    i++;
	}
	this.planning.selectRow(indexes);
    }

    /**
     * Force the planning table to be redrawn.
     */
    public void redrawTable() {
	this.planning.redrawTable();
    }

    /**
     * Getter for the attribute addingPanel.
     * 
     * @return AddingDeliveryView, the adding delivery form panel.
     */
    public AddingDeliveryView getAddingPanel() {
	return this.addingPanel;
    }

    /**
     * Setter for the attribute addingPanel.
     * 
     * @param addingPanel is the adding delivery form panel to set.
     */
    public void setAddingPanel(AddingDeliveryView addingPanel) {
	this.addingPanel = addingPanel;
    }

}