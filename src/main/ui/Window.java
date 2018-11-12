package main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;

public class Window extends JFrame {

    /* Attributes */
    private Controler controler;
    private int planScale = 1;

    /* Components */
    private WindowHeader header;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel southPanel;
    private JPanel deliveryMenCountPanel;
    protected static PlanView planPanel;
    protected static PlanningView planningPanel;
    protected static AddingDeliveryView addingPanel;

    /* Listeners */
    protected ButtonListener buttonListener;
    protected KeyListener keyListener;

    /* Components visibility */
    private boolean headerVisibility = true;

    /* Component's text */
    private final String WINDOW_TITLE = "Optimod";
    private final String TEXT_DELIVERY_SELECTION = "Sélectionnez un fichier de demande de livraison au format XML :";
    private final String TEXT_PLAN_SELECTION = "Sélectionnez un fichier de plan au format XML :";
    private final String BUTTON_BROWSE = "Parcourir";
    private final String BUTTON_TOUR_CALCUL = "Planifier la tournée";
    private final String TEXT_PLANNING_BOARD = "Planning des tournées obtenu :";
    private final String TEXT_DELIVERY_LENGTH_COUNT = "Nombre de Livreurs paramétré :";

    /* Button's action */
    protected static final String ACTION_SELECTION_PLAN = "LOAD_PLAN";
    protected static final String ACTION_SELECTION_DELIVERY = "LOAD_DELIVERY";
    protected static final String ACTION_CALCULATE_TOUR = "CALCULATE_TOUR";

