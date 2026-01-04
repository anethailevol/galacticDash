package galacticDash;

import java.awt.*;
import javax.swing.ImageIcon;

public class Alien {
    public int x, y, width, height;
    private Image img;
    private int speed;

    public Alien(int x, int y, int speed) {
        this.img = new ImageIcon("assets/images/alien.gif").getImage();
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

        if (this.width <= 0) {
            this.width = 64;
            this.height = 64;
        }
    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}