package test.model.plan;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;

public class DijkstraTest {

    private Plan p;
    
    @Before
    public void setUp() {
	p = ModelInterface.getPlan();
    }
    
    @Test
    public void testNormalCaseDijkstra(){
	Intersection a = new Intersection(4, 8, 8);
	Intersection b = new Intersection(5, 9, 8);
	Intersection c = new Intersection(6, 10, 8);
	Intersection d = new Intersection(7, 11, 8);
	Intersection e = new Intersection(8, 12, 8);
	ModelInterface.addIntersection(a);
	ModelInterface.addIntersection(b);
	ModelInterface.addIntersection(c);
	ModelInterface.addIntersection(d);
	ModelInterface.addIntersection(e);
	Section ab = new Section(a, b, 4, "canard");
	Section ac = new Section(a, c, 3, "canard");
	Section cd = new Section(c, d, 2, "canard");
	Section dc = new Section(d, c, 2, "canard");
	Section db = new Section(d, b, 1, "canard");
	Section be = new Section(b, e, 8, "canard");
	Section ca = new Section(c, a, 6, "canard");
	ModelInterface.addSection(ab);
	ModelInterface.addSection(ac);
	ModelInterface.addSection(cd);
	ModelInterface.addSection(dc);
	ModelInterface.addSection(db);
	ModelInterface.addSection(be);
	ModelInterface.addSection(ca);
	Pair<HashMap<Long, Double>, HashMap<Long, Long>> results = ModelInterface.dijkstra(a);

	HashMap<Long, Double> distances = results.getKey();
	HashMap<Long, Long> predecessors = results.getValue();
	double[] expectedDistances = {0.0,4.0,3.0,5.0,12.0};
	int [] expectedPredecessors = {4,4,4,6,5};
	int j=0;
	for (long i : distances.keySet()) {
	    assert(distances.get(i)==expectedDistances[j]);
	    assert(predecessors.get(i)==expectedPredecessors[j]);
	    System.out.println(i + ", d=" + distances.get(i) + ", predecessors =" + predecessors.get(i));
	    j++;
	}

	// Expected output :
	// 4, d=0.0, predecessors =4
	// 5, d=4.0, predecessors =4
	// 6, d=3.0, predecessors =4
	// 7, d=5.0, predecessors =6
	// 8, d=12.0, predecessors =5
    }
}
