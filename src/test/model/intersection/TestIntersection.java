package test.model.intersection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.model.Intersection;
import main.model.Section;

/**
 * Test of Intersection class.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class TestIntersection {
    private Intersection intersection;

    @Before
    public void setUp() {
	this.intersection = new Intersection(1, 1, 1);
    }

    @Test
    public void testAddOutcomingSection() {
	Intersection depart = new Intersection(4, 0, 0);
	Intersection arrivee = new Intersection(3, 1, 2);
	Section toAdd = new Section(depart, arrivee, 10, "PHILLIPPE");
	this.intersection.addOutcomingSection(toAdd);
	assertEquals("Added wrong outcoming section", this.intersection.getOutcomingSections().get(0), toAdd);
	this.intersection.addOutcomingSection(toAdd);
	assert (this.intersection.getOutcomingSections().size() == 1) : "Two equals sections were added";
    }

    @Test(expected = AssertionError.class)
    public void testAddEmptyOutcomingSection() {
	this.intersection.addOutcomingSection(null);
    }

    @Test
    public void testEquals() {
	Intersection egalIntersection = new Intersection(1, 1, 1);
	Intersection differentIntersection = new Intersection(4, 2, 2);
	assertEquals("egal intersection were not deemed egal", this.intersection, egalIntersection);
	assert (!this.intersection.equals(differentIntersection)) : "Differents intersection were deemed egals";
    }
}
