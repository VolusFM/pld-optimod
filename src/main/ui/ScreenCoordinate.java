package main.ui;

/**
 * Wrapper class representing a tuple x/y, in the screen coordinate system
 * Screen coordinate are "normalized" (taking into account boundaries of map
 * view, etc.)
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class ScreenCoordinate {

    int x;
    int y;

    /**
     * Create screen coordinates corresponding at the specified point.
     * 
     * @param x the x coordinate of the specified point.
     * @param y the y coordinate of the specified point.
     */
    public ScreenCoordinate(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Getter of the x attribute.
     * 
     * @return Integer, the x coordinate.
     */
    public int getX() {
	return this.x;
    }

    /**
     * Setter of the x attribute.
     * 
     * @param x is the new x coordinate to set.
     */
    public void setX(int x) {
	this.x = x;
    }

    /**
     * Getter of the y attribute.
     * 
     * @return Integer, the y coordinate.
     */
    public int getY() {
	return this.y;
    }

    /**
     * Setter of the y attribute.
     * 
     * @param y is the new y coordinate to set.
     */
    public void setY(int y) {
	this.y = y;
    }
}
