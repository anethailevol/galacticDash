package galacticDash;
import java.awt.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - UFO Object
 */

public class UFO {
    public int x, y, width, height;//coordinates and dimensions
    public Image img;//ufo image
    
    //constructor
    public UFO(int x, int y, int width, int height, Image img) {
    	//coordinates, dimensions, and image
        this.x = x;
        this.y = y;
        this.img = img;
        this.width = width;
        this.height = height;
    }//end of constructor

    /* PURPOSE: to return bounds of UFO
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }//end of bounds

    /* PURPOSE: to draw UFO
	 * PRE: Graphics g
	 * POST: n/a
	 */
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }//end of draw
    
}//end of class

