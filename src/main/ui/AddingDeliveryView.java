package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddingDeliveryView extends JPanel {

	private Window window;
	/* Components */
	protected JTextField latitudeField;
	protected JTextField longitudeField;
	protected JTextField durationField;
	protected JComboBox<Integer> deliveryMenBox;
	private JButton validationButton;
	private JButton cancelationButton;

	/* Buttons Actions */
	protected final static String ACTION_VALIDATION_ADDING_DELIVERY 	= "Valider le nouveau point de livraison";
	protected final static String ACTION_CANCELATION_ADDING_DELIVERY 	= "Annuler le nouveau point de livraison";

	/* Labels texts */
	private final String validationText 								= "Valider";
	private final String cancelationText 								= "Annuler";
	private final String durationText 									= "Duree : ";
	private final String latitudeText 									= "Latitude : ";
	private final String longitudeText									= "Longitude : ";
	private final String deliveryMenText 								= "Livreur : ";
	private final String instructionsText1 								= "Saisissez les informations de cette nouvelle livraison";
	private final String instructionsText2 								= "(cliquez sur le plan pour obtenir les coordon�es GPS d'une adresse).";

	/**
	 * Create the view to defined a new delivery point.
	 * @param w the the Window in which this will be used
	 * (to access to the listeners)
	 */
	public AddingDeliveryView(Window w) {
		super();
		/* Initialize */
		window = w;
		latitudeField = new JTextField();
		longitudeField = new JTextField();
		/* Display */
		createAddingDeliveryPanel();
	}

	/**
	 * Function called to create the adding form.
	 */
	public void createAddingDeliveryPanel() {
		/* Labels */
		JLabel durationLabel = new JLabel(durationText);
		JLabel latitudeLabel = new JLabel(latitudeText);
		JLabel longitudeLabel = new JLabel(longitudeText);
		JLabel deliveryMenLabel = new JLabel(deliveryMenText);
		JLabel instructionsLabel1 = new JLabel(instructionsText1);
		JLabel instructionsLabel2 = new JLabel(instructionsText2);
		/* Components */
		latitudeField = new JTextField();
		longitudeField = new JTextField();
		durationField = new JTextField();
		deliveryMenBox = new JComboBox<Integer>();
		validationButton = new JButton(validationText);
		validationButton.setBackground(Color.GREEN);
		validationButton.setActionCommand(ACTION_VALIDATION_ADDING_DELIVERY);
		validationButton.addActionListener(window.buttonListener);
		cancelationButton = new JButton(cancelationText);
		cancelationButton.setBackground(Color.RED);
		cancelationButton.setActionCommand(ACTION_CANCELATION_ADDING_DELIVERY);
		cancelationButton.addActionListener(window.buttonListener);
		/* Components dimensions */
		latitudeField.setPreferredSize(new Dimension(100, 20));
		longitudeField.setPreferredSize(new Dimension(100, 20));
		durationField.setPreferredSize(new Dimension(100, 20));
		deliveryMenBox.setPreferredSize(new Dimension(100, 20));
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
		/* Form */
		displayConstraint.gridwidth = 1;
		displayConstraint.fill = GridBagConstraints.NONE;
		/* Duration label and field */
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 2;
		totalPanel.add(durationLabel, displayConstraint);
		displayConstraint.gridx = 1;
		displayConstraint.gridy = 2;
		totalPanel.add(durationField, displayConstraint);
		/* Latitude label and field */
		displayConstraint.gridx = 2;
		displayConstraint.gridy = 2;
		totalPanel.add(latitudeLabel, displayConstraint);
		displayConstraint.gridx = 3;
		displayConstraint.gridy = 2;
		totalPanel.add(latitudeField, displayConstraint);
		/* Delivery Men label and box */
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 3;
		totalPanel.add(deliveryMenLabel, displayConstraint);
		displayConstraint.gridx = 1;
		displayConstraint.gridy = 3;
		totalPanel.add(deliveryMenBox, displayConstraint);
		/* Longitude label and field */
		displayConstraint.gridx = 2;
		displayConstraint.gridy = 3;
		totalPanel.add(longitudeLabel, displayConstraint);
		displayConstraint.gridx = 3;
		displayConstraint.gridy = 3;
		totalPanel.add(longitudeField, displayConstraint);
		/* Validation Button */
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 4;
		totalPanel.add(validationButton, displayConstraint);
		/* Cancelation Button */
		displayConstraint.gridx = 3;
		displayConstraint.gridy = 4;
		displayConstraint.anchor = GridBagConstraints.LINE_END;
		displayConstraint.insets = new Insets(5, 5, 5, 12);
		totalPanel.add(cancelationButton, displayConstraint);
		this.add(totalPanel);
	}
}