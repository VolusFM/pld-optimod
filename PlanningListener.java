package main.ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import main.ui.Window;

public class PlanningListener extends MouseAdapter {
	
	private JTable planning;
	
	public PlanningListener (JTable t){
		super();
		this.planning = t;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()== 2 ){
			Point p = e.getPoint();
			int row = planning.rowAtPoint(p);
			System.out.println("N° ligne cliquée : " + row);
			//TODO : controleur.modifLigne(row) qui ouvre une range selector (1 à nbr livreurs)
			// et change le delivery man de la ligne avec le retour de la range selector
		}		
	}

}
