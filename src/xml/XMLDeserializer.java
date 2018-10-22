package xml;

import java.io.File;
import java.io.IOException;

import javax.print.attribute.standard.Destination;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Intersection;
import model.Plan;
import model.Section;

public class XMLDeserializer {
	public static void load(Plan plan) throws ParserConfigurationException, SAXException, IOException, XMLException {
		File xml = XMLFileOpener.getInstance().open();
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		Element root = document.getDocumentElement();
		if (root.getNodeName().equals("reseau")) {
			buildFromDOMXML(root, plan);
		} else
			throw new XMLException("Unrecognized file format");
	}

	private static void buildFromDOMXML(Element noeudDOMRacine, Plan plan) throws XMLException, NumberFormatException {
		NodeList listIntersections = noeudDOMRacine.getElementsByTagName("noeud");
		for (int i = 0; i < listIntersections.getLength(); i++) {
			plan.addIntersection(createIntersection((Element) listIntersections.item(i)));
		}
		NodeList listSections = noeudDOMRacine.getElementsByTagName("troncon");
		for (int i = 0; i < listSections.getLength(); i++) {
			plan.addSection(createSection((Element) listSections.item(i)));
		}
	}
	
	private static Intersection createIntersection(Element element) throws XMLException {
		int id = Integer.parseInt(element.getAttribute("id"));
		double latitude = Integer.parseInt(element.getAttribute("latitude"));
		double longitude = Integer.parseInt(element.getAttribute("longitude"));
	
		return new Intersection(id, latitude, longitude);
	}
	
    private static Section createSection(Element elt) throws XMLException{ // TODO : naming
    	int idDeparture = Integer.parseInt(elt.getAttribute("origine"));
    	int idArrival = Integer.parseInt(elt.getAttribute("destination"));
   		Intersection departure = plan.findIntersectionById(idDeparture); // XXX : is this OK ?
   		Intersection arrival = plan.findIntersectionById(idArrival);
   		
   		String streetName = elt.getAttribute("nomRue");
   		
   		double length = Double.parseDouble(elt.getAttribute("longueur"));

   		if (length <= 0) {
   			throw new XMLException("Error when loading file : length of a section must be positive");
   		}
   		
   		return new Section(departure, arrival, length, streetName);
    }
}
