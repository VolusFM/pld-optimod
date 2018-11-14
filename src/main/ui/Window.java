package main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;

/**
 * Window of the application, receiving order from the controller in order to
 * adapt its displaying and transmitting the users actions to the controller.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class Window extends JFrame {

    /* Id */
    private static final long serialVersionUID = 1L;

    /* Attributes */
    protected Controler controler;

    /* Components */
    private WindowHeader header;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel southPanel;
    private JPanel deliveryMenCountPanel;
    protected static PlanView planPanel;
    private static PlanningView planningPanel;
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
     * 
     * @param controler the specified controller to work for.
     */
    public Window(Controler controler) {
	setBestLookAndFeelAvailable();
	setLayout(new BorderLayout());
	/* Initialize */
	this.controler = controler;
	this.buttonListener = new ButtonListener(controler);
	this.keyListener = new KeyListener(controler);
	this.rightPanel = new JPanel();
	this.rightPanel.setPreferredSize(new Dimension(500, 900));
	this.southPanel = new JPanel();
	this.deliveryMenCountPanel = new JPanel();
	/* Header */
	this.header = new WindowHeader(this, this.buttonListener);
	this.header.setVisible(this.headerVisibility);
	getContentPane().add(this.header, BorderLayout.NORTH);
	/* Plan Selection Panel */
	displayPlanSelectionPanel();
	/* Display */
	this.setTitle(this.WINDOW_TITLE);
	setWindowDimensions();
	setVisible(true);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Create the panel with the planning of the tour as a board of delivery men,
     * locations, hours and list of roads.
     */
    public void displayTourPlanningPanel() {
	this.rightPanel.setVisible(false);
	this.rightPanel = new JPanel();
	this.rightPanel.setPreferredSize(new Dimension(500, 900));
	this.rightPanel.setLayout(new BorderLayout());
	/* Create Content */
	JLabel planningText = new JLabel(this.TEXT_PLANNING_BOARD);
	setPlanningPanel(new PlanningView(this.controler, this));
	/* Panels */
	JPanel planning = new JPanel();
	planning.add(planningText, BorderLayout.NORTH);
	planning.add(getPlanningPanel(), BorderLayout.CENTER);
	/* Set content */
	displayDeliveryMenCountPanel();
	this.rightPanel.add(planning, BorderLayout.CENTER);
	this.rightPanel.setVisible(true);
	add(this.rightPanel, BorderLayout.EAST);
	redraw();
    }

    /**
     * Create the panel witch print the count of delivery men
     */
    public void displayDeliveryMenCountPanel() {
	this.deliveryMenCountPanel.removeAll();
	JLabel deliveryMenCount = new JLabel(this.TEXT_DELIVERY_LENGTH_COUNT);
	JLabel count = new JLabel("" + ModelInterface.getDeliveryMenCount());
	this.deliveryMenCountPanel.add(deliveryMenCount, BorderLayout.NORTH);
	this.deliveryMenCountPanel.add(count, BorderLayout.CENTER);
	this.rightPanel.add(this.deliveryMenCountPanel, BorderLayout.NORTH);
	redraw();
    }

    /**
     * Create the panel with the planning of the tour as a board of delivery men,
     * locations, hours and list of roads.
     */
    public void displayAddingDeliveryPanel() {
	getPlanningPanel().displayAddingDeliveryPanel();
    }

    /**
     * Defined the window and components size.
     */
    private void setWindowDimensions() {
	setExtendedState(Frame.MAXIMIZED_BOTH);
	pack();
	this.header.setLocation(0, 0);
    }

    /**
     * Create the panel for selecting a plan.
     */
    private void displayPlanSelectionPanel() {
	this.centerPanel = new JPanel();
	/* Create Content */
	JLabel selectionText = new JLabel(this.TEXT_PLAN_SELECTION);
	JButton selectionButton = createButton(this.BUTTON_BROWSE, ACTION_SELECTION_PLAN);
	this.centerPanel.add(selectionText);
	this.centerPanel.add(selectionButton);
	/* Set content */
	add(this.centerPanel, BorderLayout.CENTER);
    }

    /**
     * Create the panel for selecting a delivery request.
     */
    public void displayDeliveryRequestSelectionPanel() {
	this.rightPanel.removeAll();
	this.rightPanel.setPreferredSize(new Dimension(500, 900));
	/* Create Content */
	JLabel selectionText = new JLabel(this.TEXT_DELIVERY_SELECTION);
	JButton selectionButton = createButton(this.BUTTON_BROWSE, ACTION_SELECTION_DELIVERY);
	this.rightPanel.add(selectionText);
	this.rightPanel.add(selectionButton);
	/* Set content */
	add(this.rightPanel, BorderLayout.EAST);
	redraw();
    }

    /**
     * Create the panel with the city plan.
     */
    public void displayPlanView() {
	remove(this.centerPanel);
	this.centerPanel = new JPanel();
	this.centerPanel.setLayout(new GridBagLayout());
	/* Create Content */
	Plan plan = ModelInterface.getPlan();
	planPanel = new PlanView(this, plan);
	PlanListener planListener = new PlanListener(this.controler);
	planPanel.addMouseListener(planListener);
	planPanel.addKeyListener(this.keyListener);

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
	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	this.centerPanel.add(scroll, displayConstraint);
	/* Set content */
	add(this.centerPanel, BorderLayout.CENTER);
	redraw();
    }

    /**
     * Create the panel with the button to calculate the planning.
     */
    public void displayCalculateTourButtonPanel() {
	this.rightPanel.setVisible(false);
	this.rightPanel = new JPanel();
	this.rightPanel.setPreferredSize(new Dimension(500, 900));
	/* Create Content */
	JButton selectionButton = createButton(this.BUTTON_TOUR_CALCUL, ACTION_CALCULATE_TOUR);
	this.rightPanel.add(selectionButton, BorderLayout.EAST);
	displayDeliveryMenCountPanel();
	/* Set content */
	add(this.rightPanel, BorderLayout.EAST);
	this.rightPanel.setVisible(true);
	redraw();
    }

    /**
     * Remove the panel use for creating a new delivery point
     */
    public void hideAddingDeliveryPanel() {
	getPlanningPanel().hideAddingDeliveryPanel();
    }

    /**
     * Method used to set the lat and long fields of the adding form
     * 
     * 
     * @param lat the latitude value
     * @param lon the longitude value
     */
    public void setLatLonFieldsOfAddingPanel(double lat, double lon) {
	addingPanel.latitudeField.setText(" " + lat);
	addingPanel.longitudeField.setText(" " + lon);
    }

    /**
     * Convenience method to create a new button with a given text and action, and
     * to bind it to the action listener
     */
    private JButton createButton(String text, String action) {
	JButton button = new JButton(text);
	button.setActionCommand(action);
	button.addActionListener(this.buttonListener);
	return button;
    }

    /**
     * Update the graphics on the window, used when we don't add/remove components
     */
    public void redraw() {
	repaint();
	revalidate();
    }

    /**
     * Highlight a clicked intersection on the plan view.
     * 
     * @param intersection the intersection to highlight
     */
    public void highlightSelectedIntersection(Intersection intersection) {
	getPlanningPanel().selectRow(intersection);
	planPanel.setHighlightedIntersection(intersection);
	redraw();
    }

    /**
     * Highlight a clicked section on the plan view.
     * 
     * @param section the section to highlight
     */
    public void highlightSelectedSection(Section section) {
	planPanel.setHighlightedSection(section);
    }

    /**
     * Toggle the parameters button's visibility in the header
     */
    public void toggleDeliveryMenCountButtonVisiblity() {
	this.header.toggleDeliveryMenCountButtonVisibility();
    }

    /**
     * Toggle the return button's visibility in the header
     */
    public void toggleReturnButtonVisibility() {
	this.header.toggleReturnButtonVisibility();
    }

    /**
     * Display the sections of a delivery step
     * 
     * @param step is the step of which we want to display the section.
     */
    public void listSectionsOfStep(Step step) {
	this.southPanel.removeAll();
	this.southPanel.setVisible(false);
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
	this.southPanel.add(label);
	this.southPanel.setVisible(true);
	add(this.southPanel, BorderLayout.SOUTH);
	redraw();
    }

    /**
     * Hide the panel which print the section as the list of roads names.
     */
    public void hideSectionsList() {
	this.southPanel.setVisible(false);
    }

    /**
     * Force the event focus on the plan view.
     */
    public void forceFocusOnPlanView() {
	planPanel.requestFocus();
    }

    /**
     * Force the planning table to be redrawn.
     */
    public void redrawTable() {
	getPlanningPanel().redrawTable();
    }

    /**
     * Give a more esthetic look to the app when the window is initialized.
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

    /**
     * Getter of the planningPanel attribute.
     * 
     * @return PlanningView, the planning panel.
     */
    public static PlanningView getPlanningPanel() {
	return planningPanel;
    }

    /**
     * Setter of the planningPanel attribute.
     * 
     * @param planningPanel is the planning view to set.
     */
    public static void setPlanningPanel(PlanningView planningPanel) {
	Window.planningPanel = planningPanel;
    }

}
