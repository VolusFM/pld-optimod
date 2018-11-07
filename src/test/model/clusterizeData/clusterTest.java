package test.model.clusterizeData;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.model.Cluster;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.TourCalculator;
import main.xml.XMLDeserializer;

public class clusterTest {

	private TourCalculator calculator;
	private int clusterNb = 3;

	@Before
	public void setUp() throws Exception {
		XMLDeserializer.load(ModelInterface.getPlan());
		calculator = TourCalculator.getInstance();
		XMLDeserializer.load(ModelInterface.getPlan(), calculator);
		calculator.createGraph();
		
		
	}

	@Test
	public void solveBasicKmeans(){
		List<Cluster> clusters = calculator.kMeans(clusterNb, calculator.getDeliveries(), 0.0001);
		if(!(clusters.size()==clusterNb)){
			/*if this happened, then the algorithm as been compromised. That will not happen*/
			throw new AssertionError("Kmean didn't return correct number of clusters");
		}
		/*Check if that the sum of deliveries in cluster is egal to number of deliveries*/
		int deliveriesInClusters = 0;
		for(Cluster cluster: clusters){
			deliveriesInClusters += cluster.getDeliveries().size();
		}
		if(deliveriesInClusters != calculator.getDeliveries().size()){
			throw new AssertionError("Incorrect number of deliveries in clusters");
		}
//		for(Cluster cluster : clusters){
//			System.out.println(cluster.toString());
//		}
	}

	@Test
	public void clusterizeDataTest() {
		System.out.println("Start");
		long startTime = System.currentTimeMillis();
		List<Cluster> bestClusters = calculator.clusterizeData(clusterNb, 0.1);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("execution time after initialisation : "+elapsedTime);
		for(Cluster cluster : bestClusters){
			System.out.println(cluster.toString());
		}
	}

}
