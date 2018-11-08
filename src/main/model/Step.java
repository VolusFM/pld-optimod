package main.model;

import java.util.ArrayList;
import java.util.List;

public class Step {

	private List<Section> sections;
	private Delivery startDelivery;
	private Delivery endDelivery;

	public Step(List<Section> sections) {
		this.sections = new ArrayList<>(sections);
		startDelivery = TourCalculator.getInstance().findCorrespondingDelivery(sections.get(0).getStart());
		endDelivery = TourCalculator.getInstance().findCorrespondingDelivery(sections.get(sections.size() - 1).getEnd());
	}

	public double calculateLength() {
		double sum = 0;
		for (Section section : sections) {
			sum += section.getLength();
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Step from " + startDelivery.getAddress().getId() + " to " + endDelivery.getAddress().getId();
	}

	public List<Section> getSections() {
		return sections;
	}

	public Delivery getStartDelivery() {
		return startDelivery;
	}

	public Delivery getEndDelivery() {
		return endDelivery;
	}
}
