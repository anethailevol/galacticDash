package galacticDash;
import java.awt.*;
import javax.swing.ImageIcon;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Asteroid
 */

public class Asteroid {
    public int x, y, width, height;
    private Image img;
    private int speed;

    public Asteroid(int x, int y, int speed) {
        this.img = new ImageIcon("assets/images/asteroid.gif").getImage();
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = 50;
        this.height = 50;
    }
    
    /* PURPOSE: to update speed of asteroid
	 * PRE: n/a
	 * POST: n/a
	 */
    public void update() {
        x -= speed;
    }
    
    /* PURPOSE: to draw asteroid
	 * PRE: Graphics g
	 * POST: n/a
	 */
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
    
    /* PURPOSE: to return bounds of asteroid
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    /* PURPOSE: to return asteroid off screen
   	 * PRE: n/a
   	 * POST: return when asteroid is not on screen
   	 */
    public boolean isOffScreen() {
        return x + width < 0;
    }
}
