package main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import javafx.util.Pair;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;

public class PlanView extends JPanel {

	/* Attributes */
	private Plan plan;

	/* Scale and normalization parameters */
	private int scale;
	private double minLat;
	private double maxLat;
	private double minLong;
	private double maxLong;

	/* Graphic components */
	// private Graphics graphics;

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
	public PlanView(int s, Window w, Plan p) {
		super();
		/* Initialize */
		this.scale = s;
		this.plan = p;
		this.findExtremes();
		setLayout(null);
		setSize(400, 400);
		/* Display */
		setBackground(Color.WHITE);
		w.getContentPane().add(this);
	}
	// TODO : mettre l'observer sur le plan.

	/**
	 * Function called any time the view must be redraw.
	 * 
	 * @param g
	 *            the graphics component
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/* Draw the sections */
		g.setColor(Color.DARK_GRAY);
		// this.graphics = g;
		Collection<Intersection> intersections = plan.getGraph().values();
		Iterator<Intersection> itIntersection = intersections.iterator();
		while (itIntersection.hasNext()) {
			List<Section> sections = itIntersection.next().getOutcomingSections();
			Iterator<Section> itSection = sections.iterator();
			while (itSection.hasNext()) {
				printSection(g, itSection.next());
			}
		}
		/* Deliveries Displaying */
		g.setColor(Color.BLUE);
		Collection<Delivery> deliveries = ModelInterface.getDeliveries();
		Iterator<Delivery> itDeliveries = deliveries.iterator();
		while (itDeliveries.hasNext()) {
			printDelivery(g, itDeliveries.next());
		}
	}

	/**
	 * Method called any time we need to draw a plan section. Draw a line
	 * between the two intersections of the section.
	 * 
	 * @param g
	 *            the graphics component
	 * @param section
	 *            the section to print
	 */
	public void printSection(Graphics g, Section section) {
		/* Values from the plan */
		double startLat = section.getStart().getLat();
		double startLong = section.getStart().getLon();
		double endLat = section.getEnd().getLat();
		double endLong = section.getEnd().getLon();
		/* Normalization */
		startLat = (startLat - minLat) / (maxLat - minLat);
		startLong = (startLong - minLong) / (maxLong - minLong);
		endLat = (endLat - minLat) / (maxLat - minLat);
		endLong = (endLong - minLong) / (maxLong - minLong);
		/* Scaling */
		int startX = (int) Math.round(startLat * getHeight());
		int startY = (int) Math.round(startLong * getWidth());
		int endX = (int) Math.round(endLat * getHeight());
		int endY = (int) Math.round(endLong * getWidth());
		/* Display */
		g.drawLine(startX, startY, endX, endY);
	}

	/**
	 * Method called any time we need to draw a delivery point. Draw a circle at
	 * the specified point.
	 * 
	 * @param g
	 *            the graphics component
	 * @param delivery
	 *            the delivery to print
	 */
	public void printDelivery(Graphics g, Delivery delivery) {
		/* Values from the delivery */
		double lat = delivery.getAddress().getLat();
		double lon = delivery.getAddress().getLon();
		/* Normalization */
		lat = (lat - minLat) / (maxLat - minLat);
		lon = (lon - minLong) / (maxLong - minLong);
		/* Scaling */
		int x = (int) Math.round(lat * getHeight());
		int y = (int) Math.round(lon * getWidth());
		/* Display */
		g.fillOval(x, y, 10, 10);
	}

	/**
	 * Method used to extract max and min of latitudes and longitudes in the
	 * current plan. (Used to normalize the values in order to print the
	 * sections).
	 */
	private void findExtremes() {
		/* Initialization */
		Collection<Intersection> intersections = plan.getGraph().values();
		Iterator<Intersection> it = intersections.iterator();
		Intersection first = it.next();
		minLat = first.getLat();
		maxLat = first.getLat();
		minLong = first.getLon();
		maxLong = first.getLon();
		Intersection current;
		/* Calculate */
		while (it.hasNext()) {
			current = it.next();
			if (current.getLat() < minLat) {
				minLat = current.getLat();
			}
			if (current.getLat() > maxLat) {
				maxLat = current.getLat();
			}
			if (current.getLon() < minLong) {
				minLong = current.getLon();
			}
			if (current.getLon() > maxLong) {
				maxLong = current.getLon();
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		// XXX : not so clean... figure out a better (dynamic) way
		return new Dimension(800, 800);
	}

	public Pair<Integer, Integer> convertToScreenCoordinate(Pair<Double, Double> latLng) {
		return null; // TODO
	}

	public Pair<Double, Double> convertToLatLong(Pair<Integer, Integer> screenCOords) {
		return null; // TODO
	}
}
