package main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.model.Delivery;
import main.model.ModelInterface;
import main.model.Tour;

/**
 * Form panel for the adding delivery purpose. Form with validate and cancel
 * buttons, and data fields.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class AddingDeliveryView extends JPanel {

    /* Id */
    private static final long serialVersionUID = 1L;

    private Window window;
    /* Components */
    protected JTextField latitudeField;
    protected JTextField longitudeField;
    private JFormattedTextField durationField;
    private JComboBox<Integer> deliveryMenBox;
    private JComboBox<Delivery> precedingDeliveryBox;
    private JButton validationButton;
    private JButton cancelationButton;

    /* Buttons Actions */
    protected final static String ACTION_VALIDATION_ADDING_DELIVERY = "Valider le nouveau point de livraison";
    protected final static String ACTION_CANCELATION_ADDING_DELIVERY = "Annuler le nouveau point de livraison";

    /* Labels texts */
    private final String validationText = "Valider";
    private final String cancelationText = "Annuler";
    private final String durationText = "Duree : ";
    private final String latitudeText = "Latitude : ";
    private final String longitudeText = "Longitude : ";
    private final String deliveryMenText = "Livreur : ";
    private final String previousDeliveryHoursText = "Précédente livraison : ";
    private final String instructionsText1 = "Saisissez les informations de cette nouvelle livraison";
    private final String instructionsText2 = "(cliquez sur le plan pour obtenir les coordonées GPS d'une adresse, et";
    private final String instructionsText3 = " cliquez dans le tableau pour obtenir les informations d'une livraison).";

    /* Listeners */
    private ComboboxListener comboxListener;

    /**
     * Create the view to defined a new delivery point.
     * 
     * @param w the Window in which this will be used.
     */
    public AddingDeliveryView(Window w) {
	super();
	/* Initialize */
	this.window = w;
	this.latitudeField = new JTextField();
	this.longitudeField = new JTextField();
	this.comboxListener = new ComboboxListener();
	/* Display */
	createAddingDeliveryPanel();
    }

    /**
     * Create the adding form.
     */
    private void createAddingDeliveryPanel() {
	/* Labels */
	JLabel durationLabel = new JLabel(this.durationText);
	JLabel latitudeLabel = new JLabel(this.latitudeText);
	JLabel longitudeLabel = new JLabel(this.longitudeText);
	JLabel deliveryMenLabel = new JLabel(this.deliveryMenText);
	JLabel previousDeliveryLabel = new JLabel(this.previousDeliveryHoursText);
	JLabel instructionsLabel1 = new JLabel(this.instructionsText1);
	JLabel instructionsLabel2 = new JLabel(this.instructionsText2);
	JLabel instructionsLabel3 = new JLabel(this.instructionsText3);
	/* Components */
	this.latitudeField = new JTextField();
	this.longitudeField = new JTextField();
	this.durationField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	this.deliveryMenBox = new JComboBox<Integer>(createDeliveryMenIdVector());
	this.precedingDeliveryBox = new JComboBox<Delivery>(new Vector<Delivery>(ModelInterface.getDeliveriesById(0)));
	this.validationButton = new JButton(this.validationText);
	this.validationButton.setBackground(Color.GREEN);
	this.validationButton.setActionCommand(ACTION_VALIDATION_ADDING_DELIVERY);
	this.validationButton.addActionListener(this.window.buttonListener);
	this.cancelationButton = new JButton(this.cancelationText);
	this.cancelationButton.setBackground(Color.RED);
	this.cancelationButton.setActionCommand(ACTION_CANCELATION_ADDING_DELIVERY);
	this.cancelationButton.addActionListener(this.window.buttonListener);
	/* Components dimensions */
	this.latitudeField.setPreferredSize(new Dimension(125, 20));
	this.longitudeField.setPreferredSize(new Dimension(125, 20));
	this.durationField.setPreferredSize(new Dimension(125, 20));
	this.deliveryMenBox.setPreferredSize(new Dimension(125, 20));
	this.precedingDeliveryBox.setPreferredSize(new Dimension(200, 20));
	/* Listeners */
	this.deliveryMenBox.addItemListener(this.comboxListener);
	/* GridBagLayout Display */
	JPanel totalPanel = new JPanel();
	totalPanel.setLayout(new GridBagLayout());
	GridBagConstraints displayConstraint = new GridBagConstraints();
	/* Instruction text display */
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 0;
	displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	displayConstraint.gridheight = 1;
	displayConstraint.weightx = 1.;
	displayConstraint.weighty = 1.;
	displayConstraint.fill = GridBagConstraints.BOTH;
	displayConstraint.anchor = GridBagConstraints.LINE_START;
	displayConstraint.insets = new Insets(5, 5, 5, 5);
	totalPanel.add(instructionsLabel1, displayConstraint);
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 1;
	totalPanel.add(instructionsLabel2, displayConstraint);
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 2;
	totalPanel.add(instructionsLabel3, displayConstraint);
	/* Form */
	displayConstraint.gridwidth = 1;
	displayConstraint.fill = GridBagConstraints.NONE;
	/* Duration label and field */
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 3;
	totalPanel.add(durationLabel, displayConstraint);
	displayConstraint.gridx = 1;
	displayConstraint.gridy = 3;
	totalPanel.add(this.durationField, displayConstraint);
	/* Latitude label and field */
	displayConstraint.gridx = 2;
	displayConstraint.gridy = 3;
	totalPanel.add(latitudeLabel, displayConstraint);
	displayConstraint.gridx = 3;
	displayConstraint.gridy = 3;
	totalPanel.add(this.latitudeField, displayConstraint);
	/* Delivery Men label and box */
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 4;
	totalPanel.add(deliveryMenLabel, displayConstraint);
	displayConstraint.gridx = 1;
	displayConstraint.gridy = 4;
	totalPanel.add(this.deliveryMenBox, displayConstraint);
	/* Longitude label and field */
	displayConstraint.gridx = 2;
	displayConstraint.gridy = 4;
	totalPanel.add(longitudeLabel, displayConstraint);
	displayConstraint.gridx = 3;
	displayConstraint.gridy = 4;
	totalPanel.add(this.longitudeField, displayConstraint);
	/* Previous delivery box */
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 5;
	displayConstraint.gridwidth = 3;
	totalPanel.add(previousDeliveryLabel, displayConstraint);
	displayConstraint.gridx = 2;
	displayConstraint.gridy = 5;
	displayConstraint.anchor = GridBagConstraints.LINE_START;
	totalPanel.add(this.precedingDeliveryBox, displayConstraint);
	/* Validation Button */
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 6;
	totalPanel.add(this.validationButton, displayConstraint);
	/* Cancellation Button */
	displayConstraint.gridx = 3;
	displayConstraint.gridy = 6;
	displayConstraint.anchor = GridBagConstraints.LINE_END;
	displayConstraint.insets = new Insets(5, 5, 5, 12);
	totalPanel.add(this.cancelationButton, displayConstraint);
	this.add(totalPanel);
    }

    /**
     * Function used to initialize our combobox with delivery men id.
     */
    private static Vector<Integer> createDeliveryMenIdVector() {
	Vector<Integer> deliveryMenId = new Vector<Integer>();
	for (Tour deleveryMenTour : ModelInterface.getTourPlanning()) {
	    if (!deliveryMenId.contains(deleveryMenTour.getDeliveryManId())) {
		deliveryMenId.add(deleveryMenTour.getDeliveryManId());
	    }
	}
	return deliveryMenId;
    }

    /**
     * Fill the delivery combobox when selecting the delivery men.
     */
    protected void updatePreviousDeliveryCombobox() {
	List<Delivery> deliveryMenDeliveries = ModelInterface.getDeliveriesById(getSelectedDeliveryMen());
	this.precedingDeliveryBox.removeAllItems();
	for (Delivery delivery : deliveryMenDeliveries) {
	    this.precedingDeliveryBox.addItem(delivery);
	}
    }

    /**
     * Get the selected value of the delivery men id combobox.
     * 
     * @return Integer, the selected delivery men id.
     */
    public int getSelectedDeliveryMen() {
	try {
	    return (Integer) this.deliveryMenBox.getSelectedItem();
	} catch (NumberFormatException e) {
	    return -1;
	}
    }

    /**
     * Get the written value of the duration field.
     * 
     * @return Integer, the selected duration.
     */
    public int getSelectedDuration() {
	try {
	    return Integer.valueOf(this.durationField.getText());
	} catch (NumberFormatException e) {
	    return -1;
	}
    }

    /**
     * Get the written value of the latitude field.
     * 
     * @return Integer, the selected latitude.
     */
    public double getSelectedLat() {
	try {
	    return Double.valueOf(this.latitudeField.getText());
	} catch (NumberFormatException e) {
	    return -1;
	}
    }

    /**
     * Get the written value of the longitude field.
     * 
     * @return Integer, the selected longitude.
     */
    public double getSelectedLon() {
	try {
	    return Double.valueOf(this.longitudeField.getText());
	} catch (NumberFormatException e) {
	    return -1;
	}
    }

    /**
     * Function used to get the selected value of the preceding delivery combobox.
     * 
     * @return Delivery, the selected delivery.
     */
    public Delivery getSelectedPrecedingDelivery() {
	try {
	    return (Delivery) this.precedingDeliveryBox.getSelectedItem();
	} catch (Exception e) {
	    return null;
	}
    }

}