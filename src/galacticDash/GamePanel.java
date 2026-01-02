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
		setOpaque(false);//to ensure transparent sprite
		
		input = new Input();
	    addKeyListener(input);
	    setFocusable(true);
	    requestFocusInWindow();

	    player = new Player(200, 300);
	    Timer timer = new Timer(30, e ->{//game loop using swing timer
            update();
            repaint();
        });
        timer.start();
    }

	
	public void update() {

	    if (input.left) {
	        player.x -= 5;
	        player.setAction("run");
	    }

	    else if (input.right) {
	        player.x += 5;
	        player.setAction("run");
	    }
	    else{
	    	player.setAction("idle");
	    }

	    if (input.jump) {
	        player.jump();//calling jump
	        player.setAction("jump");
	    }

	    
	    //gravity
	    player.velocity += 1;//gravity amount
	    player.y += player.velocity;//apply velocity to player

	    if (player.y >= player.groundY){//checking is player on ground
	        player.y = player.groundY;
	        player.velocity = 0;
	        player.onGround = true;
	        if (!input.left && !input.right){
	            player.setAction("idle");
	        }
	    }

	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    player.draw(g);
	}

}//end of class
