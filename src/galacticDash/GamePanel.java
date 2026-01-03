package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

	//elapsed time (for display)
	private int elapsedTime = 0;//in seconds
	private Timer timeElapsedTimer;

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

		player = new Player(200, 510);

		timer = new Timer(15, e ->{//game loop using swing timer
			update();
			repaint();
		});

		timeElapsedTimer = new Timer(1000, e ->{//for displayed timer
			elapsedTime++;//increases by 1 per second
		});

		//Load Background
		bg1 = new ImageIcon("assets/images/background1.gif").getImage();
		bg2 = new ImageIcon("assets/images/background2.gif").getImage();
		bgX2 = bg1.getWidth(null); 

		//load platforms
		longPlatform = new ImageIcon("assets/images/longPlatform.png").getImage();
		tallPlatform = new ImageIcon("assets/images/tallPlatform.png").getImage();

		//add platforms
		platforms.add(new Platform(0, 638, 400, 180, longPlatform));
		platforms.add(new Platform(398, 638, 400, 180, longPlatform));
		platforms.add(new Platform(796, 638, 400, 180, longPlatform));
		platforms.add(new Platform(1194, 638, 400, 180, longPlatform));

		platforms.add(new Platform(1720, 500, 150, 160, tallPlatform));
		platforms.add(new Platform(2200, 500, 150, 160, tallPlatform));

		platforms.add(new Platform(2560, 528, 400, 180, longPlatform));
		platforms.add(new Platform(2958, 528, 400, 180, longPlatform));

		platforms.add(new Platform(3658, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4056, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4454, 638, 400, 180, longPlatform));
		
		platforms.add(new Platform(5000, 450, 150, 160, tallPlatform));
		platforms.add(new Platform(5500, 600, 150, 160, tallPlatform));
		
		platforms.add(new Platform(5800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6198, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6596, 638, 400, 180, longPlatform));
	}

	public void startGame() {
		timer.start();//game timer
		timeElapsedTimer.start();//elapsed timer (display)
	}

	public void endGame() {
		timer.stop();
		timeElapsedTimer.stop();
		window.showScreen("start");
	}

	public void update() {
		if (input.menu){//if menu clicked
			endGame();
			return;//stop updating frame
		}

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

		int previousY = player.y;//previous y value

		//gravity
		player.velocity += 1;//gravity amount
		player.y += player.velocity;//apply velocity to player

		player.onGround = false;//asume sprite falling
		boolean voided = false;
		
		//PLATFORM COLLISION
		for (Platform p : platforms){
			Rectangle playerBounds = player.getBounds();
			Rectangle platformBounds = p.getBounds();

			if (playerBounds.intersects(platformBounds)){//if the player and platform overlap
				
				if (player.velocity > 0){//only works if player falling
					int platformTop = p.y;
					boolean abovePlatform = previousY + player.height <= platformTop;//if player above platform
					boolean feetOnPlatform = player.y + player.height >= platformTop;//if playet feet reaches/passes top

					if (abovePlatform && feetOnPlatform){//only works if both are true
						player.y = platformTop - player.height;//player goes to top of platform
						player.velocity = 0;
						player.onGround = true;
					}
				}
			}
		}

		//TEMP--> changing ground level to 800 for void
		int groundY = 800;
		if (!player.onGround){
		    int feet = player.y + player.height;//sprite bottom
		    if (feet >= groundY){//checking if sprite is on ground
		        player.y = groundY - player.height;
		        player.velocity = 0;
		        player.onGround = true;
		        voided = true;
		    }
		}

		if (player.x <=-130){//if player touches very left
			voided = true;
		}
		
		if (voided == true){//if player touches bottom
			endGame();
			window.showScreen("gameOver");
			return;
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

	}//end of update

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font pixelFont;//main game font

		//SET FONT
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);
		} 

		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}

		//background
		g.drawImage(bg1, bgX1, 0, null);
		g.drawImage(bg2, bgX2, 0, null);

		//platforms
		for (Platform p : platforms){
			p.draw(g);
		}

		//player
		player.draw(g);

		g.setColor(Color.WHITE);
		g.setFont(pixelFont.deriveFont(Font.PLAIN, 15f));
		//converting into minutes and seconds
		int minutes = elapsedTime/60;
		int seconds = elapsedTime%60;
		String timeAnalog = String.format("%02d:%02d", minutes, seconds);
		g.drawString("Time: " + timeAnalog, 10, 30);
		g.drawString("Click 'M' for menu", 1200, 30);

	}

}//end of class
