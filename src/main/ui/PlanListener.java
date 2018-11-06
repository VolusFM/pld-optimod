package main.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.controler.Controler;
import main.model.ModelInterface;
import main.ui.PlanView.GeographicCoordinate;
import main.ui.PlanView.ScreenCoordinate;

public class PlanListener extends MouseAdapter {

	Controler controler;
	
	
	
	public PlanListener(Controler controler) {
		this.controler = controler;
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		ScreenCoordinate screenCoordinate = new ScreenCoordinate((int) e.getPoint().getX(), (int) e.getPoint().getY());
		GeographicCoordinate geographicCoordinate = ((PlanView) e.getSource()).convertToGeographicCoordinate(screenCoordinate);

		System.out.println("Clicked at pos : (" + geographicCoordinate.latitude + "; " + geographicCoordinate.longitude + ")");
		controler.clickedNearIntersection(ModelInterface.findClosestIntersection(geographicCoordinate.latitude, geographicCoordinate.longitude));
	}
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println(e);
	}
}
