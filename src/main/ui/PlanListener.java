package main.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.model.ModelInterface;
import main.ui.PlanView.GeographicCoordinate;
import main.ui.PlanView.ScreenCoordinate;

public class PlanListener extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		ScreenCoordinate screenCoordinate = new ScreenCoordinate((int) e.getPoint().getX(), (int) e.getPoint().getY());
		GeographicCoordinate geographicCoordinate = ((PlanView) e.getSource()).convertToGeographicCoordinate(screenCoordinate);

		System.out.println("Clicked at pos : (" + geographicCoordinate.latitude + "; " + geographicCoordinate.longitude + ")");
		ModelInterface.getPlan().findClosestIntersection(geographicCoordinate.latitude, geographicCoordinate.longitude);
	}
}