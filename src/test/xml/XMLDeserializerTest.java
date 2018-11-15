package test.xml;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import main.model.ModelInterface;
import main.model.Plan;
import main.xml.XMLDeserializer;
import main.xml.XMLException;

public class XMLDeserializerTest {

    private Plan plan;

    @Before
    public void setUp() throws Exception {
	plan = new Plan();
	ModelInterface.setMap(plan);
    }

    @After
    public void tearDown() {
	ModelInterface.emptyLoadedDeliveries();
    }

    @Test
    public void testLoadValidPlan() {
	System.out.println("Please load 'petitPlanValide.xml'");
	try {
	    XMLDeserializer.load(plan);
	} catch (ParserConfigurationException | SAXException | IOException | XMLException e) {
	    fail("Failed to parse valid plan");
	}
    }

    @Test
    public void testLoadPlanWithUnvalidRoot() {
	System.out.println("Please load 'petitPlanInvalidePasReseau.xml'");
	try {
	    XMLDeserializer.load(plan);
	} catch (ParserConfigurationException | SAXException | IOException | XMLException e) {
	    if (e.getClass() == XMLException.class) {
		return; // expected exception for invalid XML syntax
	    }
	    e.printStackTrace();
	}
	fail("Unexpected exception or lack of exception");
    }

    @Test
    public void testLoadUnvalidPlan() {
	System.out.println("Please load 'petitPlanInvalide.xml'");
	try {
	    XMLDeserializer.load(plan);
	} catch (ParserConfigurationException | SAXException | IOException | XMLException e) {
	    if (e.getClass() == SAXParseException.class) {
		return; // expected exception for invalid XML syntax
	    }
	    e.printStackTrace();
	}
	fail("Unexpected exception or lack of exception");
    }

    @Test
    public void testLoadNotAnXMLFile() {
	System.out.println("Please load 'petitPlanInvalideNonXml.not_xml'");
	try {
	    XMLDeserializer.load(plan);
	} catch (ParserConfigurationException | SAXException | IOException | XMLException e) {
	    fail("Even though the file doesn't claim to be XML, if it is actually a valid XML file with a plan it is fine");
	}
    }

    @Test
    public void testLoadPlanWithWrongSectionLength() {
	System.out.println("Please load 'petitPlanInvalideLongueurSection.xml'");

	try {
	    XMLDeserializer.load(plan);
	} catch (ParserConfigurationException | SAXException | IOException | XMLException e) {
	    if (e.getClass() == XMLException.class) {
		return; // expected exception when a section has a negative
			// or
			// null length
	    }
	    e.printStackTrace();
	}
	fail("Unexpected exception or lack of exception");
    }

}
