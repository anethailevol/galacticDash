package galacticDash;
import java.awt.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Platform Object
 */

public class Platform {
	public int x, y, width, height;
	public Image img;//platform image

	//constructor
	public Platform(int x, int y, int width, int height, Image img){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}//end of constructor

	/* PURPOSE: to return player bounds
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}//end of bounds

	/* PURPOSE: to draw playform graphics
	 * PRE: n/a
	 * POST: n/a
	 */
	public void draw(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}//end of draw

}//end of class
