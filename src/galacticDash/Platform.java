package galacticDash;
import java.awt.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Platform
 */

public class Platform {
	public int x, y, width, height;
	public Image img;//platform image

	public Platform(int x, int y, int width, int height, Image img){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}//end of bounds

	public void draw(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}//end of draw

}//end of class
