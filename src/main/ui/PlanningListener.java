package main.ui;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.controler.Controler;
import main.model.Intersection;
import main.model.ModelInterface;

public class PlanningListener implements ListSelectionListener {

     private JTable planning;
     private Controler controler;
     private Window window;

     public PlanningListener(JTable t, Controler c, Window window) {
	  this.planning = t;
	  this.controler = c;
	  this.window = window;
     }

     @Override
     public void valueChanged(ListSelectionEvent e) {
	  System.out.println(e.getSource());

	  if (planning.getSelectedRow() == -1) {
	       // no selected row, ignore
	       return;
	  }
	  String address = (String) planning.getValueAt(planning.getSelectedRow(), 1);

	  String[] fragments = address.substring(1, address.length() - 1).split("; ");
	  double lat = Double.parseDouble(fragments[0]);
	  double lon = Double.parseDouble(fragments[1]);

	  Intersection closest = ModelInterface.findClosestIntersection(lat, lon);

	  controler.setSelectedIntersection(closest);
	  window.highlightSelectedIntersection(closest);
     }

}
