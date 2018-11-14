package main.ui;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;

/**
 * Listener catching the events that appends on the PlanningTable.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER Léo, THOLOT Cassandre
 */
public class PlanningListener implements ListSelectionListener {

     private JTable planning;
     private Controler controler;

     /**
      * Create a planning listener for the specified planning and controller.
     * @param t the specified JTable planning.
     * @param c the specified controller.
     */
    public PlanningListener(JTable t, Controler c) {
	  this.planning = t;
	  this.controler = c;
     }

     @Override
     public void valueChanged(ListSelectionEvent e) {
	 /* No row selected */
	  if (planning.getSelectedRow() == -1) {
	       return;
	  }
	  /* Values selected */
	  String address = (String) planning.getValueAt(planning.getSelectedRow(), 1);
	  String[] fragments = address.substring(1, address.length() - 1).split("; ");
	  double lat = Double.parseDouble(fragments[0]);
	  double lon = Double.parseDouble(fragments[1]);
	  Intersection closest = ModelInterface.findClosestIntersection(lat, lon);
	  /* Set selection */
	  controler.setSelectedIntersection(closest);
     }

}
