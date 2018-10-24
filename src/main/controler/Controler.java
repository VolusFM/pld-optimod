package main.controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.model.ModelInterface;
import main.xml.XMLException;
import main.ui.Window;

public class Controler {
	private State currentState;
	private Window window;
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

	public void openPlan() /*throws XMLException, ParserConfigurationException, SAXException, IOException */{
		try{
			currentState.openPlan(this, window);
		}catch (Exception e){
			
		}
	}

	public void openDeliveries() throws XMLException, ParserConfigurationException, SAXException, IOException {
		currentState.openDeliveries(this);
	}

	public ModelInterface getModel() {
		return model;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

}
