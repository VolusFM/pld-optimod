package test.model.cluster;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import main.model.Cluster;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;

/**
 * Test of the Cluster class.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class ClusterTest {

    private Cluster cluster;

    @Before
    public void setUp() {
	Pair<Double, Double> centroid = new Pair<Double, Double>(0.0, 0.0);
	this.cluster = new Cluster(centroid);
    }

    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    public void testAddDelivery() {
	Intersection intersection = new Intersection(4, 2, 2);
	Delivery toAdd = new Delivery(10, intersection);
	this.cluster.addDelivery(toAdd);
	assertEquals("Delivery wasnt correctly added", toAdd, this.cluster.getDeliveries().get(0));
    }

    @Test(expected = AssertionError.class)
    public void testAddEmptyDelivery() {
	this.cluster.addDelivery(null);
    }

    @Test
    public void testPopDelivery() {
	Intersection intersection = new Intersection(4, 2, 2);
	Delivery toPop = new Delivery(10, intersection);
	this.cluster.addDelivery(toPop);
	assertEquals("Poped delivery isnt expected delivery", toPop, this.cluster.popDelivery(0));
	assert (this.cluster.getDeliveries().size() == 0) : "cluster isnt empty after delivery pop ";
    }

    @Test(expected = AssertionError.class)
    public void testPopDeliveryEmptyCluster() {
	this.cluster.popDelivery(0);
    }

    @Test
    public void testSortDeliveries() {
	this.cluster.sortDeliveriesByEuclidianDistanceToCentroid();
	Intersection intersection1 = new Intersection(4, 2, 2);
	Intersection intersection2 = new Intersection(5, 2, 0);
	Intersection intersection3 = new Intersection(6, 3, 3);
	Delivery delivery1 = new Delivery(10, intersection1);
	Delivery delivery2 = new Delivery(10, intersection2);
	Delivery delivery3 = new Delivery(10, intersection3);
	this.cluster.addDelivery(delivery1);
	this.cluster.addDelivery(delivery2);
	this.cluster.addDelivery(delivery3);
	this.cluster.sortDeliveriesByEuclidianDistanceToCentroid();
	List<Delivery> sortedDeliveries = new ArrayList<Delivery>();
	sortedDeliveries.add(delivery3);
	sortedDeliveries.add(delivery1);
	sortedDeliveries.add(delivery2);
	assertEquals("Sort algorithm failed", this.cluster.getDeliveries(), sortedDeliveries);
    }

    @Test
    public void testCalculateCoefficient() {
	assert (this.cluster.evaluateClusteringQuality() == 0) : "Empty cluster doesnt have coef = 0";
	Intersection intersection1 = new Intersection(4, 2, 0);
	Intersection intersection2 = new Intersection(5, 2, 0);
	Intersection intersection3 = new Intersection(6, 0, 3);
	Delivery delivery1 = new Delivery(10, intersection1);
	Delivery delivery2 = new Delivery(10, intersection2);
	Delivery delivery3 = new Delivery(10, intersection3);
	this.cluster.addDelivery(delivery1);
	this.cluster.addDelivery(delivery2);
	this.cluster.addDelivery(delivery3);
	assert (this.cluster.evaluateClusteringQuality() == 7) : "cluster coefficient isnt egal to expected result";
    }

    @Test
    public void testReinitializeCLuster() {
	Intersection intersection1 = new Intersection(4, 2, 0);
	Intersection intersection2 = new Intersection(5, 2, 0);
	Intersection intersection3 = new Intersection(6, 0, 3);
	Delivery delivery1 = new Delivery(10, intersection1);
	Delivery delivery2 = new Delivery(10, intersection2);
	Delivery delivery3 = new Delivery(10, intersection3);
	this.cluster.addDelivery(delivery1);
	this.cluster.addDelivery(delivery2);
	this.cluster.addDelivery(delivery3);
	this.cluster.reinitializeClusters();
	assert (this.cluster.getDeliveries().size() == 0) : "Empty cluster isnt empty";
    }
}
