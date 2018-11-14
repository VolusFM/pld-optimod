package main.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;

/**
 * Listener catching the events that appends on the PlanView.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class PlanListener extends MouseAdapter {

    private static int LEFT_CLICK = MouseEvent.BUTTON1;

    Controler controler;

    /**
     * Create a plan listener.
     * 
     * @param controler is the application's controller.
     */
    public PlanListener(Controler controler) {
	this.controler = controler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	ScreenCoordinate screenCoordinate = new ScreenCoordinate((int) e.getPoint().getX(), (int) e.getPoint().getY());
	GeographicCoordinate geographicCoordinate = ((PlanView) e.getSource())
		.convertToGeographicCoordinate(screenCoordinate);
	Intersection closestIntersection = ModelInterface.findClosestIntersection(geographicCoordinate.latitude,
		geographicCoordinate.longitude);
	if (e.getButton() == LEFT_CLICK) {
	    this.controler.clickedNearIntersection(closestIntersection);
	    this.controler.clickedNearSection(
		    ModelInterface.findClosestSection(geographicCoordinate.latitude, geographicCoordinate.longitude));
	    /*
	     * Write the coordinates information into the adding delivery form
	     */
	    if (Window.getPlanningPanel().getAddingPanel() != null) {
		Window.getPlanningPanel().getAddingPanel().latitudeField.setText("" + closestIntersection.getLat());
		Window.getPlanningPanel().getAddingPanel().longitudeField.setText("" + closestIntersection.getLon());
	    }
	}
	this.controler.getWindow().forceFocusOnPlanView();
    }
}
