package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;

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

	/* Components texts */
	private final String ADD_DELIVERY_POINT_BUTTON = "Ajouter un point de livraison";

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
		// /* Building board dimensions */
		// Collection<Delivery> deliveries = ModelInterface.getDeliveries();
		// Object[][] boardDatas = new Object[deliveries.size()][columnsNumber];
		// /* Building board datas */
		// Collection<Tour> tours = ModelInterface.getTourPlanning();
		// Iterator<Tour> itTours = tours.iterator();
		//
		// int currentLastEmptyLine = 0;
		//
		// while (itTours.hasNext()) {
		// Tour currentTour = itTours.next();
		// int deliveryMan = currentTour.getDeliveryManId();
		// Collection<Delivery> deliveryPoints =
		// currentTour.getDeliveryPoints();
		// Collection<Step> steps = currentTour.getSteps();
		// Iterator<Delivery> itDeliveries = deliveryPoints.iterator();
		// while (itDeliveries.hasNext()) {
		// Delivery currentDelivery = itDeliveries.next();
		// boardDatas[currentLastEmptyLine][0] = deliveryMan;
		// boardDatas[currentLastEmptyLine][1] = "(" +
		// currentDelivery.getAddress().getLat() + "; "
		// + currentDelivery.getAddress().getLon() + ")";
		// SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
		// Calendar hour = currentDelivery.getHour();
		// dateFormat.setTimeZone(hour.getTimeZone());
		// boardDatas[currentLastEmptyLine][2] =
		// dateFormat.format(hour.getTime());
		//
		// currentLastEmptyLine++;
		// }
		// }
		// /* Building board */
		// planning = new JTable(boardDatas, boardTitle);
		// planning.setAutoCreateRowSorter(true);

		planning = new PlanningTable();
		PlanningListener planningListener = new PlanningListener(planning, window);
		planning.getSelectionModel().addListSelectionListener(planningListener);

		/* Displaying */
		addDeliveryPoint = new JButton(ADD_DELIVERY_POINT_BUTTON);
		add(addDeliveryPoint, BorderLayout.NORTH);
		add(new JScrollPane(planning), BorderLayout.CENTER);
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