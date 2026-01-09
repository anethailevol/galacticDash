package galacticDash;
import java.awt.*;
import javax.swing.ImageIcon;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Extra Life
 */

public class ExtraLife {
    public int x, y, width, height;//coordinates and dimensions
    private Image img;//image
    private int speed;//speed

    //constructor
    public ExtraLife(int x, int y, int speed) {
        this.img = new ImageIcon("assets/images/heart.png").getImage();
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = 32;
        this.height = 32;
    }

    /* PURPOSE: to update speed of extra life
	 * PRE: n/a
	 * POST: n/a
	 */
    public void update() {
        x -= speed;
    }

    /* PURPOSE: to draw extra life
	 * PRE: Graphics g
	 * POST: n/a
	 */
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    /* PURPOSE: to return bounds of extra life
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /* PURPOSE: to return extra life off screen
   	 * PRE: n/a
   	 * POST: return when extra life is not on screen
   	 */
    public boolean isOffScreen() {
        return x + width < 0;
    }
}