package main.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;
import main.ui.GeographicCoordinate;
import main.ui.ScreenCoordinate;

public class PlanListener extends MouseAdapter {

    private static int LEFT_CLICK = MouseEvent.BUTTON1;
    private static int RIGHT_CLICK = MouseEvent.BUTTON3;

    Controler controler;

    public PlanListener(Controler controler) {
	this.controler = controler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
		ScreenCoordinate screenCoordinate = new ScreenCoordinate((int) e.getPoint().getX(), (int) e.getPoint().getY());
		GeographicCoordinate geographicCoordinate = ((PlanView) e.getSource()).convertToGeographicCoordinate(screenCoordinate);
	
		Intersection closestIntersection = ModelInterface.findClosestIntersection(geographicCoordinate.latitude,
			geographicCoordinate.longitude);		
	
		if (e.getButton() == LEFT_CLICK) {
		    controler.clickedNearIntersection(closestIntersection);
		    controler.clickedNearSection(
			    ModelInterface.findClosestSection(geographicCoordinate.latitude, geographicCoordinate.longitude));		    
		    /* Write the coordinates information into the adding delivery form */ 
			if (controler.getWindow().planningPanel.addingPanel != null){
				controler.getWindow().planningPanel.addingPanel.latitudeField.setText(""+closestIntersection.getLat());
				controler.getWindow().planningPanel.addingPanel.longitudeField.setText(""+closestIntersection.getLon());
			}
		}
		
		if (e.getButton() == RIGHT_CLICK) {
		    controler.rightClickedNearIntersection(closestIntersection);
		}
	
		// XXX
		controler.getWindow().forceFocusOnPlanView();
    }
}
