/**
 * 
 */
package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.controler.Controler;
import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.Step;
import main.xml.XMLException;

/**
 * @author Montigny
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws XMLException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void main(String[] args) {
		Plan plan = new Plan();
		/* Creation intersections */
		Intersection i1 = new Intersection(1, 5, 5);
		Intersection i2 = new Intersection(2, 8, 8);
		Intersection i3 = new Intersection(3, 10, 10);
		Intersection i4 = new Intersection(4, 7, 14);
		Intersection i5 = new Intersection(5, 12, 2);
		/* Create sections */
		Section s1 = new Section(i1, i2, 6, "");
		Section s2 = new Section(i1, i3, 6, "");
		Section s3 = new Section(i1, i5, 6, "");
		Section s4 = new Section(i2, i4, 6, "");
		Section s5 = new Section(i2, i5, 6, "");
		Section s6 = new Section(i2, i1, 6, "");
		Section s7 = new Section(i2, i3, 6, "");
		Section s8 = new Section(i3, i1, 6, "");
		Section s9 = new Section(i3, i2, 6, "");
		Section s0 = new Section(i5, i4, 6, "");
		/* Add sections to intersection */
		i1.addOutcomingSection(s1);
		i1.addOutcomingSection(s2);
		i1.addOutcomingSection(s3);
		i2.addOutcomingSection(s4);
		i2.addOutcomingSection(s5);
		i2.addOutcomingSection(s6);
		i2.addOutcomingSection(s7);
		i3.addOutcomingSection(s8);
		i3.addOutcomingSection(s9);
		i5.addOutcomingSection(s0);
		/* Initialisation plan avec intersection */
		plan.addIntersection(i1);
		plan.addIntersection(i2);
		plan.addIntersection(i3);
		plan.addIntersection(i4);
		plan.addIntersection(i5);
		/* create delivery */
		Delivery d1 = new Delivery(5, i3);
		Delivery d2 = new Delivery(10, i1);
		Delivery d3 = new Delivery(15, i4);
		List<Delivery> deliveries = new ArrayList<Delivery>();
		deliveries.add(d1);
		deliveries.add(d2);
		deliveries.add(d3);
		/* create steps */
		List<Section> section = new ArrayList<Section>();
		section.add(s1);
		section.add(s5);
		section.add(s0);
		Step step = new Step(section);
		List<Step> steps = new ArrayList<Step>();
		steps.add(step);
		/* initialize app */
		ModelInterface.setPlan(plan);
		ModelInterface.addDelivery(d1);
		ModelInterface.addDelivery(d2);
		ModelInterface.addDelivery(d3);
		
		ModelInterface.createTour(1, steps, deliveries);
		/* lauch app */
		new Controler();
	}

}
