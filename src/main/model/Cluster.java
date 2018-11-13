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
	 * Create a cluster.
	 * 
	 * @param deliveries
	 */
	public Cluster(Pair<Double, Double> centroid) {
		this.centroid = centroid;
		deliveries = new ArrayList<Delivery>();
		isBalanced = false;
	}

	/**
	 * add delivery
	 * 
	 * @param delivery
	 */
	public void addDelivery(Delivery delivery) {
		if (delivery == null) {
			throw new AssertionError("Tried to add an empty delivery in a cluster");
		}
		this.deliveries.add(delivery);
	}

	/**
	 * removes the delivery at the specified index and returns it
	 * 
	 * @param index
	 * @return
	 */
	public Delivery popDelivery(int index) {
		if ((index > deliveries.size()) || (deliveries.size() == 0)) {
			throw new AssertionError("Delivery 's index to pop is out of range");
		}
			return this.deliveries.remove(index);
	}

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
	 * empties deliveries
	 */
	public void reinitializeClusters() {
		deliveries = new ArrayList<Delivery>();
	}

	/**
	 * centroid getter
	 * 
	 * @return
	 */
	public Pair<Double, Double> getCentroid() {
		return centroid;
	}

	/**
	 * deliveries s getter
	 * 
	 * @return
	 */
	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	/**
	 * isBalanced s getter
	 * 
	 * @return
	 */
	public boolean isBalanced() {
		return isBalanced;
	}

	/**
	 * centroid s setter
	 * 
	 * @param centroid
	 */
	public void setCentroid(Pair<Double, Double> centroid) {
		this.centroid = centroid;
	}

	/**
	 * isBalanced s setter
	 * 
	 * @param centroid
	 */
	public void setIsBalanced(boolean isBalanced) {
		this.isBalanced = isBalanced;
	}

	/**
	 * sort deliveries in a decreasing order based on euclidian distance to centroid
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

	private double calculateDistanceToCentroid(Pair<Double, Double> intersectionData) {
		return Math.sqrt(Math.pow((intersectionData.getKey() - centroid.getKey()), 2)
				+ Math.pow((intersectionData.getValue() - centroid.getValue()), 2));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String cluster = "Centroid : x = " + centroid.getKey().toString() + " y =" + centroid.getValue().toString()
				+ "\n\r";
		for (Delivery delivery : deliveries) {
			cluster += delivery.getAddress().toString() + "\n\r";
		}
		return cluster;
	}
}
