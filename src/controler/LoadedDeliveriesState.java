package controler;

public class LoadedDeliveriesState extends DefaultState{
	public void openParameters(Controler controler){
		//TODO : add code to open parameter window
		controler.setCurrentState(controler.parametersState);
	}
	public void calculateTour(Controler controler){
		//TODO: add code to call tourCalculator
		controler.setCurrentState(controler.planningState);
	}
}
