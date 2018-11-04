package main.model;

import java.util.ArrayList;
import java.util.List;

public class Step {

	private List<Section> sections;

	public Step(List<Section> sections) {
		this.sections = new ArrayList<>(sections);
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
		return sections.toString();
	}

}
