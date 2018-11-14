package main.xml;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.model.Delivery;
import main.model.Intersection;
import main.model.ModelInterface;
import main.model.Plan;
import main.model.Section;
import main.model.TourCalculator;

/**
 * 
 * XMLDeserializer handles the conversion from XML to Model objects.
 *
 */
public abstract class XMLDeserializer {

    /* Constants used for deserialization */
    private static final String DELIVERIES_REQUEST = "demandeDeLivraisons";
    private static final String DELIVERY = "livraison";
    private static final String DEPARTURE_TIME = "heureDepart";
    private static final String DEPOT = "entrepot";
    private static final String DESTINATION = "destination";
    private static final String DURATION = "duree";
    private static final String INTERSECTION = "noeud";
    private static final String INTERSECTION_ID = "id";
    private static final String INTERSECTION_LONGITUDE = "longitude";
    private static final String INTERSECTION_LATITUDE = "latitude";
    private static final String LENGTH = "longueur";
    private static final String ORIGIN = "origine";
    private static final String PLAN = "reseau";
    private static final String SECTION = "troncon";
    private static final String STREET_NAME = "nomRue";

    /**
     * Load a plan from an XML file.
     * 
     * @param plan is the plan into which we are going to load the sections and
     *                 intersections.
     * @throws ParserConfigurationException if the configuration is invalid.
     * @throws SAXException                 if deserialization fails.
     * @throws IOException                  if reading is interrupted.
     * @throws XMLException                 if the file's contents are invalid.
     */
    public static void load(Plan plan) throws ParserConfigurationException, SAXException, IOException, XMLException {
	File xml = XMLFileOpener.getInstance().open();
	DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document document = docBuilder.parse(xml);
	Element root = document.getDocumentElement();

	if (isPlan(root)) {
	    buildFromDOMXML(root, plan);
	} else {
	    throw new XMLException("Le contenu du fichier ne correspond pas à un plan.");
	}
    }

    /**
     * Build the plan from the XML DOM.
     * 
     * @param rootNode is the root node of the XML tree.
     * @param plan     is the plan into which we are going to load the sections and
     *                     intersections.
     * @throws XMLException if the file's contents are invalid.
     */
    private static void buildFromDOMXML(Element rootNode, Plan plan) throws XMLException {
	NodeList intersections = rootNode.getElementsByTagName(INTERSECTION);
	for (int i = 0; i < intersections.getLength(); i++) {
	    ModelInterface.addIntersection(createIntersection((Element) intersections.item(i)));
	}
	NodeList sections = rootNode.getElementsByTagName(SECTION);
	for (int i = 0; i < sections.getLength(); i++) {
	    ModelInterface.addSection(createSection((Element) sections.item(i), plan));
	}
    }

    /**
     * Create an Intersection from an XML node.
     * 
     * @param element is the element from which we are going to create the
     *                    Intersection.
     * @return Intersection, the created intersection.
     */
    private static Intersection createIntersection(Element element) {
	long id = Long.parseLong(element.getAttribute(INTERSECTION_ID));
	double latitude = Double.parseDouble(element.getAttribute(INTERSECTION_LATITUDE));
	double longitude = Double.parseDouble(element.getAttribute(INTERSECTION_LONGITUDE));

	return new Intersection(id, latitude, longitude);
    }

    /**
     * Create a Section from an XML node.
     * 
     * @param element is the element from which we are going to create the Section.
     * @param plan    is the plan we are loading.
     * @return Section, the created section.
     * @throws XMLException if the file's content are invalid.
     */
    private static Section createSection(Element element, Plan plan) throws XMLException {
	long idDeparture = Long.parseLong(element.getAttribute(ORIGIN));
	long idArrival = Long.parseLong(element.getAttribute(DESTINATION));
	Intersection departure = plan.getIntersectionById(idDeparture);
	Intersection arrival = plan.getIntersectionById(idArrival);

	String streetName = element.getAttribute(STREET_NAME);

	double length = Double.parseDouble(element.getAttribute(LENGTH));

	if (length <= 0) {
	    throw new XMLException("Erreur lors du chargement : la longueur d'un tronçon de rue doit être positive.");
	}

	return new Section(departure, arrival, length, streetName);
    }

