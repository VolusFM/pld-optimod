package ui;

import java.util.Iterator;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.Intersection;

public class PlanView extends JPanel {
	
	private int scale;
	private int heightView ; 
	private int widthView;
	//private Plan plan;		// Point entrée modèle observable 
								// TODO : mettre le bon type
	private Graphics graphics;
	
	/**
	 * Create the graphical view for drawing the charged plan
	 * with the scale s in the specified window w.
	 * @param s the scale
	 * @param w the window
	 */
	public PlanView(int s, Window w){
		super();
		this.scale = s;
		setLayout(null);
		setBackground(Color.white);
		w.getContentPane().add(this);
	}
	// TODO : ajouter le modele observable comme paramètre 
	// pour reccupérer les dimensions, mettre l'observer et 
	// le sauver en attribut de la vue.
	
	/**
	 * Function called any time the view must be redraw.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.lightGray);
		for (int y=0; y<widthView/scale; y++)
			g.drawLine(y*scale, 0, y*scale, heightView);
		for (int x=0; x<heightView/scale; x++)
			g.drawLine(0, x*scale, widthView, x*scale);
		g.setColor(Color.gray);
		g.drawRect(0, 0, widthView, heightView);
		this.graphics = g;
//		Iterator<Intersection> it = plan.getIteratorIntersections();
//		while (it.hasNext())
//			it.next().print(this);

	}
	
	
}
