package controler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Interface;
import model.Plan;
import xml.XMLException;

public class Controler {
	private State currentState;
	private Window window;
	private Interface model;
	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();
	
	public Controler(Interface model, Window window){
		this.currentState = initState;
		this.model = model;
		this.window = window;	
	}
	
	protected void openPlan()throws XMLException, ParserConfigurationException, SAXException, IOException{
		//TODO : try/catch
		currentState.openPlan(this);
	}
	protected void openDeliveries()throws XMLException, ParserConfigurationException, SAXException, IOException{
		currentState.openDeliveries(this);
	}
	
	public Interface getModel() {
		return model;
	}
	
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
}
