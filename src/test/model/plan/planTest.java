package test.model.plan;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.model.Intersection;
import main.model.Plan;
import main.model.Section;

public class planTest {
	private Plan p;
    
    @Before
    public void setUp() {
		p = new Plan();
	}
    
    @Test
    public void testAddIntersection() {
    	Intersection toAdd = new Intersection(4,8,8);
    	p.addIntersection(toAdd);
    	assertEquals("Intersections dont match",p.getGraph().get(toAdd.getId()), toAdd);
    }
    
    @Test
    public void testAddSection() {
    	Intersection int1 = new Intersection(4,8,8);
    	Intersection int2 = new Intersection(5,8,10);
    	Section toAdd = new Section(int1, int2, 23, "PHILLIPPE");
    	p.addIntersection(int1);
    	p.addIntersection(int2);
    	p.addSection(toAdd);
    	assertEquals("",p.getGraph().get(int1.getId()).getOutcomingSections().get(0), toAdd);
    }
    
    
}
