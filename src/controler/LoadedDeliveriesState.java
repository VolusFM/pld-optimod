package controler;

public class LoadedDeliveriesState extends DefaultState{
	
	@Override
	public void openParameters(Controler controler, Window window){
		//TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
	
	@Override
	public void calculateTour(Controler controler, Window window){
		//TODO: add code to call tourCalculator
		controler.setCurrentState(controler.planningState);
	}
}
