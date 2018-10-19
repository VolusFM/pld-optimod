package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*import modele.Cercle;
import modele.Plan;
import modele.Point;
import modele.PointFactory;
import modele.Rectangle;*/

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jdk.management.resource.internal.UnassignedContext;

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
		NodeList listSections = noeudDOMRacine.getElementsByTagName("trocon");
		for (int i = 0; i < listSections.getLength(); i++) {
			plan.addSection(createSection((Element) listSections.item(i)));
		}
	}
	
	private static Intersection createIntersection(Element element) throws XMLException {
		double longitude = Integer.parseInt(element.getAttribute("longitude"));
		double latitude = Integer.parseInt(element.getAttribute("latitude"));
		int id = Integer.parseInt(element.getAttribute("id"));

		return new Intersection(id, latitude, longitude);
	}
}
