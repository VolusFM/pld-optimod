package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.Intersection;
import main.model.Section;
import main.ui.Window;
import main.xml.XMLException;

/**
 * Default state is the most basic state and parent class of all other states.
 *
 */
class DefaultState implements State {

    @Override
    public void openPlan(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException {
    }

    @Override
    public void openDeliveries(Controler controler, Window window)
	    throws XMLException, ParserConfigurationException, SAXException, IOException {
    }

    @Override
    public void calculatePlanning(Controler controler, Window window) {
    }

    @Override
    public void addDelivery(Controler controler, Window window) {
    }

    @Override
    public void confirmNewDelivery(Controler controler, Window window) {
    }

    @Override
    public void cancelNewDelivery(Controler controler, Window window) {
    }

    @Override
    public void removeDelivery(Controler controler, Window window) {
    }

    @Override
    public void moveDelivery(Controler controler, Window window) {
    }

    @Override
    public void openParameters(Controler controler, Window window) {
    }

    @Override
    public void returnToState(Controler controler, Window window, State returnState) {
    }

    @Override
    public void clickedNearIntersection(Controler controler, Window window, Intersection closestIntersection) {
    }

    @Override
    public void clickedNearSection(Controler controler, Window window, Section section) {
    }

    @Override
    public String stateToString() {
	return "defaultState";
    }

}
