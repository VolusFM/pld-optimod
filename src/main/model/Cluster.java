package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.util.Pair;

public class Cluster {
	private List<Delivery> deliveries;
	private Pair<Double, Double> centroid;
	private boolean isBalanced;

	/**
	 * Create a unbalanced cluster.
	 * 
	 * @param centroid : Latitude and Longitude of cluster's centroid
	 */
	public Cluster(Pair<Double, Double> centroid) {
		this.centroid = centroid;
		deliveries = new ArrayList<Delivery>();
		isBalanced = false;
	}

	/**
	 * add a new delivery to cluster list
	 * 
	 * @param delivery : Delivery to add
	 */
	public void addDelivery(Delivery delivery) {
		if (delivery == null) {
			throw new AssertionError("Tried to add an empty delivery in a cluster");
		}
		this.deliveries.add(delivery);
	}

	/**
	 * removes the delivery at the specified index and returns it. Throws an
	 * assertion error if index is out of range
	 * @param index : index of the delivery to pop
	 * @return the delivery that has been popped.
	 */
	public Delivery popDelivery(int index) {
		if ((index > deliveries.size()) || (deliveries.size() == 0)) {
			throw new AssertionError("Delivery 's index to pop is out of range");
		}
		return this.deliveries.remove(index);
	}
/**
 * Evaluate the quality of a clustering
 * @return : a coefficient that reflect the quality of a clustering
 */
	public double calculateCoefficient() {
		double coeff = 0;
		for (Delivery delivery : deliveries) {
			Pair<Double, Double> deliveryData = new Pair<Double, Double>(delivery.getAddress().getLat(),
					delivery.getAddress().getLon());
			coeff += calculateDistanceToCentroid(deliveryData);
		}
		return coeff;
	}

	/**
	 * Reinitialize the delivery list
	 */
	public void reinitializeClusters() {
		deliveries = new ArrayList<Delivery>();
	}

	/**
	 * centroid getter
	 * 
	 * @return cluster s centroid
	 */
	public Pair<Double, Double> getCentroid() {
		return centroid;
	}

	/**
	 * deliveries s getter
	 * 
	 * @return cluster s delivery list
	 */
	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	/**
	 * isBalanced s getter
	 * 
	 * @return if the cluster is balanced
	 */
	public boolean isBalanced() {
		return isBalanced;
	}

	/**
	 * centroid s setter
	 * 
	 * @param centroid : new clusters centroid
	 */
	public void setCentroid(Pair<Double, Double> centroid) {
		this.centroid = centroid;
	}

	/**
	 * isBalanced s setter
	 * 
	 * @param isBalanced
	 */
	public void setIsBalanced(boolean isBalanced) {
		this.isBalanced = isBalanced;
	}

	/**
	 * sort deliveries in a decreasing order based on euclidean distance to centroid
	 */
	public void sortDeliveriesByEuclidianDistanceToCentroid() {
		Collections.sort(deliveries, new Comparator<Delivery>() {
			public int compare(Delivery firstDelivery, Delivery secondDelivery) {
				double firstDistance = calculateDistanceToCentroid(new Pair<Double, Double>(
						firstDelivery.getAddress().getLat(), firstDelivery.getAddress().getLon()));
				double secondDistance = calculateDistanceToCentroid(new Pair<Double, Double>(
						secondDelivery.getAddress().getLat(), secondDelivery.getAddress().getLon()));
				if (firstDistance < secondDistance) {
					return 1;
				}
				if (firstDistance > secondDistance) {
					return -1;
				}
				return 0;
			}
		});

	}
/**
 * Calculate the distance between a pair of coordinate and clusters centroid
 * @param intersectionData : data's latitude and longitude
 * @return : the distance between coordinate and centroid
 */
	private double calculateDistanceToCentroid(Pair<Double, Double> intersectionData) {
		return Math.sqrt(Math.pow((intersectionData.getKey() - centroid.getKey()), 2)
				+ Math.pow((intersectionData.getValue() - centroid.getValue()), 2));
	}

	@Override
	public String toString() {
		String cluster = "Centroid : x = " + centroid.getKey().toString() + " y =" + centroid.getValue().toString()
				+ "\n\r";
		for (Delivery delivery : deliveries) {
			cluster += delivery.getAddress().toString() + "\n\r";
		}
		return cluster;
	}
}
