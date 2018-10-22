package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ui.WindowHeader;

public class Window extends JFrame {
		
	/* Components */
	private WindowHeader header;
	private JPanel planSelection;
	private JPanel deliveryRequestSelection;
	
	/*Components dimensions */
	
	/*Components visibility */
	private boolean headerVisibility = true;

	/**
	 * Create a window with a header (with a title and "parameters" button),
	 * a choice of file component and a validation button. 
	 */

	public Window(){
		setLayout(new BorderLayout());
		/* Header */
		this.header = new WindowHeader(this, true, false);
		this.header.setVisible(headerVisibility);
		getContentPane().add(header, BorderLayout.NORTH);
		/* Plan Selection Panel */
		getContentPane().add(createPlanSelectionPanel(), BorderLayout.CENTER);
		/* Delivery Request Selection Panel */
		getContentPane().add(createDeliveryRequestSelectionPanel(), BorderLayout.EAST);
		/* Display */
		this.setTitle("Optimod");
		setWindowDimensions();		
		setVisible(true);
	}

	/**
	 * Defined the window and components size.
	 */
	private void setWindowDimensions() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		header.setLocation(0,0);
	}
	
	/**
	 * Create the panel for selecting a plan.
	 */
	private JPanel createPlanSelectionPanel() {
		this.planSelection = new JPanel();
		
		JLabel selectionText = new JLabel("Sélectionnez un fichier de plan au format XML :", SwingConstants.CENTER);
		planSelection.add(selectionText, BorderLayout.CENTER);
		
		return planSelection;
	}
	
	/**
	 * Create the panel for selecting a delivery request.
	 */
	private JPanel createDeliveryRequestSelectionPanel() {
		this.deliveryRequestSelection = new JPanel();
		
		JLabel selectionText = new JLabel("Sélectionnez un fichier de demande de livraisons au format XML :", SwingConstants.CENTER);
		deliveryRequestSelection.add(selectionText, BorderLayout.CENTER);
		
		return deliveryRequestSelection;
	}
	

}
