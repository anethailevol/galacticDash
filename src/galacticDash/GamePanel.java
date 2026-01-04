package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	private Timer timer;
	private boolean paused = false;
	private boolean prevPaused = false;
	private boolean prevM = false;
	
	// aliens + hearts
	private ArrayList<Alien> aliens = new ArrayList<>();
	private int hearts = 3;
	private int alienSpawnRate = 0;   // frames between spawns
	private int alienSpawnTimer = 0;  // counts frames
	
	//level
	private int currentLevel = 1;
	
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

	//ending ufo
	private UFO ufo;
	
	public void loadLevel(int level) {
		this.currentLevel = level;
		
		platforms.clear();
		aliens.clear();
		hearts = 3;
		
		if (level == 1) {
	        loadLevel1();
	        scrollSpeed = 6;
	        alienSpawnRate = 0; //no aliens
	    }

	    if (level == 2) {
	        loadLevel2();
	        scrollSpeed = 10;      // faster scroll
	        alienSpawnRate = 200; // 5 seconds at 15ms per frame
	    }

	    if (level == 3) {
	        loadLevel3();
	        scrollSpeed = 15;
	        alienSpawnRate = 100; // aliens every ~3 seconds
	    }
	}

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
			if(!paused)
				elapsedTime++;//increases by 1 per second
		});

		//Load Background
		bg1 = new ImageIcon("assets/images/background1.gif").getImage();
		bg2 = new ImageIcon("assets/images/background2.gif").getImage();
		bgX2 = bg1.getWidth(null); 

		//load platforms
		longPlatform = new ImageIcon("assets/images/longPlatform.png").getImage();
		tallPlatform = new ImageIcon("assets/images/tallPlatform.png").getImage();

		//load ufo
		Image ufoImg = new ImageIcon("assets/images/ufo.png").getImage();
		ufo = new UFO(6710, 460, 260, 190, ufoImg);
		
		//load alien
		
		loadLevel(1);
	}

	public int getElapsedTime() {
		return elapsedTime;//return elapsed time
	}

	public void startGame() {
		resetGame();
		loadLevel(currentLevel);
		timer.start();//game timer
		timeElapsedTimer.start();//elapsed timer (display)
		requestFocusInWindow();
	}

	public void endGame(String screen) {
		timer.stop();
		timeElapsedTimer.stop();
		window.showScreen(screen);

		//reset all inputs
		input.reset();
		paused = false;
		prevPaused = false;
		prevM = false;

	}

	public void resetGame() {
		//reset player positions, etc.
		player.x = 200;
		player.y = 510;
		player.velocity = 0;
		player.onGround = false;

		//reset inputs
		paused = false;
		prevPaused = false;
		prevM = false;

		//reset scrolling
		bgX1 = 0;
		bgX2 = bg1.getWidth(null);

		//resetting platforms
		platforms.clear();
		addPlatforms();

		//reset ufo
		ufo.x = 6710;
		ufo.y = 460;

		//reset timer
		elapsedTime = 0;
		timeElapsedTimer.stop();
		timer.stop();
		
		//reset aliens + heart
		aliens.clear();
		hearts = 3;
	}//end of reset


	public void update() {
		if (input.menu){//if menu clicked
			endGame("start");
			return;//stop updating frame
		}

		if (input.pause && !prevPaused){//if pause is clicked
			paused = !paused;//pause/resume
		}
		prevPaused = input.pause;

		// If paused, freeze game
		if (paused){

			if (input.menu && !prevM){//go to menu
				window.showScreen("start");
			}
			prevM = input.menu;

			return;//stops game
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

		Rectangle playerBounds = player.getBounds();//player boundaries
		Rectangle ufoBounds = ufo.getBounds();//ufo boundaries

		//WINNING GAME
		if (playerBounds.intersects(ufoBounds)){//player touches ufo
			endGame("gameWin");
			return;
		}//end of win

		//PLATFORM COLLISION
		for (Platform p : platforms){
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

		//void detection (game over)
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

		if (player.x <=-150){//if player is too far into space on left
			voided = true;
		}

		if (voided == true) {//if player touches bottom
			endGame("gameOver");
			return;
		}

		// alien spawn: only when a positive spawn rate is set for the level
		if (alienSpawnRate > 0) {
			alienSpawnTimer++;
			if (alienSpawnTimer >= alienSpawnRate) {
				spawnAlien();
				alienSpawnTimer = 0;
			}
		}
		
		// update aliens
		for (int i = aliens.size() - 1; i >= 0; i--) {
		    Alien a = aliens.get(i);
		    a.x -= scrollSpeed; //move to left

		    if (a.x + a.width < 0) {
		        aliens.remove(i);
		        continue;
		    }

		    if (a.getBounds().intersects(player.getBounds())) {
		        hearts--;
		        aliens.remove(i);

		        if (hearts <= 0) {
		            endGame("gameOver");
		            return;
		        }
		    }
		}

		//Scrolling mechanism
		bgX1 -= scrollSpeed;
		bgX2 -= scrollSpeed;

		// If bg1 goes off screen, move it to the right of bg2
		if (bgX1 + bg1.getWidth(null) <= 0) {
			bgX1 = bgX2 + bg2.getWidth(null);
		}

		// If bg2 goes off screen, move it to the right of bg1
		if (bgX2 + bg2.getWidth(null) <= 0) {
			bgX2 = bgX1 + bg1.getWidth(null);
		}

		//platform scroll
		for (Platform p : platforms){
			p.x -= scrollSpeed;
		}

		//ufo scroll
		ufo.x -= scrollSpeed;
		

	}//end of update
	
	private void spawnAlien() {
	    int startX = (getWidth() > 0) ? getWidth() + 200 : 1500;
	    int startY = 400;
	    aliens.add(new Alien(startX, startY, 6));
	}
	
	private void loadLevel1() {
	    addPlatforms(); // custom level 1 layout
	}

	private void loadLevel2() {
	    addPlatforms(); // replace with level 2 layout
	}

	private void loadLevel3() {
	    addPlatforms(); // replace with level 3 layout
	    aliens.add(new Alien(2000, 500, 6));
	}
	
	public void addPlatforms() {
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

		platforms.add(new Platform(5000, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(5500, 600, 150, 160, tallPlatform));

		platforms.add(new Platform(5800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6198, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6596, 638, 400, 180, longPlatform));
	}
	

	//PAINT COMPONENT
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

		//ufo
		ufo.draw(g);

		//player
		player.draw(g);
		
		// draw aliens
		for (Alien a : aliens) {
		    a.draw(g);
		}
		
		// draw hearts
		for (int i = 0; i < 3; i++) {
		    if (i < hearts)
		        g.drawImage(new ImageIcon("assets/images/heart.png").getImage(), 20 + i * 40, 60, 32, 32, null);
		    else
		        g.drawImage(new ImageIcon("assets/images/deadheart.png").getImage(), 20 + i * 40, 60, 32, 32, null);
		}

		g.setColor(Color.WHITE);
		g.setFont(pixelFont.deriveFont(Font.PLAIN, 15f));
		//converting into minutes and seconds
		int minutes = elapsedTime/60;
		int seconds = elapsedTime%60;
		String timeAnalog = String.format("%02d:%02d", minutes, seconds);
		g.drawString("Time: " + timeAnalog, 10, 30);
		g.drawString("'M' for Menu | 'ESC' to Pause", 1020, 30);

		g.drawString("LEVEL " + currentLevel, 500, 30);//current level

		if (paused){//paused game box
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(490, 200, 500, 200);
			g.setColor(Color.WHITE);
			g.setFont(pixelFont.deriveFont(40f));
			g.drawString("PAUSED", 620, 270);
			g.setFont(pixelFont.deriveFont(20f));
			g.drawString("Press 'ESC' to Resume", 530, 320);
			g.drawString("Press 'M' for Menu", 530, 360);
		}//end of paused box

	}//end of paintComponent

}//end of class
