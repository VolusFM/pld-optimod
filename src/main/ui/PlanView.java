package main.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;
import main.model.Tour;

/**
 * Panel of the graphical view of the application, with the drawn plan,
 * deliveries and tours.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class PlanView extends JPanel {

    /* Id */
    private static final long serialVersionUID = 1L;

    /* Attributes */
    private Plan plan;

    /* Scale and normalization parameters */
    private double minLat;
    private double maxLat;
    private double minLong;
    private double maxLong;

    /* Highlighted elements */
    private Intersection highlightedIntersection;
    private Section highlightedSection;

    /* Graphic attributes */
    private double xConstant = 1.4;
    private double yConstant = 0.7;

    /* Tour colors */
    private final Color[] colors = { Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN, Color.LIGHT_GRAY,
	    Color.PINK };

    /**
     * Create the graphical view for drawing the charged plan p in the specified
     * window w.
     * 
     * @param w is the application's graphical window.
     * @param p is the plan to print.
     */
    public PlanView(Window w, Plan p) {
	super();
	/* Initialize */
	this.plan = p;
	this.findExtremes();
	/* Display */
	setBackground(Color.WHITE);
	setToolTipText("");
	/* Allow the panel to emit key events */
	setFocusable(true);
    }

    /**
     * Redraw a component.
     * 
     * @param g is the graphical component to redraw.
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D graphics2d = (Graphics2D) g.create();
	/* Draw the sections */
	graphics2d.setColor(Color.DARK_GRAY);
	// this.graphics = g;
	Collection<Intersection> intersections = this.plan.getGraph().values();
	Iterator<Intersection> itIntersection = intersections.iterator();
	while (itIntersection.hasNext()) {
	    List<Section> sections = itIntersection.next().getOutcomingSections();
	    Iterator<Section> itSection = sections.iterator();
	    while (itSection.hasNext()) {
		paintSection(graphics2d, itSection.next());
	    }
	}

	/* Depot displaying */
	graphics2d.setColor(Color.RED);
	Delivery depot = ModelInterface.getDepot();
	if (depot != null) {
	    paintDelivery(graphics2d, depot);
	}
	/* Deliveries Displaying */
	graphics2d.setColor(Color.BLUE);
	Collection<Delivery> deliveries = ModelInterface.getDeliveries();
	Iterator<Delivery> itDeliveries = deliveries.iterator();
	while (itDeliveries.hasNext()) {
	    paintDelivery(graphics2d, itDeliveries.next());
	}
	/* Tour displaying */
	graphics2d.setColor(Color.GREEN);
	graphics2d.setStroke(new BasicStroke(2));
	Collection<Tour> tours = ModelInterface.getTourPlanning();
	int i = 0;
	for (Tour tour : tours) {
	    graphics2d.setColor(this.colors[i++ % this.colors.length]);
	    for (Step step : tour.getSteps()) {
		for (Section section : step.getSections()) {
		    paintSection(graphics2d, section);
		}
	    }
	}
	/* Highlighted intersection */
	if (this.highlightedIntersection != null) {
	    graphics2d.setColor(Color.MAGENTA);
	    Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
		    new float[] { 2.0f, 0.5f }, 0.0f);
	    graphics2d.setStroke(stroke);
	    paintIntersection(graphics2d, this.highlightedIntersection);
	}
	/* Highlighted section */
	if (this.highlightedSection != null) {
	    graphics2d.setColor(Color.ORANGE);
	    Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
		    new float[] { 2.0f, 0.5f }, 0.0f);
	    graphics2d.setStroke(stroke);
	    paintSection(graphics2d, this.highlightedSection);
	}
    }

    /**
     * Draw a plan section, i.e. a line between the two intersections of the
     * section.
     * 
     * @param g       is the graphics component onto which to paint.
     * @param section the section to print.
     */
    public void paintSection(Graphics g, Section section) {
	/* Values from the plan */
	GeographicCoordinate startGeographicCoordinate = new GeographicCoordinate(section.getStart().getLat(),
		section.getStart().getLon());
	GeographicCoordinate endGeographicCoordinate = new GeographicCoordinate(section.getEnd().getLat(),
		section.getEnd().getLon());
	/* Conversion */
	ScreenCoordinate startScreenCoordinate = convertToScreenCoordinate(startGeographicCoordinate);
	ScreenCoordinate endScreenCoordinate = convertToScreenCoordinate(endGeographicCoordinate);
	/* Display */
	g.drawLine(startScreenCoordinate.getX(), startScreenCoordinate.getY(), endScreenCoordinate.getX(),
		endScreenCoordinate.getY());
    }

    /**
     * Draw a circle at the specified point of a delivery.
     * 
     * @param g        is the graphics component onto which to paint.
     * @param delivery is the delivery to print.
     */
    public void paintDelivery(Graphics g, Delivery delivery) {
	/* Values from delivery */
	GeographicCoordinate geographicCoordinate = new GeographicCoordinate(delivery.getAddress().getLat(),
		delivery.getAddress().getLon());
	/* Conversion */
	ScreenCoordinate screenCoordinate = convertToScreenCoordinate(geographicCoordinate);
	/* Display */
	g.fillOval(screenCoordinate.getX() - 5, screenCoordinate.getY() - 5, 10, 10);
    }

    /**
     * Draw an intersection on the plan view.
     * 
     * @param g            is the graphics component onto which to paint.
     * @param intersection is the intersection to print.
     */
    public void paintIntersection(Graphics g, Intersection intersection) {
	/* Values from delivery */
	GeographicCoordinate geographicCoordinate = new GeographicCoordinate(intersection.getLat(),
		intersection.getLon());
	/* Conversion */
	ScreenCoordinate screenCoordinate = convertToScreenCoordinate(geographicCoordinate);
	/* Display */
	g.drawOval(screenCoordinate.getX() - 8, screenCoordinate.getY() - 8, 16, 16);
    }

    /**
     * Extract max and min of latitudes and longitudes in the current plan. (Used to
     * normalize the values in order to print the. sections).
     */
    private void findExtremes() {
	/* Initialization */
	Collection<Intersection> intersections = this.plan.getGraph().values();
	Iterator<Intersection> it = intersections.iterator();
	Intersection first = it.next();
	this.minLat = first.getLat();
	this.maxLat = first.getLat();
	this.minLong = first.getLon();
	this.maxLong = first.getLon();
	Intersection current;
	/* Calculate */
	while (it.hasNext()) {
	    current = it.next();
	    if (current.getLat() < this.minLat) {
		this.minLat = current.getLat();
	    }
	    if (current.getLat() > this.maxLat) {
		this.maxLat = current.getLat();
	    }
	    if (current.getLon() < this.minLong) {
		this.minLong = current.getLon();
	    }
	    if (current.getLon() > this.maxLong) {
		this.maxLong = current.getLon();
	    }
	}

    }

    /**
     * Convert geographical coordinates into screen ones.
     * 
     * @param geographicCoordinate are the geographical coordinates to convert.
     * @return ScreenCoordinate, the coordinates as screen ones.
     */
    public ScreenCoordinate convertToScreenCoordinate(GeographicCoordinate geographicCoordinate) {
	double lat = geographicCoordinate.getLatitude();
	double lon = geographicCoordinate.getLongitude();
	/* Normalization */
	lat = (lat - this.minLat) / (this.maxLat - this.minLat);
	lon = (lon - this.minLong) / (this.maxLong - this.minLong);
	/* Scaling */
	int x = (int) Math.round(this.xConstant * lat * getHeight());
	int y = (int) Math.round(this.yConstant * lon * getWidth());

	return new ScreenCoordinate(x, y);
    }

    /**
     * Convert screen coordinates into geographical ones.
     * 
     * @param screenCoordinate are the screen coordinates to convert.
     * @return GeographicCoordinate, the coordinates as geographical ones.
     */
    public GeographicCoordinate convertToGeographicCoordinate(ScreenCoordinate screenCoordinate) {
	int x = screenCoordinate.getX();
	int y = screenCoordinate.getY();
	/* Normalization */
	double latSpan = (this.maxLat - this.minLat) / (this.xConstant * getHeight());
	double longSpan = (this.maxLong - this.minLong) / (this.yConstant * getWidth());

	double lat = this.minLat + x * latSpan;
	double lon = this.minLong + y * longSpan;

	return new GeographicCoordinate(lat, lon);
    }

    /**
     * Highlight the intersection (left-clicked).
     * 
     * @param intersection is the intersection to highlight.
     */
    public void setHighlightedIntersection(Intersection intersection) {
	this.highlightedIntersection = intersection;
    }

    /**
     * Highlight a section.
     * 
     * @param section is the section to highlight.
     */
    public void setHighlightedSection(Section section) {
	this.highlightedSection = section;
	setToolTipText(this.highlightedSection.getStreetName());
	ToolTipManager manager = ToolTipManager.sharedInstance();
	manager.setInitialDelay(0);
	manager.setReshowDelay(0);
	manager.setDismissDelay(1000);

	Point mousePos = MouseInfo.getPointerInfo().getLocation();
	int x = mousePos.x - getLocationOnScreen().x;
	int y = mousePos.y - getLocationOnScreen().y;
	MouseEvent phantom = new MouseEvent(this, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0,
		false);
	ToolTipManager.sharedInstance().mouseMoved(phantom);
    }
}
