package main.ui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ToolTipManager;

import main.model.Intersection;
import main.model.Section;

/**
	 * Wrapper class representing a tuple x/y, in the screen coordinate system
	 * Screen coordinate are "normalized" (taking into account boundaries of map
	 * view, etc.)
	 */
	/*
	 * TODO : FlyWeight for this class ?
	 */
public class ScreenCoordinate {
		int x;
		int y;

		public ScreenCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
}
