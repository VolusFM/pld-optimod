package xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import model.Delivery;
import model.Intersection;
import model.Plan;
import model.Section;

/**
 * 
 * @author lsterner
 *
 */
public class XMLDeserializer {
	/**
	 * Load a plan from an XML file.
	 * @param plan
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XMLException
	 */
	public static void load(Plan plan) throws ParserConfigurationException, SAXException, IOException, XMLException {
		File xml = XMLFileOpener.getInstance().open();
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		Element root = document.getDocumentElement();
		if (root.getNodeName().equals("reseau")) {
			buildFromDOMXML(root, plan);
		} else {
			throw new XMLException("File's content doesn't match that of a plan");
		}
	}

	/**
	 * 
	 * @param rootNode
	 * @param plan
	 * @throws XMLException
	 * @throws NumberFormatException
	 */
	private static void buildFromDOMXML(Element rootNode, Plan plan) throws XMLException, NumberFormatException {
		NodeList intersections = rootNode.getElementsByTagName("noeud");
		for (int i = 0; i < intersections.getLength(); i++) {
			plan.addIntersection(createIntersection((Element) intersections.item(i)));
		}
		NodeList sections = rootNode.getElementsByTagName("troncon");
		for (int i = 0; i < sections.getLength(); i++) {
			plan.addSection(createSection((Element) sections.item(i), plan));
		}
	}
	
	public static void load(List<Delivery> deliveries) throws ParserConfigurationException, SAXException, IOException, XMLException {
		File xml = XMLFileOpener.getInstance().open();
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		Element root = document.getDocumentElement();
		if (root.getNodeName().equals("demandeDeLivraisons")) {
			buildFromDOMXML(root, deliveries);
		} else {
			throw new XMLException("File's content doesn't match that of a deliveries request");
		}
	}
	
	private static void buildFromDOMXML(Element rootNode, List<Delivery> deliveries) throws XMLException, NumberFormatException {
		Node depot = rootNode.getElementsByTagName("entrepot").item(0);
		NodeList deliveriesNodes = rootNode.getElementsByTagName("livraison");
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 * @throws XMLException
	 */
	private static Intersection createIntersection(Element element) throws XMLException {
		long id = Long.parseLong(element.getAttribute("id"));
		double latitude = Double.parseDouble(element.getAttribute("latitude"));
		double longitude = Double.parseDouble(element.getAttribute("longitude"));
	
		return new Intersection(id, latitude, longitude);
	}
	

	/**
	 * 
	 * @param elt
	 * @param plan
	 * @return
	 * @throws XMLException
	 */
    private static Section createSection(Element elt, Plan plan) throws XMLException{ // TODO : naming
    	long idDeparture = Long.parseLong(elt.getAttribute("origine"));
    	long idArrival = Long.parseLong(elt.getAttribute("destination"));
   		Intersection departure = plan.getIntersectionById(idDeparture); // XXX : is this OK ?
   		Intersection arrival = plan.getIntersectionById(idArrival);
   		
   		String streetName = elt.getAttribute("nomRue");
   		
   		double length = Double.parseDouble(elt.getAttribute("longueur"));

   		if (length <= 0) {
   			throw new XMLException("Error when loading file : length of a section must be positive");
   		}
   		
   		return new Section(departure, arrival, length, streetName);
    }
}
