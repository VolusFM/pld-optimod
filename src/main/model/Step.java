package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Step represents the path between two deliveries in a tour. It is a list of
 * sections.
 *
 */
public class Step {

    private List<Section> sections;
    private Delivery startDelivery;
    private Delivery endDelivery;

    /**
     * Create a Step.
     * @param sections is the list of sections leading from one delivery to the other.
     */
    public Step(List<Section> sections) {
	this.sections = new ArrayList<>(sections);
	startDelivery = TourCalculator.getInstance().findCorrespondingDelivery(sections.get(0).getStart());
	endDelivery = TourCalculator.getInstance()
		.findCorrespondingDelivery(sections.get(sections.size() - 1).getEnd());
    }

    /**
     * Calculate the total length of a step.
     * @return Double, the total length (in meters) of all the sections in the step.
     */
    public double calculateLength() {
	double sum = 0;
	for (Section section : sections) {
	    sum += section.getLength();
	}
	return sum;
    }

    /**
     * Getter for sections of the step.
     * @return List, the list of Sections in the step.
     */
    public List<Section> getSections() {
	return sections;
    }

    /**
     * Getter for the start delivery.
     * @return Delivery, the start of the step.
     */
    public Delivery getStartDelivery() {
	return startDelivery;
    }

    /**
     * Getter for the end delivery.
     * @return Delivery, the end of the step.
     */
    public Delivery getEndDelivery() {
	return endDelivery;
    }

    @Override
    public String toString() {
	return "Step from " + startDelivery.getAddress().getId() + " to " + endDelivery.getAddress().getId();
    }

}
