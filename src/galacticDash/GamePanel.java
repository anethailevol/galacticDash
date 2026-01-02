package galacticDash;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

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
	private Timer timer;

	//Scrolling
	private Image bg1, bg2;
	private int bgX1 = 0;
	private int bgX2;
	private int scrollSpeed = 4;

	//platforms
	private Image longPlatform, tallPlatform;
	private ArrayList<Platform> platforms = new ArrayList<>();

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		setBackground(Color.BLACK);//temp colour
		setLayout(null);
		setFocusable(true);
		setDoubleBuffered(true);

		input = new Input();
		addKeyListener(input);
		setFocusable(true);
		requestFocusInWindow();

		player = new Player(200, 500);

		timer = new Timer(15, e ->{//game loop using swing timer
			update();
			repaint();
		});

		//Load Background
		bg1 = new ImageIcon("assets/images/background1.gif").getImage();
		bg2 = new ImageIcon("assets/images/background2.gif").getImage();
		bgX2 = bg1.getWidth(null); 

		//load platforms
		longPlatform = new ImageIcon("assets/images/longPlatform.png").getImage();
		tallPlatform = new ImageIcon("assets/images/tallPlatform.png").getImage();

		platforms.add(new Platform(0, 628, 400, 180, longPlatform));
		platforms.add(new Platform(398, 628, 400, 180, longPlatform));
		platforms.add(new Platform(398, 628, 400, 180, longPlatform));
		platforms.add(new Platform(796, 628, 400, 180, longPlatform));
		platforms.add(new Platform(1194, 628, 400, 180, longPlatform));

		platforms.add(new Platform(1720, 500, 250, 160, tallPlatform));
	}

	public void startGame() {
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

		//Scrolling mechanism
		bgX1 -= scrollSpeed;
		bgX2 -= scrollSpeed;

		//platform scroll
		for (Platform p : platforms){
			p.x -= scrollSpeed;
		}

		// If bg1 goes off screen, move it to the right of bg2
		if (bgX1 + bg1.getWidth(null) <= 0) {
			bgX1 = bgX2 + bg2.getWidth(null);
		}

		// If bg2 goes off screen, move it to the right of bg1
		if (bgX2 + bg2.getWidth(null) <= 0) {
			bgX2 = bgX1 + bg1.getWidth(null);
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//background
		g.drawImage(bg1, bgX1, 0, null);
		g.drawImage(bg2, bgX2, 0, null);

		//platforms
		for (Platform p : platforms){
			p.draw(g);
		}

		//player
		player.draw(g);

	}

}//end of class
