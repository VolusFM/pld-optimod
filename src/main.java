/**
 * 
 */

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Plan;
import xml.*;

/**
 * @author Montigny
 *
 */
public class main {

	/**
	 * @param args
	 * @throws XMLException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws XMLException, ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
//		XMLFileOpener fo = XMLFileOpener.getInstance();
//		fo.open();
//		
		Plan p = new Plan();
		XMLDeserializer.load(p);
		p.print();
	}

}