    /**
     * Create a window with a header (with a title and "parameters" button), a
     * choice of file component and a validation button.
     */
    public Window(Controler controler) {
	setBestLookAndFeelAvailable();
	setLayout(new BorderLayout());
	/* Initialize */
	this.controler = controler;
	buttonListener = new ButtonListener(controler);
	keyListener = new KeyListener(controler);
	rightPanel = new JPanel();
	rightPanel.setPreferredSize(new Dimension(500, 900));
	southPanel = new JPanel();
	deliveryMenCountPanel = new JPanel();
	/* Header */
	this.header = new WindowHeader(this, buttonListener);
	this.header.setVisible(headerVisibility);
	getContentPane().add(header, BorderLayout.NORTH);
	/* Plan Selection Panel */
	displayPlanSelectionPanel();
	/* Display */
	this.setTitle(WINDOW_TITLE);
	setWindowDimensions();
	setVisible(true);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Create the panel with the planning of the tour as a board of delivery
     * men, locations, hours and list of roads.
     */
    public void displayTourPlanningPanel() {
	rightPanel.setVisible(false);
	rightPanel = new JPanel();
	rightPanel.setPreferredSize(new Dimension(500, 900));
	rightPanel.setLayout(new BorderLayout());
	/* Create Content */	
	JLabel planningText = new JLabel(TEXT_PLANNING_BOARD);	
	planningPanel = new PlanningView(this);
	/* Panels */
	JPanel planning = new JPanel();
	planning.add(planningText, BorderLayout.NORTH);
	planning.add(planningPanel, BorderLayout.CENTER);
	/* Set content */
	displayDeliveryMenCountPanel();
	rightPanel.add(planning, BorderLayout.CENTER);
	rightPanel.setVisible(true);
	add(rightPanel, BorderLayout.EAST);
	redraw();
    }
    
    /**
     * Create the panel witch print the count of delivery men
     */
    public void displayDeliveryMenCountPanel() {
	deliveryMenCountPanel.removeAll();
	JLabel deliveryMenCount = new JLabel(TEXT_DELIVERY_LENGTH_COUNT);
	JLabel count = new JLabel("" + ModelInterface.getDeliveryMenCount());
	deliveryMenCountPanel.add(deliveryMenCount, BorderLayout.NORTH);
	deliveryMenCountPanel.add(count, BorderLayout.CENTER);
	rightPanel.add(deliveryMenCountPanel, BorderLayout.NORTH);
	redraw();
    }

    /**
     * Create the panel with the planning of the tour as a board of delivery
     * men, locations, hours and list of roads.
     */
    public void displayAddingDeliveryPanel() {
	planningPanel.displayAddingDeliveryPanel();
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
    public void displayDeliveryRequestSelectionPanel() {
	this.rightPanel = new JPanel();
	rightPanel.setPreferredSize(new Dimension(500, 900));
	/* Create Content */
	JLabel selectionText = new JLabel(TEXT_DELIVERY_SELECTION);
	JButton selectionButton = createButton(BUTTON_BROWSE, ACTION_SELECTION_DELIVERY);
	rightPanel.add(selectionText);
	rightPanel.add(selectionButton);
	/* Set content */
	add(rightPanel, BorderLayout.EAST);
	redraw();
    }

    /**
     * Create the panel with the city plan.
     */
    public void displayPlanView() {
	remove(centerPanel);
	this.centerPanel = new JPanel();
	centerPanel.setLayout(new GridBagLayout());
	/* Create Content */
	Plan plan = ModelInterface.getPlan();
	planPanel = new PlanView(planScale, this, plan);
	PlanListener planListener = new PlanListener(controler);
	planPanel.addMouseListener(planListener);
	planPanel.addKeyListener(keyListener);

	/* GridbagLayoutDisplaying */
	GridBagConstraints displayConstraint = new GridBagConstraints();
	displayConstraint.gridx = 0;
	displayConstraint.gridy = 0;
	displayConstraint.gridwidth = GridBagConstraints.REMAINDER;
	displayConstraint.gridheight = GridBagConstraints.REMAINDER;
	displayConstraint.weightx = 1.;
	displayConstraint.weighty = 1.;
	displayConstraint.fill = GridBagConstraints.BOTH;
	displayConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
	displayConstraint.insets = new Insets(5, 5, 5, 5);
	JScrollPane scroll = new JScrollPane(planPanel);
	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	centerPanel.add(scroll, displayConstraint);
	/* Set content */
	add(centerPanel, BorderLayout.CENTER);
	redraw();
    }

    /**
     * Create the panel with the button to calculate the planning.
     */
    public void displayCalculateTourButtonPanel() {
	rightPanel.setVisible(false);
	rightPanel = new JPanel();
	rightPanel.setPreferredSize(new Dimension(500, 900));
	/* Create Content */
	JButton selectionButton = createButton(BUTTON_TOUR_CALCUL, ACTION_CALCULATE_TOUR);
	rightPanel.add(selectionButton, BorderLayout.EAST );
	displayDeliveryMenCountPanel();
	/* Set content */	
	add(rightPanel, BorderLayout.EAST);
	rightPanel.setVisible(true);
	redraw();
    }

    /**
     * Remove the panel use for creating a new delivery point
     */
    public void hideAddingDeliveryPanel() {
	planningPanel.hideAddingDeliveryPanel();
    }

    /**
     * Method used to set the lat and long fields of the adding form
     * 
     * 
     * @param lat the latitude value
     * @param lon the longitude value
     */
    public void setLatLonFieldsOfAddingPanel(double lat, double lon) {
	this.addingPanel.latitudeField.setText(" " + lat);
	this.addingPanel.longitudeField.setText(" " + lon);
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

    /**
     * Update the graphics on the window, used when we don't add/remove
     * components Update the graphics on the window, used when we don't
     * add/remove components
     */
    // FIXME visibility -> private
    public void redraw() {
	repaint();
	revalidate();
    }

    /**
     * Method call to highlight a clicked intersection on the plan view.
     * 
     * @param intersection the intersection to highlight
     */
    public void highlightSelectedIntersection(Intersection intersection) {
	planningPanel.selectRow(intersection);
	planPanel.setHighlightedIntersection(intersection);
	redraw();
    }

    /**
     * Method call to highlight a right-clicked intersection on the plan view.
     * 
     * @param intersection the intersection to highlight
     */
    public void highlightRightClickedIntersection(Intersection intersection) {
	planPanel.setRightClickedIntersection(intersection);
	redraw();
    }

    /**
     * Method call to highlight a clicked section on the plan view.
     * 
     * @param intersection the intersection to highlight
     */
    public void highlightSelectedSection(Section findClosestSection) {
	planPanel.setHighlightedSection(findClosestSection);
    }

    /**
     * Method to hide/show the parameters button from the header
     */
    public void toggleDeliveryMenCountButtonVisiblity() {
	header.toggleDeliveryMenCountButtonVisibility();
    }

    /**
     * Method to hide/show the return button from the header
     */
    public void toggleReturnButtonVisibility() {
	header.toggleReturnButtonVisibility();
    }

    /**
     * Method call to print the sections of a delivery step
     * 
     * @param step the step
     */
    public void listSectionsOfStep(Step step) {
	southPanel.removeAll();
	southPanel.setVisible(false);
	Set<String> streetNames = new LinkedHashSet<>();
	for (Section section : step.getSections()) {
	    streetNames.add(section.getStreetName());
	}
	String html = "<html>";
	Iterator<String> it = streetNames.iterator();
	html += it.next();
	while (it.hasNext()) {
	    html += " - " + it.next();
	}
	html += "</html>";
	JLabel label = new JLabel(html);
	southPanel.add(label);
	southPanel.setVisible(true);
	add(southPanel, BorderLayout.SOUTH);
	redraw();
    }

    public void forceFocusOnPlanView() {
	planPanel.requestFocus();
    }

    public void redrawTable() {
	planningPanel.redrawTable();
    }

    /**
     * Function call when the window is initialize to set a more esthetic look
     * to the app
     */
    public static void setBestLookAndFeelAvailable() {
	String system_lf = UIManager.getSystemLookAndFeelClassName().toLowerCase();
	if (system_lf.contains("metal")) {
	    try {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    } catch (Exception e) {
	    }
	} else {
	    try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    }
	}
    }
}
