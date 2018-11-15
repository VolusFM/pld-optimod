package test.model.tsp;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Test;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;
import main.model.Tour;
import main.model.TourCalculator;
import main.model.TourFactory;

public class TSPTest {

    @After
    public void setUp() {
	ModelInterface.emptyTourFactory();
	ModelInterface.emptyLoadedDeliveries();
    }

    
    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    public void solveTSP() {
	/* Creation intersections */
	Intersection i1 = new Intersection(1, 5, 5);
	Intersection i2 = new Intersection(2, 8, 8);
	Intersection i3 = new Intersection(3, 10, 10);
	/* Create sections */
	Section s1 = new Section(i1, i2, 6, "");
	Section s2 = new Section(i1, i3, 8, "");
	Section s6 = new Section(i2, i1, 6, "");
	Section s7 = new Section(i2, i3, 4, "");
	Section s8 = new Section(i3, i1, 6, "");
	Section s9 = new Section(i3, i2, 10, "");
	/* Add sections to intersection */
	i1.addOutcomingSection(s1);
	i1.addOutcomingSection(s2);
	i2.addOutcomingSection(s6);
	i2.addOutcomingSection(s7);
	i3.addOutcomingSection(s8);
	i3.addOutcomingSection(s9);
	/* Initialisation plan avec intersection */
	ModelInterface.setMap(new Plan());
	ModelInterface.addIntersection(i1);
	ModelInterface.addIntersection(i2);
	ModelInterface.addIntersection(i3);
	/* create delivery */
	Delivery d1 = new Delivery(0, i1);
	Calendar time = new GregorianCalendar();
	time.set(Calendar.HOUR_OF_DAY, 8);
	time.set(Calendar.MINUTE, 0);
	time.set(Calendar.SECOND, 0);
	d1.setHour(time);
	Delivery d2 = new Delivery(5 * 60, i2);
	Delivery d3 = new Delivery(15 * 60, i3);
	ModelInterface.setDepot(d1);
	ModelInterface.addDeliveryToTourCalculator(d2);
	ModelInterface.addDeliveryToTourCalculator(d3);
	ModelInterface.setDeliveryMenCount(1);
	TourCalculator.getInstance().calculateTours();
	Tour result = ModelInterface.getTourPlanning().get(0);
	Step firstStep = result.getSteps().get(0);
	Step secondStep = result.getSteps().get(1);
	Step lastStep = result.getSteps().get(2);

	assertEquals(firstStep.getStartDelivery(), d1);
	assertEquals(firstStep.getEndDelivery(), d2);
	assertEquals(secondStep.getStartDelivery(), d2);
	assertEquals(secondStep.getEndDelivery(), d3);
	assertEquals(lastStep.getStartDelivery(), d3);
	assertEquals(lastStep.getEndDelivery(), d1);
    }

}
