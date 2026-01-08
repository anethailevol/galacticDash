package galacticDash;
import java.awt.*;
import javax.swing.ImageIcon;

public class Alien {
    public int x, y, width, height;//coordinates and dimensions
    private Image img;//alien image
    private int speed;//alien speed

    //constructor
    public Alien(int x, int y, int speed) {
        this.img = new ImageIcon("assets/images/alien.gif").getImage();
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = 100;
        this.height = 150;
    }

    /* PURPOSE: to update speed of alien
	 * PRE: n/a
	 * POST: n/a
	 */
    public void update() {
        x -= speed;
    }

    /* PURPOSE: to draw alien
	 * PRE: Graphics g
	 * POST: n/a
	 */
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    /* PURPOSE: to return bounds of alien
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /* PURPOSE: to return alien off screen
   	 * PRE: n/a
   	 * POST: return when alien is not on screen
   	 */
    public boolean isOffScreen() {
        return x + width < 0;
    }
}