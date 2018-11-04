package xml;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Delivery;
import model.Intersection;
import model.Plan;
import model.Section;
import model.TourCalculator;

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
	
		return new Intersection((int) id, latitude, longitude);
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
   		Intersection departure = plan.getIntersectionById((int) idDeparture); // XXX : is this OK ?
   		Intersection arrival = plan.getIntersectionById((int) idArrival);
   		
   		String streetName = elt.getAttribute("nomRue");
   		
   		double length = Double.parseDouble(elt.getAttribute("longueur"));

   		if (length <= 0) {
   			throw new XMLException("Error when loading file : length of a section must be positive");
   		}
   		
   		return new Section(departure, arrival, length, streetName);
    }
	
    
    // XXX : this assumes the plan to be loaded, right ?
	public static void load(Plan plan, TourCalculator calculator) throws ParserConfigurationException, SAXException, IOException, XMLException {
		File xml = XMLFileOpener.getInstance().open();
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		Element root = document.getDocumentElement();
		if (root.getNodeName().equals("demandeDeLivraisons")) {
			buildFromDOMXML(root, plan, calculator);
		} else {
			throw new XMLException("File's content doesn't match that of a deliveries request");
		}
	}
	
	private static void buildFromDOMXML(Element rootNode, Plan plan, TourCalculator calculator) throws XMLException, NumberFormatException {
		Node depot = rootNode.getElementsByTagName("entrepot").item(0);
		NodeList deliveriesNodes = rootNode.getElementsByTagName("livraison");
		
		
		
		
		calculator.setDepot(createDelivery((Element) depot, plan));
		for (int i = 0; i < deliveriesNodes.getLength(); i++) {
			calculator.addDelivery(createDelivery((Element) deliveriesNodes.item(i), plan));
		}
		
		
		
		
	}
	
	private static Delivery createDelivery(Element elt, Plan plan) {
		Intersection departure = plan.getIntersectionById((int) Long.parseLong(elt.getAttribute("adresse")));;
		
		if (elt.hasAttribute("duree")) { // Actual delivery
			int duration = Integer.parseInt(elt.getAttribute("duree"));			

			return new Delivery(duration, departure);
		} else {						 // Not a delivery but a depot 			
			Calendar departureTime = GregorianCalendar.getInstance();
			// Parse date
			String date[] = elt.getAttribute("heureDepart").split(":");
			int hour = Integer.parseInt(date[0]);
			int minutes = Integer.parseInt(date[1]);
			int seconds = Integer.parseInt(date[2]);			
			departureTime.set(Calendar.HOUR, hour);
			departureTime.set(Calendar.MINUTE, minutes);
			departureTime.set(Calendar.SECOND, seconds);
			Delivery d = new Delivery(0, departure);
			d.setHour(departureTime);
			return d;
		}
	}
}
