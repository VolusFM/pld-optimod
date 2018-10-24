/**
 * 
 */

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controler.Controler;
import model.ModelInterface;
import xml.XMLException;

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
		new Controler();
	}

}
