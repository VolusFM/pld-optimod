/**
 * 
 */
package main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.controler.Controler;
import main.model.ModelInterface;
import main.model.Plan;
import main.xml.XMLException;

/**
 * @author Montigny
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws XMLException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) {
		ModelInterface.setPlan(new Plan());
		new Controler();
		
	}

}
