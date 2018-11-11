package test.model.plan;

import java.util.HashMap;

import org.junit.Test;

import javafx.util.Pair;
import main.model.Intersection;
import main.model.Plan;
import main.model.Section;

public class DijkstraTest {

    @Test
    public void testDijkstra() {
	Plan p = new Plan();
	Intersection a = new Intersection(4, 8, 8);
	Intersection b = new Intersection(5, 9, 8);
	Intersection c = new Intersection(6, 10, 8);
	Intersection d = new Intersection(7, 11, 8);
	Intersection e = new Intersection(8, 12, 8);
	p.addIntersection(a);
	p.addIntersection(b);
	p.addIntersection(c);
	p.addIntersection(d);
	p.addIntersection(e);
	Section ab = new Section(a, b, 4, "canard");
	Section ac = new Section(a, c, 3, "canard");
	Section cd = new Section(c, d, 2, "canard");
	Section dc = new Section(d, c, 2, "canard");
	Section db = new Section(d, b, 1, "canard");
	Section be = new Section(b, e, 8, "canard");
	Section ca = new Section(c, a, 6, "canard");
	p.addSection(ab);
	p.addSection(ac);
	p.addSection(cd);
	p.addSection(dc);
	p.addSection(db);
	p.addSection(be);
	p.addSection(ca);
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> results = p.Dijkstra(a);

	HashMap<Long, Double> distances = results.getKey();
	HashMap<Long, Long> predecessors = results.getValue();
	for (long i : distances.keySet()) {
	    System.out.println(i + ", d=" + distances.get(i) + ", predecessors =" + predecessors.get(i));
	}

	// Expected output :
	// 4, d=0.0, predecessors =4
	// 5, d=4.0, predecessors =4
	// 6, d=3.0, predecessors =4
	// 7, d=5.0, predecessors =6
	// 8, d=12.0, predecessors =5
    }
}
