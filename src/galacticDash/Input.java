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
    public boolean jump, left, right;//arrow keys and WAD for movement, and space 
    public boolean menu;//'M' key for menu during game
    public boolean pause;//esc key for pausing during game
    public boolean resume;//'R' key for resuming game

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();//key clicked

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)//left
            left = true;

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)//right
            right = true;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE)//jump
            jump = true;
        
        if (key == KeyEvent.VK_M)
            menu = true;
        
        if (key == KeyEvent.VK_ESCAPE)
            pause = true;
    }//end of key pressed

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)//left
            left = false;

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)//right
            right = false;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE)//jump
            jump = false;
        
        if (key == KeyEvent.VK_M)
            menu = false;
        
        if (key == KeyEvent.VK_ESCAPE)
            pause = false;
    }//end of key released

    public void keyTyped(KeyEvent e) {}//temp, required by interface
    
    public void reset() {
        left = false;
        right = false;
        jump = false;
        pause = false;
        menu = false;
    }//end of reset

}//end of class