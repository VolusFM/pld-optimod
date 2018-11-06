package main.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;
import main.model.Tour;

public class PlanView extends JPanel {

	/* Attributes */
	private Plan plan;

	/* Scale and normalization parameters */
	private int scale;
	private double minLat;
	private double maxLat;
	private double minLong;
	private double maxLong;

	/* Highlighted elements */
	private Intersection highlightedIntersection;

	/* Graphic components */
	// private Graphics graphics;

	/**
	 * Create the graphical view for drawing the charged plan with the scale s
	 * in the specified window w.
	 * 
	 * @param s
	 *            the scale
	 * @param w
	 *            the window
	 * @param p
	 *            the plan to print
	 */
	// FIXME : constructor signature may change
	public PlanView(int s, Window w, Plan p) {
		super();
		/* Initialize */
		this.scale = s;
		this.plan = p;
		this.findExtremes();
		// setSize(400, 400);
		/* Display */
		setBackground(Color.WHITE);
		// TODO tooltip pour les rues
		// setToolTipText("TOOLTIP");
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
		Graphics2D graphics2d = (Graphics2D) g.create();
		/* Draw the sections */
		graphics2d.setColor(Color.DARK_GRAY);
		// this.graphics = g;
		Collection<Intersection> intersections = plan.getGraph().values();
		Iterator<Intersection> itIntersection = intersections.iterator();
		while (itIntersection.hasNext()) {
			List<Section> sections = itIntersection.next().getOutcomingSections();
			Iterator<Section> itSection = sections.iterator();
			while (itSection.hasNext()) {
				printSection(graphics2d, itSection.next());
			}
		}
		/* Depot displaying */
		graphics2d.setColor(Color.RED);
		Delivery depot = ModelInterface.getDepot();
		if (depot != null) {
			printDelivery(graphics2d, depot);
		}
		/* Deliveries Displaying */
		graphics2d.setColor(Color.BLUE);
		Collection<Delivery> deliveries = ModelInterface.getDeliveries();
		Iterator<Delivery> itDeliveries = deliveries.iterator();
		while (itDeliveries.hasNext()) {
			printDelivery(graphics2d, itDeliveries.next());
		}
		/* Tour displaying */
		graphics2d.setColor(Color.GREEN);
		graphics2d.setStroke(new BasicStroke(2));
		Collection<Tour> tours = ModelInterface.getTourPlanning();
		for (Tour tour : tours) {
			for (Step step : tour.getSteps()) {
				for (Section section : step.getSections()) {
					printSection(graphics2d, section);
				}
			}
		}
		/* Highlighted intersection */
		if (highlightedIntersection != null) {
			graphics2d.setColor(Color.MAGENTA);
			Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] { 2.0f, 0.5f }, 0.0f);
			graphics2d.setStroke(stroke);
			printIntersection(graphics2d, highlightedIntersection);
		}
	}

	/*
	 * @Override public String getToolTipText(MouseEvent event) {
	 * System.out.println(event); return "ABC"; }
	 */

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
		GeographicCoordinate startGeographicCoordinate = new GeographicCoordinate(section.getStart().getLat(), section.getStart().getLon());
		GeographicCoordinate endGeographicCoordinate = new GeographicCoordinate(section.getEnd().getLat(), section.getEnd().getLon());
		/* Conversion */
		ScreenCoordinate startScreenCoordinate = convertToScreenCoordinate(startGeographicCoordinate);
		ScreenCoordinate endScreenCoordinate = convertToScreenCoordinate(endGeographicCoordinate);
		/* Display */
		g.drawLine(startScreenCoordinate.getX(), startScreenCoordinate.getY(), endScreenCoordinate.getX(), endScreenCoordinate.getY());
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
		/* Values from delivery */
		GeographicCoordinate geographicCoordinate = new GeographicCoordinate(delivery.getAddress().getLat(), delivery.getAddress().getLon());
		/* Conversion */
		ScreenCoordinate screenCoordinate = convertToScreenCoordinate(geographicCoordinate);
		/* Display */
		g.fillOval(screenCoordinate.getX() - 5, screenCoordinate.getY() - 5, 10, 10);
	}

	public void printIntersection(Graphics g, Intersection intersection) {
		/* Values from delivery */
		GeographicCoordinate geographicCoordinate = new GeographicCoordinate(intersection.getLat(), intersection.getLon());
		/* Conversion */
		ScreenCoordinate screenCoordinate = convertToScreenCoordinate(geographicCoordinate);
		/* Display */
		g.drawOval(screenCoordinate.getX() - 8, screenCoordinate.getY() - 8, 16, 16);
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

	public ScreenCoordinate convertToScreenCoordinate(GeographicCoordinate geographicCoordinate) {
		double lat = geographicCoordinate.getLatitude();
		double lon = geographicCoordinate.getLongitude();
		/* Normalization */
		lat = (lat - minLat) / (maxLat - minLat);
		lon = (lon - minLong) / (maxLong - minLong);
		/* Scaling */
		int x = (int) Math.round(lat * getHeight());
		int y = (int) Math.round(lon * getWidth());

		return new ScreenCoordinate(x, y);
	}

	public GeographicCoordinate convertToGeographicCoordinate(ScreenCoordinate screenCoordinate) {
		int x = screenCoordinate.getX();
		int y = screenCoordinate.getY();
		/* Normalization */
		double latSpan = (maxLat - minLat) / getHeight();
		double longSpan = (maxLong - minLong) / getWidth();

		double lat = minLat + x * latSpan;
		double lon = minLong + y * longSpan;

		return new GeographicCoordinate(lat, lon);
	}

	/**
	 * Wrapper class representing a tuple latitude/longitude, in the geographic
	 * coordinate system
	 */
	public static class GeographicCoordinate {
		double latitude;
		double longitude;

		public GeographicCoordinate(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	}

	/**
	 * Wrapper class representing a tuple x/y, in the screen coordinate system
	 * Screen coordinate are "normalized" (taking into account boundaries of map
	 * view, etc.)
	 */
	/*
	 * TODO : FlyWeight for this class ?
	 */
	public static class ScreenCoordinate {
		int x;
		int y;

		public ScreenCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

	public void setHighlightedIntersection(Intersection intersection) {
		this.highlightedIntersection = intersection;
	}
}
