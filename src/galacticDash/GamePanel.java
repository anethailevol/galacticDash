package galacticDash;
import java.awt.*;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Main Game Panel
 */

public class GamePanel extends JPanel {
	private GameWindow window;
	private Input input;
	private Player player;   

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		setBackground(Color.LIGHT_GRAY);//temp colour
		setLayout(null);
		setFocusable(true);
		
		input = new Input();
	    addKeyListener(input);
	    setFocusable(true);

	    player = new Player(200, 300);
	    
	    repaint();
	    
	}
	
	public void update() {

	    if (input.left) {
	        player.x -= 5;
	    }

	    if (input.right) {
	        player.x += 5;
	    }

	    if (input.jump) {
	        player.y -= 10; // or call player.jump()
	    }
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    player.draw(g);
	}



}//end of class
