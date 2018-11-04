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
//	private ModelInterface model;

	protected final InitState initState = new InitState();
	protected final LoadedPlanState loadedPlanState = new LoadedPlanState();
	protected final LoadedDeliveriesState loadedDeliveriesState = new LoadedDeliveriesState();
	protected final PlanningState planningState = new PlanningState();
	protected final ParametersState parametersState = new ParametersState();
	protected final AddDeliveryState addState = new AddDeliveryState();
	
	/**
	 * Create application s controler
	 * @param model, model package s entry point
	 * @param window
	 */
	public Controler(){
		this.currentState = initState;
//		this.model;
		this.window = new Window(this);	
	}
	
	/**
	 * Load the xml formatted plan. Called when the "accueil" screen s button "Valider" is pushed. 
	 */
	public void openPlan(){
		try{
			currentState.openPlan(this, window);
		}
		catch(XMLException xml){ 
			System.out.println(xml);
		}
		catch(ParserConfigurationException parserConfig){
			System.out.println(parserConfig);
		}
		catch(SAXException sax){
			System.out.println(sax);
		}
		catch(IOException io){
			System.out.println(io);
		}
	}
	
	/**
	 * Load the xml formatted delivery request. Called when the "plan" screen s button "Valider" is pushed. 
	 */
	public void openDeliveries(){
		try{
			currentState.openDeliveries(this, window);
		}
		catch(XMLException xml){ 
			System.out.println(xml);
		}
		catch(ParserConfigurationException parserConfig){
			System.out.println(parserConfig);
		}
		catch(SAXException sax){
			System.out.println(sax);
		}
		catch(IOException io){
			System.out.println(io);
		}
	}
	
	/**
	 * model s getter.
	 */
//	public ModelInterface getModel() {
//		return model;
//	}
	
	/**
	 * State s setter.
	 */
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	/**
	 * get the controler current state
	 * @return currentState
	 */
public State getCurrentState() {
	return currentState;
}

	public void openParameters() {
		// TODO Auto-generated method stub
		throw new RuntimeException("NYI");
	}
}
