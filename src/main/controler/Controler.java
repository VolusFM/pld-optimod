package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.ui.Window;
import main.xml.XMLException;

public class Controler {

	private State currentState;
	public Window window; // FIXME visibility
	private ModelInterface model;

	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();

	public Controler() {
		this.currentState = initState;
		this.window = new Window(this);
	}

	public void openPlan() /*
							 * throws XMLException, ParserConfigurationException,
							 * SAXException, IOException
							 */ {
		try {
			currentState.openPlan(this, window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openDeliveries() throws XMLException, ParserConfigurationException, SAXException, IOException {
		currentState.openDeliveries(this, window);
	}

	public void openParameters() {
		currentState.openParameters(this, window);
	}

	public ModelInterface getModel() {
		return model;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

}
