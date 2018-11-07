package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddingDeliveryView extends JPanel {

	private Window window;
	/* Components */
	private JTextField latitudeField;
	private JTextField longitudeField;
	private JTextField durationField;
	private JComboBox<Integer> deliveryMenBox;
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
	private final String instructionsText1 = "Saisissez les informations de cette noouvelle livraison";
	private final String instructionsText2 = "(cliquez sur le plan pour obtenir les coordonées GPS d'une adresse).";

	/**
	 * Create the view to defined a new delivery point.
	 * 
	 */
	// FIXME : doc is not matching constructor
	public AddingDeliveryView(Window w) {
		super();
		window = w;
		/* Initialize */
		setSize(600, 500);
		/* Display */
		setBackground(Color.WHITE);
		createAddingDeliveryPanel();
	}

	/**
	 * Function called to create the planning panel.
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
		latitudeField = new JTextField("latitude");
		longitudeField = new JTextField("longitude");
		durationField = new JTextField("durée");
		deliveryMenBox = new JComboBox<Integer>();
		validationButton = new JButton(validationText);
		validationButton.setBackground(Color.GREEN);
		validationButton.setActionCommand(ACTION_VALIDATION_ADDING_DELIVERY);
		validationButton.addActionListener(window.buttonListener);
		cancelationButton = new JButton(cancelationText);
		cancelationButton.setBackground(Color.RED);
		validationButton.setActionCommand(ACTION_CANCELATION_ADDING_DELIVERY);
		validationButton.addActionListener(window.buttonListener);
		/* Panels */
		JPanel durationPanel = new JPanel();
		durationPanel.add(durationLabel, BorderLayout.WEST);
		durationPanel.add(durationField, BorderLayout.EAST);
		JPanel latitudePanel = new JPanel();
		latitudePanel.add(latitudeLabel, BorderLayout.WEST);
		latitudePanel.add(latitudeField, BorderLayout.EAST);
		JPanel longitudePanel = new JPanel();
		longitudePanel.add(longitudeLabel, BorderLayout.WEST);

		longitudePanel.add(longitudeField, BorderLayout.EAST);
		JPanel deliveryMenPanel = new JPanel();
		deliveryMenPanel.add(deliveryMenLabel, BorderLayout.WEST);
		deliveryMenPanel.add(deliveryMenBox, BorderLayout.EAST);
		/* Displaying */

		JPanel totalPanel =  new JPanel();
		JPanel instructionsPanel = new JPanel();
		JPanel formPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		formPanel.setLayout(new GridLayout(2, 2));
		buttonsPanel.setLayout(new GridLayout(1, 2));
		instructionsPanel.setLayout(new GridLayout(2, 1));
		totalPanel.setLayout(new GridLayout(3, 1));
		formPanel.add(durationPanel);
		formPanel.add(latitudePanel);
		formPanel.add(deliveryMenPanel);
		formPanel.add(longitudePanel);
		instructionsPanel.add(instructionsLabel1);
		instructionsPanel.add(instructionsLabel2);
		buttonsPanel.add(validationButton);
		buttonsPanel.add(cancelationButton);
		totalPanel.add(instructionsPanel);
		totalPanel.add(formPanel);
		totalPanel.add(buttonsPanel);
		this.add(totalPanel);
	}
}