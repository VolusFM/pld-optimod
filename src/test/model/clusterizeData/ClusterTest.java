package test.model.clusterizeData;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.model.Cluster;
import main.model.ModelInterface;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;

/**
 * Test of clusterization.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class ClusterTest {

    private TourCalculator calculator;
    private int clusterNb = 3;

    @Before
    public void setUp() throws Exception {
	XMLDeserializer.load(ModelInterface.getPlan());
	this.calculator = TourCalculator.getInstance();
	XMLDeserializer.load(ModelInterface.getPlan(), this.calculator);
	ModelInterface.createGraph();
    }

    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    public void solveBasicKmeans() {
	List<Cluster> clusters = ModelInterface.kMeans(this.clusterNb, this.calculator.getDeliveries(), 0.0001);
	assertEquals("clusters size doesnt match cluster number", clusters.size(), this.clusterNb);
	int deliveriesInClusters = 0;
	for (Cluster cluster : clusters) {
	    deliveriesInClusters += cluster.getDeliveries().size();
	}
	assertEquals("total number of deliveries in clusters doesnt match calculator 's deliveries size",
		deliveriesInClusters, this.calculator.getDeliveries().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void solveBasicKmeansError() {
	ModelInterface.kMeans(50, this.calculator.getDeliveries(), 0.0001);
    }

    @Test
    public void clusterizeDataTest() {
	for (int i = 0; i < 10; i++) {
	    List<Cluster> bestClusters = this.calculator.clusterizeData(this.clusterNb, 0.1);
	    if (this.clusterNb > this.calculator.getDeliveries().size()) {
		for (Cluster cluster : bestClusters) {
		    assert (cluster.getDeliveries()
			    .size() <= 1) : "Inbalanced clusters when more clusters then deliveries";
		}
	    }
	    for (Cluster cluster : bestClusters) {
		assert (cluster.getDeliveries().size() >= this.calculator.getDeliveries().size()
			/ this.clusterNb) : "Inbalanced clusters";
	    }
	    this.clusterNb++;
	}
    }

}
