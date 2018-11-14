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
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER Léo, THOLOT Cassandre
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
    private final String ADD_DELIVERY_POINT_BUTTON = "Ajouter une livraison";
    private final String SUPRESS_DELIVERY_POINT_BUTTON = "Supprimer la livraison sélectionnée";
    private final String CANCEL_MODIFICATIONS_BUTTON = "Annuler";

    /* Actions */
    protected final static String ACTION_ADDING_DELIVERY_POINT = "ADD_DELIVERY_POINT";
    protected final static String ACTION_SUPRESSING_DELIVERY_POINT = "SUPPRES_DELIVERY_POINT";
    protected final static String ACTION_CANCELLING_MODIFICATIONS = "CANCEL_MODIFICATIONS";

    /* Graphic components */
    private Window window;
    private GridBagConstraints displayConstraint;

    /**
     * Create the textual view for printing the panning as a table in the
     * specified window of the specified controller.
     * 
     * @param c the controller.
     * @param w the window.
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
     * Function called to create the planning panel.
     */
    public void createBoardPanel() {
	/* Building board */
	planning = new PlanningTable();
	PlanningListener planningListener = new PlanningListener(planning, controler);
	planning.getSelectionModel().addListSelectionListener(planningListener);
	/* Buttons */
	addDeliveryPoint = new JButton(ADD_DELIVERY_POINT_BUTTON);
	addDeliveryPoint.setActionCommand(ACTION_ADDING_DELIVERY_POINT);
	addDeliveryPoint.addActionListener(buttonListener);
	supressDelivery = new JButton(SUPRESS_DELIVERY_POINT_BUTTON);
	supressDelivery.setActionCommand(ACTION_SUPRESSING_DELIVERY_POINT);
	supressDelivery.addActionListener(buttonListener);
	cancelModifications = new JButton(CANCEL_MODIFICATIONS_BUTTON);
	cancelModifications.setActionCommand(ACTION_CANCELLING_MODIFICATIONS);
	cancelModifications.addActionListener(buttonListener);
	cancelModifications.setVisible(false);
	/* Button Panel */
	JPanel buttonRangePanel = new JPanel();
	buttonRangePanel.setPreferredSize(new Dimension(600, 30));
	displayConstraint = new GridBagConstraints();
	// 3 buttons aligned and filling the line
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 0;
	displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	displayConstraint.gridheight = 1;
	displayConstraint.anchor = GridBagConstraints.LINE_START;
	displayConstraint.insets = new Insets(5, 0, 5, 0);
	buttonRangePanel.add(addDeliveryPoint, displayConstraint);
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 1;
	buttonRangePanel.add(supressDelivery, displayConstraint);
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 2;
	buttonRangePanel.add(cancelModifications, displayConstraint);
	/* Table Panel */
	JPanel tablePanel = new JPanel();
	tablePanel.add(new JScrollPane(planning));
	/* Total Panel */
	totalViewPanel = new JPanel();
	totalViewPanel.setLayout(new GridBagLayout());
	displayConstraint = new GridBagConstraints();
	// Buttons Panels upper and larger
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 0;
	displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	displayConstraint.gridheight = 1;
	displayConstraint.fill = GridBagConstraints.BOTH;
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
    protected void displayAddingDeliveryPanel() {
	totalViewPanel.setVisible(false);
	setAddingPanel(new AddingDeliveryView(window));
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
	totalViewPanel.add(getAddingPanel(), displayConstraint);
	totalViewPanel.setVisible(true);
    }

    /**
     * Method hiding the elements to add a new delivery point
     */
    protected void hideAddingDeliveryPanel() {
	totalViewPanel.setVisible(false);
	getAddingPanel().setVisible(false);
	getAddingPanel().removeAll();
	totalViewPanel.setVisible(true);
    }

    /**
     * Method used to synchronize the textual view with the plan A click in the
     * plan select the corresponding rox of the planning if existing
     * 
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
	List<Integer> indexes = new ArrayList<>();
	int i = 0;
	while (it.hasNext()) {
	    if (it.next().getAddress().getId() == closestIntersection.getId()) {
		indexes.add(i);
	    }
	    i++;
	}
	planning.selectRow(indexes);
    }

    /**
     * Function call to force the planning table to be redraw.
     */
    public void redrawTable() {
	planning.redrawTable();
    }

    /**
     * Getter for the attribute addingPanel.
     * 
     * @return addingPanel, the adding delivery form panel.
     */
    public AddingDeliveryView getAddingPanel() {
	return addingPanel;
    }

    /**
     * Setter for the attribute addingPanel.
     * 
     * @param addingPanel the adding delivery form panel to set.
     */
    public void setAddingPanel(AddingDeliveryView addingPanel) {
	this.addingPanel = addingPanel;
    }

}