    /**
     * @param plan       is the plan loaded into the application.
     * @param calculator is the calculator instance into which we are going to load
     *                       the deliveries.
     * @throws ParserConfigurationException if the configuration is invalid.
     * @throws SAXException                 if deserialization fails.
     * @throws IOException                  if reading is interrupted.
     * @throws XMLException                 if the file's contents are invalid.
     */
    public static void load(Plan plan, TourCalculator calculator)
	    throws ParserConfigurationException, SAXException, IOException, XMLException {
	File xml = XMLFileOpener.getInstance().open();
	DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document document = docBuilder.parse(xml);
	Element root = document.getDocumentElement();

	if (isDeliveriesRequest(root)) {
	    buildFromDOMXML(root, plan, calculator);
	} else {
	    throw new XMLException("Le contenu du fichier ne correspond pas à une demande de livraison.");
	}
    }

    /**
     * Build the deliveries from the XML DOM.
     * 
     * @param rootNode   is the root node of the XML tree.
     * @param plan       is the plan loaded into the application.
     * @param calculator is the calculator into which we are going to load the
     *                       deliveries.
     * @throws XMLException if the file's contents are invalid.
     */
    private static void buildFromDOMXML(Element rootNode, Plan plan, TourCalculator calculator)
	    throws XMLException, NumberFormatException {
	Node depot = rootNode.getElementsByTagName(DEPOT).item(0);
	NodeList deliveriesNodes = rootNode.getElementsByTagName(DELIVERY);

	ModelInterface.setDepot(createDelivery((Element) depot, plan));
	for (int i = 0; i < deliveriesNodes.getLength(); i++) {
	    ModelInterface.addDeliveryToTourCalculator(createDelivery((Element) deliveriesNodes.item(i), plan));
	}
    }

    /**
     * Create a delivery from an XML node.
     * 
     * @param element is the element from which we are going to create the Section.
     * @param plan    is the plan loaded into the application.
     * @return Delivery, the created delivery.
     * @throws XMLException if the file's content are invalid.
     */
    private static Delivery createDelivery(Element element, Plan plan) throws XMLException {

	Intersection address = plan.getIntersectionById(Long.parseLong(element.getAttribute("adresse")));

	if (address == null) {
	    throw new XMLException("Erreur lors du chargement : une livraison doit avoir une adresse connue.");
	}

	if (isDeliveryNode(element)) {
	    int duration = Integer.parseInt(element.getAttribute(DURATION));

	    return new Delivery(duration, address);
	}

	// If it's not a delivery, treat the node like it's a depot.
	Calendar departureTime = Calendar.getInstance();
	String date[] = element.getAttribute(DEPARTURE_TIME).split(":");
	int hour = Integer.parseInt(date[0]);
	int minutes = Integer.parseInt(date[1]);
	int seconds = Integer.parseInt(date[2]);
	departureTime.set(Calendar.HOUR_OF_DAY, hour);
	departureTime.set(Calendar.MINUTE, minutes);
	departureTime.set(Calendar.SECOND, seconds);
	Delivery d = new Delivery(0, address);
	d.setHour(departureTime);

	return d;
    }

    /**
     * Check whether the loaded XML file's contents match those of a plan.
     * 
     * @param root is the root node of the file's XML tree.
     * @return boolean, whether the file's content match those of a plan.
     */
    private static boolean isPlan(Element root) {
	return root.getNodeName().equals(PLAN);
    }

    /**
     * Check whether the loaded XML file's contents match those of deliveries
     * request.
     * 
     * @param root is the root node of the file's XML tree.
     * @return boolean, whether the file's content match those of a deliveries
     *         request.
     */
    private static boolean isDeliveriesRequest(Element root) {
	return root.getNodeName().equals(DELIVERIES_REQUEST);
    }

    /**
     * Check whether the current node's content match those of a delivery.
     * 
     * @param element is the node to evaluate.
     * @return boolean, whether the node's content match those of a delivery.
     */
    private static boolean isDeliveryNode(Element element) {
	return element.hasAttribute(DURATION);
    }
}
