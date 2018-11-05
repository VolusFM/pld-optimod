package test.model.tsp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.model.Delivery;
import main.model.Intersection;
import main.model.Plan;
import main.model.Section;
import main.model.TourCalculator;
import main.model.tsp.TSP1;
import main.model.tsp.TemplateTSP;

public class TSPTest {

	private double[][] graph = {
			/* v---- 0, 1, 2, 3, 4 */
			/* 0 */ {0, 2, 3, 4, 5},
			/* 1 */ {3, 0, 4, 8, 2},
			/* 2 */ {1, 2, 0, 5, 3},
			/* 3 */ {4, 5, 3, 0, 3},
			/* 4 */ {4, 5, 6, 5, 0},
			};
	
	private int[] duree = // TODO : je ne comprend pas comment c'est utilisé
			/* 0, 1, 2, 3, 4 */
			  {0, 0, 0, 0, 0};
	
	private int nbSommets = 5;
	
	@Test
	public void solveBasicTSP() {
		TemplateTSP tsp = new TSP1();
		tsp.searchAndDisplayBestSolution(10000, nbSommets, graph, duree);
	}
	
	
	
	@Test
	public void solveAnotherTSP() {
        Plan plan = new Plan();
        /* Creation intersections */
        Intersection i1 = new Intersection(1, 5, 5);
        Intersection i2 = new Intersection(2, 8, 8);
        Intersection i3 = new Intersection(3, 10, 10);
        /* Create sections */
        Section s1 = new Section(i1, i2, 6, "");
        Section s2 = new Section(i1, i3, 6, "");
        Section s6 = new Section(i2, i1, 6, "");
        Section s7 = new Section(i2, i3, 6, "");
        Section s8 = new Section(i3, i1, 6, "");
        Section s9 = new Section(i3, i2, 6, "");
        /* Add sections to intersection */
        i1.addOutcomingSection(s1);
        i1.addOutcomingSection(s2);
        i2.addOutcomingSection(s6);
        i2.addOutcomingSection(s7);
        i3.addOutcomingSection(s8);
        i3.addOutcomingSection(s9);
        /* Initialisation plan avec intersection */
        plan.addIntersection(i1);
        plan.addIntersection(i2);
        plan.addIntersection(i3);
        /* create delivery */
        Delivery d1 = new Delivery(5, i3);
        Delivery d3 = new Delivery(15, i2);
        List<Delivery> deliveries = new ArrayList<Delivery>();
        deliveries.add(d1);
        deliveries.add(d3);
        
        
//        TourCalculator.init(plan, deliveries, new Delivery(0, i1)); FIXME
        TourCalculator.getInstance().calculateTours(0);
	}

	
}
