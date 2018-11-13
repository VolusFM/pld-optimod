package test.model.tour;

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
		Intersection depotIntersection = new Intersection(1,0,0);
		depot = new Delivery(10,depotIntersection);
		tour = new Tour(depot,steps,deliveries,1);
	}
	
	@Test
	public void testAddDeliveryAtIndex() {
		Intersection intersection = new Intersection(2,2,2);
		Delivery toAdd = new Delivery(10, intersection);
		tour.addDeliveryAtIndex(toAdd,0);
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
}
