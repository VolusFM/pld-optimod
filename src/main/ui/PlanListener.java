package main.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;
import main.ui.GeographicCoordinate;
import main.ui.ScreenCoordinate;

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
		
		Intersection closest = ModelInterface.findClosestIntersection(geographicCoordinate.latitude, geographicCoordinate.longitude);

		controler.clickedNearIntersection(closest);		
		
		controler.clickedNearSection(ModelInterface.findClosestSection(geographicCoordinate.latitude, geographicCoordinate.longitude));
		
		if (controler.window.planningPanel.addingPanel != null){
			controler.window.planningPanel.addingPanel.latitudeField.setText(""+closest.getLat());
			controler.window.planningPanel.addingPanel.longitudeField.setText(""+closest.getLon());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// System.out.println(e);
	}
}
