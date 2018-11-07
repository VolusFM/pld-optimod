package main.model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class Cluster {
	private List<Delivery> deliveries;
	private Pair<Double,Double> centroid;
	
	/**
	 * Create a cluster.
	 * @param deliveries
	 */
	public Cluster(Pair<Double,Double> centroid){
		this.centroid = centroid;
		deliveries = new ArrayList<Delivery>();
	}
	
	/**
	 * add delivery
	 * @param delivery
	 */
	public void addDelivery(Delivery delivery){
		this.deliveries.add(delivery);
	}
	
	/**
	 * removes the delivery at the specified index and returns it
	 * @param index
	 * @return
	 */
	public Delivery popDelivery(int index){
		return this.deliveries.remove(index);
	}
	
	/**
	 * empties deliveries
	 */
	public void reinitializeClusters() {
		deliveries = new ArrayList<Delivery>();
	}
	/**
	 * centroid getter
	 * @return
	 */
	public Pair<Double, Double> getCentroid() {
		return centroid;
	}
	
	/**
	 * deliveries s getter
	 * @return
	 */
	public List<Delivery> getDeliveries() {
		return deliveries;
	}
	
	/**
	 * centroid s setter
	 * @param centroid
	 */
	public void setCentroid(Pair<Double, Double> centroid) {
		this.centroid = centroid;
	}
	
	/**
	 * sort deliveries in a decreasing order based on euclidian distance to centroid
	 */
	public void sortDeliveriesByEuclidianDistanceToCentroid() {
		for (int i = 0; i< deliveries.size()-1; i++){
			for (int j = 0; j<deliveries.size()-i-1; j++) {
				Pair<Double, Double> firstIntersectionData = new Pair<Double, Double>(deliveries.get(j).getAddress().getLat(),
						deliveries.get(j).getAddress().getLon());
				Pair<Double, Double> secondIntersectionData = new Pair<Double, Double>(deliveries.get(j+1).getAddress().getLat(),
						deliveries.get(j+1).getAddress().getLon());
				if(calculateDistanceToCentroid(firstIntersectionData)<calculateDistanceToCentroid(secondIntersectionData)) {
					Delivery temp = deliveries.get(j);
					deliveries.set(j, deliveries.get(j+1));
					deliveries.set(j+1, temp);
				}
			}
		}
	}
	
	public double calculateDistanceToCentroid(Pair<Double, Double> intersectionData) {
		/*TODO : in private ??*/
		return Math.sqrt(Math.pow((intersectionData.getKey() - centroid.getKey()), 2)
				+ Math.pow((intersectionData.getValue() - centroid.getValue()), 2));
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String cluster = "Centroid : x = " + centroid.getKey().toString() + " y =" + centroid.getValue().toString() +"\n\r";
		for (Delivery delivery : deliveries) {
			cluster += delivery.getAddress().toString() +"\n\r";
		}
		return cluster;
	}
}
