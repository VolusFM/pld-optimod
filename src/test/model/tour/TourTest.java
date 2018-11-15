package test.model.tour;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.model.Tour;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Step;

/**
 * Test of Tour class.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class TourTest {
    private Tour tour;
    private Delivery depot;
    private List<Step> steps;
    private List<Delivery> deliveries;

    @Before
    public void setUp() {
	this.steps = new ArrayList<Step>();
	this.deliveries = new ArrayList<Delivery>();
	Intersection depotIntersection = new Intersection(7, 0, 0);
	this.depot = new Delivery(10, depotIntersection);
	this.tour = new Tour(this.depot, this.steps, this.deliveries, 2);
    }

    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    public void testAddDeliveryAtIndex() {
	Intersection intersection = new Intersection(2, 2, 2);
	Delivery toAdd = new Delivery(10, intersection);
	this.tour.addDeliveryAtIndex(toAdd, 0);
	assertEquals("Delivery wasnt correctly added", toAdd, this.tour.getDeliveryPoints().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyDelivery() {
	this.tour.addDeliveryAtIndex(null, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddDeliveryWrongIndex() {
	Intersection intersection = new Intersection(2, 2, 2);
	Delivery toAdd = new Delivery(10, intersection);
	this.tour.addDeliveryAtIndex(toAdd, 15);
    }

    @Test
    public void testRemoveDelivery() {
	Intersection intersection = new Intersection(5, 2, 2);
	Delivery toAdd = new Delivery(10, intersection);
	this.tour.addDeliveryAtIndex(toAdd, 0);
	this.tour.removeDelivery(toAdd);
	assert (this.tour.getDeliveryPoints().size() == 1);
    }

    @Test
    public void testRemoveEmptyDelivery() {
	this.tour.removeDelivery(null);
    }

    @Test
    public void testRemoveUnknownDelivery() {
	Intersection intersection = new Intersection(5, 2, 2);
	Delivery toRemove = new Delivery(10, intersection);
	this.tour.removeDelivery(toRemove);
    }
}
