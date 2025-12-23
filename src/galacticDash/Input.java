package galacticDash;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Input Handler
 */

public class Input implements KeyListener {
    public boolean up, down, left, right;//arrow keys and WASD for movement

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();//key clicked

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)//left
            left = true;

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)//right
            right = true;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)//up
            up = true;

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)//down
            down = true;
    }//end of key pressed

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)//left
            left = false;

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)//right
            right = false;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)//up
            up = false;

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)//down
            down = false;
    }//end of key released

    public void keyTyped(KeyEvent e) {}//temp, required by interface
    
}//end of class