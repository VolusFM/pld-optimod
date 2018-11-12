package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.util.Pair;

public class Cluster {
    private List<Delivery> deliveries;
    private Pair<Double, Double> centroid;
    private boolean isBalenced;

    /**
     * Create a cluster.
     * 
     * @param deliveries
     */
    public Cluster(Pair<Double, Double> centroid) {
	this.centroid = centroid;
	deliveries = new ArrayList<Delivery>();
	isBalenced = false;
    }

    /**
     * add delivery
     * 
     * @param delivery
     */
    public void addDelivery(Delivery delivery) {
	this.deliveries.add(delivery);
    }

    /**
     * removes the delivery at the specified index and returns it
     * 
     * @param index
     * @return
     */
    public Delivery popDelivery(int index) {
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
     * isBalenced s getter
     * 
     * @return
     */
    public boolean isBalenced() {
	return isBalenced;
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
     * isBalenced s setter
     * 
     * @param centroid
     */
    public void setIsBalenced(boolean isBalenced) {
	this.isBalenced = isBalenced;
    }

    /**
     * sort deliveries in a decreasing order based on euclidian distance to
     * centroid
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

    public double calculateDistanceToCentroid(Pair<Double, Double> intersectionData) {
	/* TODO : in private ?? */
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
