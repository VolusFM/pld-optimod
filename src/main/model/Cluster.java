package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.util.Pair;

/**
 * Cluster represents a set of deliveries and is used to ease the resolution of
 * the TSP.
 *
 */
public class Cluster {
    private List<Delivery> deliveries;
    private Pair<Double, Double> centroid;
    private boolean isBalanced;

    /**
     * Create an unbalanced cluster.
     * 
     * @param centroid is the coordinates (longitude and latitude) of cluster's
     *                     centroid.
     */
    public Cluster(Pair<Double, Double> centroid) {
	this.centroid = centroid;
	this.deliveries = new ArrayList<Delivery>();
	this.isBalanced = false;
    }

    /**
     * Add a new delivery to this cluster.
     * 
     * @param delivery is the Delivery to add.
     */
    public void addDelivery(Delivery delivery) {
	if (delivery == null) {
	    throw new AssertionError("Tried to add an empty delivery in a cluster");
	}
	this.deliveries.add(delivery);
    }

    /**
     * Removes the delivery at the specified index and returns it. Throws an
     * assertion error if index is out of range.
     * 
     * @param index is the index of the delivery to pop.
     * @return Delivery, the delivery that has been popped.
     */
    public Delivery popDelivery(int index) {
	if ((index > this.deliveries.size()) || (this.deliveries.size() == 0)) {
	    throw new AssertionError("Delivery 's index to pop is out of range");
	}
	return this.deliveries.remove(index);
    }

    /**
     * Evaluate the quality of the clustering.
     * 
     * @return Double, a coefficient that reflect the quality of a clustering.
     */
    public double evaluateClusteringQuality() {
	double coeff = 0;
	for (Delivery delivery : this.deliveries) {
	    Pair<Double, Double> deliveryData = new Pair<Double, Double>(delivery.getAddress().getLat(),
		    delivery.getAddress().getLon());
	    coeff += calculateDistanceToCentroid(deliveryData);
	}
	return coeff;
    }

    /**
     * Reinitialize the deliveries list.
     */
    public void reinitializeClusters() {
	this.deliveries = new ArrayList<Delivery>();
    }

    /**
     * Sort deliveries in a decreasing order based on euclidean distance to
     * centroid.
     */
    public void sortDeliveriesByEuclidianDistanceToCentroid() {
	Collections.sort(this.deliveries, new Comparator<Delivery>() {
	    @Override
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
     * Calculate the distance between an intersection and clusters centroid.
     * 
     * @param coordinates is the intersection's latitude and longitude.
     * @return Double, the distance between coordinates and centroid.
     */
    protected double calculateDistanceToCentroid(Pair<Double, Double> coordinates) {
	return Math.sqrt(Math.pow((coordinates.getKey() - this.centroid.getKey()), 2)
		+ Math.pow((coordinates.getValue() - this.centroid.getValue()), 2));
    }

    /**
     * Getter for centroid.
     * 
     * @return Pair, the cluster's centroid.
     */
    public Pair<Double, Double> getCentroid() {
	return this.centroid;
    }

    /**
     * Getter for deliveries.
     * 
     * @return List, the cluster's deliveries.
     */
    public List<Delivery> getDeliveries() {
	return this.deliveries;
    }

    /**
     * Getter for balancing evaluation.
     * 
     * @return boolean, whether the cluster is balanced.
     */
    public boolean isBalanced() {
	return this.isBalanced;
    }

    /**
     * Setter for centroid.
     * 
     * @param centroid is cluster's new centroid.
     */
    protected void setCentroid(Pair<Double, Double> centroid) {
	this.centroid = centroid;
    }

    /**
     * Setter for balancing evaluation.
     * 
     * @param isBalanced is the cluster's new balancing evaluation.
     */
    protected void setIsBalanced(boolean isBalanced) {
	this.isBalanced = isBalanced;
    }

    @Override
    public String toString() {
	String cluster = "Centroid : x = " + this.centroid.getKey().toString() + " y ="
		+ this.centroid.getValue().toString() + "\n\r";
	for (Delivery delivery : this.deliveries) {
	    cluster += delivery.getAddress().toString() + "\n\r";
	}
	return cluster;
    }
}
