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
	ImageIcon gameBackground;

    private int cameraX = 0;//camera for scrolling bg
    private final int worldWidth = 3000;//temp 3000px wide scrolling

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		gameBackground = new ImageIcon("assets/images/gamebgNEW.gif");
		setLayout(null);
		setFocusable(true);
		input = new Input();
		addKeyListener(input);
		setFocusable(true);
		requestFocusInWindow();

		player = new Player(200, 300);
		Timer timer = new Timer(15, e ->{//game loop using swing timer
			update(1500, 800);
			repaint();
		});
		timer.start();
	}

	/*purpose: to update the game
	 * pre: int panelWidth, panelHeight - panel dimensions
	 * post: n/a
	 */
	public void update(int panelWidth, int panelHeight) {
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

		//normal boundaries
		if (player.x < 0) player.x = 0;//left
		if (player.y < 0) player.y = 0;//top
		if (player.y > panelHeight - 120) player.y = panelHeight - 120;//bottom, temp

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
		
		//camera scrolling
        if (player.x > panelWidth/2 && cameraX < (worldWidth - panelWidth)){//scroll left
            cameraX += 5;
            player.x = panelWidth/2;//player in center
        }
        
        if (player.x < panelWidth/2 && cameraX > 0){//scroll right
            cameraX -= 5;
            player.x = panelWidth/2;//player in center
        }
        
        if (player.x + cameraX > worldWidth - 120){//max right boundary
        	player.x = worldWidth - 120 - cameraX;
        }
	
	}//end of update

	/*purpose: to paint the graphics
	 * pre: Graphics g
	 * post: n/a
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D comp2D = (Graphics2D) g;
		 comp2D.drawImage(gameBackground.getImage(), -cameraX, 0, worldWidth, getHeight(), this);//draw bg
		player.draw(g);
	}

}//end of class
