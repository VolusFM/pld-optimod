package test.model.tour;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.model.Tour;
import main.model.Delivery;
import main.model.Intersection;
import main.model.Step;

public class TourTest {
	private Tour tour;
	private Delivery depot;
	private List<Step> steps;
	private List<Delivery> deliveries;
	
	@Before
	public void setUp() {
		steps = new ArrayList<Step>();
		deliveries = new ArrayList<Delivery>();
		Intersection depotIntersection = new Intersection(7,0,0);
		depot = new Delivery(10,depotIntersection);
		tour = new Tour(depot,steps,deliveries,2);
	}
	
	@Test
	public void testAddDeliveryAtIndex() {
		Intersection intersection = new Intersection(2,2,2);
		Delivery toAdd = new Delivery(10, intersection);
		tour.addDeliveryAtIndex(toAdd,0);
		assertEquals("Delivery wasnt correctly added", toAdd, tour.getDeliveryPoints().get(0));
	}
	
	@Test(expected =  AssertionError.class)
	public void testAddEmptyDelivery() {
		tour.addDeliveryAtIndex(null, 0);
	}
	
	@Test(expected =  AssertionError.class)
	public void testAddDeliveryWrongIndex() {
		Intersection intersection = new Intersection(2,2,2);
		Delivery toAdd = new Delivery(10, intersection);
		tour.addDeliveryAtIndex(toAdd, 15);
	}
	
	@Test
	public void testRemoveDelivery() {
		Intersection intersection = new Intersection(5,2,2);
		Delivery toAdd = new Delivery(10, intersection);
		tour.addDeliveryAtIndex(toAdd,0);
		tour.removeDelivery(toAdd);
		assert(tour.getDeliveryPoints().size() == 1);
	}
	
	@Test
	public void testRemoveEmptyDelivery() {
		tour.removeDelivery(null);
	}
	
	@Test
	public void testRemoveUnknownDelivery() {
		Intersection intersection = new Intersection(5,2,2);
		Delivery toRemove = new Delivery(10, intersection);
		tour.removeDelivery(toRemove);
	}
}
