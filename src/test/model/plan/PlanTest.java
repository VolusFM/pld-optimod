package test.model.plan;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;

/**
 * Test of Plan class.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class PlanTest {
    private Plan p;

    @Before
    public void setUp() {
	p = ModelInterface.getPlan();
    }

    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
	ModelInterface.emptyTourFactory();
    }

    @Test
    public void testAddIntersection() {
	Intersection toAdd = new Intersection(4, 8, 8);
	ModelInterface.addIntersection(toAdd);
	assertEquals("Intersections dont match", p.getGraph().get(toAdd.getId()), toAdd);
    }

    @Test
    public void testAddSection() {
	Intersection int1 = new Intersection(4, 8, 8);
	Intersection int2 = new Intersection(5, 8, 10);
	Section toAdd = new Section(int1, int2, 23, "PHILLIPPE");
	ModelInterface.addIntersection(int1);
	ModelInterface.addIntersection(int2);
	ModelInterface.addSection(toAdd);
	assertEquals("", p.getGraph().get(int1.getId()).getOutcomingSections().get(0), toAdd);
    }

}
