package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddingDeliveryView extends JPanel {
	
	/* Components */ 
	private JTextField latitudeField;
	private JTextField longitudeField;
	private JTextField durationField;
	private JComboBox<Integer> deliveryMenBox;
	
	/* Labels texts */
	private final String durationText 		= "Duree : ";
	private final String latitudeText 		= "Latitude : ";
	private final String longitudeText 		= "Longitude : ";
	private final String deliveryMenText 	= "Livreur : ";

	/**
	 * Create the view to defined a new delivery point.
	 * 
	 */
	// FIXME : doc is not matching constructor
	public AddingDeliveryView(Window w) {
		super();
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
		/* Components */
		latitudeField = new JTextField();
		longitudeField = new JTextField();
		durationField = new JTextField();
		deliveryMenBox = new JComboBox<Integer>();
		/* Panels */
		JPanel durationPanel = new JPanel();
		durationPanel.add(durationLabel, BorderLayout.WEST);
		durationPanel.add(durationField, BorderLayout.EAST);
		JPanel latitudePanel = new JPanel();
		latitudePanel.add(latitudeLabel, BorderLayout.WEST);
		latitudePanel.add(latitudeField, BorderLayout.EAST);
		JPanel longitudePanel = new JPanel();
		longitudePanel.add(longitudeLabel, BorderLayout.WEST);
		longitudePanel.add(durationField, BorderLayout.EAST);
		JPanel deliveryMenPanel = new JPanel();
		deliveryMenPanel.add(deliveryMenLabel, BorderLayout.WEST);
		deliveryMenPanel.add(deliveryMenBox, BorderLayout.EAST);
		/* Displaying */
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		east.add(durationPanel);
		east.add(deliveryMenPanel);
		west.add(latitudePanel);
		west.add(longitudePanel);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
	}
}