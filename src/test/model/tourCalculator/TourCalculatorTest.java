package test.model.tourCalculator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Tour;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;

/**
 * Test of TourCalculator class.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class TourCalculatorTest {
    private Plan p;
    private List<Delivery> deliveries;
    private Delivery depot;
    TourCalculator calculator;

    @Before
    public void setUp() throws Exception {
	this.p = new Plan();
	XMLDeserializer.load(ModelInterface.getPlan());
	this.calculator = TourCalculator.getInstance();
	XMLDeserializer.load(ModelInterface.getPlan(), this.calculator);
	this.calculator = TourCalculator.getInstance();
    }

    @After
    public void tearDown() throws Exception {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    // Works with "petitPlan.xml" and "dl-petit-3.xml"
    public void testFindCorrespondingDelivery() {
	Intersection intersection = new Intersection(26317242, 45.75426, 4.8732033);
	Delivery expectedDelivery = new Delivery(60, intersection);
	Delivery foundDelivery = this.calculator.findCorrespondingDelivery(intersection);
	assertEquals("didnt found expected delivery", foundDelivery, expectedDelivery);
    }

    @Test
    public void testFindDepot() {
	Delivery depot = this.calculator.getDepot();
	Intersection depotIntersection = depot.getAddress();
	assertEquals("Couldn't find depot", depot, this.calculator.findCorrespondingDelivery(depotIntersection));
    }

    @Test
    public void testFindUnknownDelivery() {
	Intersection randomIntersection = new Intersection(1, 2, 3);
	assertEquals("Managed to find an unknown delivery", this.calculator.findCorrespondingDelivery(randomIntersection),
		null);
    }

    @Test
    public void testFindNullDelivery() {
	assertEquals("Managed to find a null delivery", this.calculator.findCorrespondingDelivery(null), null);
    }

    @Test
    public void testCalculateTours() {
	this.calculator.calculateTours();
	assert (this.calculator.getDeliveryMenCount() == ModelInterface.getTourPlanning()
		.size()) : "Calculated wrong tour number";
	List<Tour> planning = ModelInterface.getTourPlanning();
	int totalDelivery = 0;
	for (Tour tour : planning) {
	    totalDelivery += tour.getDeliveryPoints().size();
	}
	assert (totalDelivery == this.calculator.getDeliveries().size()
		+ this.calculator.getDeliveryMenCount()) : "deliveries in planning dont match those in calculator";
    }
}
