package controler;

public class PlanningState extends DefaultState{
	
	@Override
	public void openParameters(Controler controler, Window window){
		//TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
	
	@Override
	public void addDelivery(Controler controler, Window window){
	}
}
