package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.XMLException;

public class DefaultState implements State{
	public void openPlan(Controler controler)throws XMLException, ParserConfigurationException, SAXException, IOException{}
	public void openDeliveries(Controler controler) throws  XMLException, ParserConfigurationException, SAXException, IOException{}
	public void openParameters(Controler controler){}
}
