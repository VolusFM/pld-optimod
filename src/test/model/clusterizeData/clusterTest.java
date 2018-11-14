package test.model.clusterizeData;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.model.Cluster;
import main.model.ModelInterface;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;

public class clusterTest {

    private TourCalculator calculator;
    private int clusterNb = 5;

    @Before
    public void setUp() throws Exception {
	XMLDeserializer.load(ModelInterface.getPlan());
	calculator = TourCalculator.getInstance();
	XMLDeserializer.load(ModelInterface.getPlan(), calculator);
	ModelInterface.createGraph();
    }

    @Test
    public void solveBasicKmeans() {
	List<Cluster> clusters = ModelInterface.kMeans(clusterNb, calculator.getDeliveries(), 0.0001);
	assertEquals("clusters size doesnt match cluster number", clusters.size(), clusterNb);
	int deliveriesInClusters = 0;
	for (Cluster cluster : clusters) {
	    deliveriesInClusters += cluster.getDeliveries().size();
	}
	assertEquals("total number of deliveries in clusters doesnt match calculator 's deliveries size",
		deliveriesInClusters, calculator.getDeliveries().size());
    }

    @Test(expected = AssertionError.class)
    public void solveBasicKmeansError() {
	ModelInterface.kMeans(50, calculator.getDeliveries(), 0.0001);
    }

    @Test
    public void clusterizeDataTest() {
	for (int i = 0; i < 10; i++) {
	    List<Cluster> bestClusters = calculator.clusterizeData(clusterNb, 0.1);
	    if (clusterNb > calculator.getDeliveries().size()) {
		for (Cluster cluster : bestClusters) {
		    assert (cluster.getDeliveries()
			    .size() <= 1) : "Inbalanced clusters when more clusters then deliveries";
		}
	    }
	    for (Cluster cluster : bestClusters) {
		assert (cluster.getDeliveries()
			.size() >= (int) (calculator.getDeliveries().size() / clusterNb)) : "Inbalanced clusters";
	    }
	    clusterNb++;
	}
    }

}
