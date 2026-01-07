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

	// asteroids (level 2 & 3)
	private ArrayList<Asteroid> asteroids = new ArrayList<>();
	private int asteroidSpawnRate = 0;
	private int asteroidSpawnTimer = 0;

	//level
	private int currentLevel = 1;

	//elapsed time (for display)
	private int elapsedTime = 0;//in seconds
	private Timer timeElapsedTimer;

	//Scrolling
	private Image bg;
	private int bgX = 0;
	private int scrollSpeed = 4;

	//platforms
	private Image longPlatform, tallPlatform;
	private ArrayList<Platform> platforms = new ArrayList<>();

	//checkpoints
	private Image cp1;
	private Image cp2;

	//ending ufo
	private UFO ufo;

	//checkpoint variables
	private int currentCP = 1;
	private int offset = 0;
	private int cPoint1X = 0;
	private int cPoint1Y = 500;
	private int cPoint2X;
	private int cPoint2Y = 500;

	public void loadLevel(int level) {
		this.currentLevel = level;

		platforms.clear();
		aliens.clear();
		hearts = 3;

		if (level == 1) {
			loadLevel1();
			scrollSpeed = 6;
			alienSpawnRate = 0; //no aliens
			asteroidSpawnRate = 0;//no asteroids
			//checkpoint 2
			cPoint2X = 3670;
			//Load Background
		}

		if (level == 2) {
			loadLevel2();
			scrollSpeed = 8;      
			alienSpawnRate = 0; 
			asteroidSpawnRate = 150;
			//checkpoint 2
			cPoint2X = 4800;
			//Load Background
		}

		if (level == 3) {
			loadLevel3();
			scrollSpeed = 15;
			alienSpawnRate = 150;
			asteroidSpawnRate = 120;
			//checkpoint 2
			cPoint2X = 4300;
			//Load Background
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
		
		//load background:
		bg = new ImageIcon("assets/images/gamebg.gif").getImage();


		//load platforms
		longPlatform = new ImageIcon("assets/images/longPlatform.png").getImage();
		tallPlatform = new ImageIcon("assets/images/tallPlatform.png").getImage();

		//load checkpoints
		cp1 = new ImageIcon("assets/images/cp1.png").getImage();
		cp2 = new ImageIcon("assets/images/cp2.png").getImage();

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
		window.setFinalHearts(hearts);
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
		bgX = 0;

		//resetting platforms
		platforms.clear();

		//reset checkpoints
		currentCP = 0;
		offset = 0;

		//reset ufo
		ufo.x = 13150;
		ufo.y = 460;

		//reset timer
		elapsedTime = 0;
		timeElapsedTimer.stop();
		timer.stop();

		//reset aliens + heart
		aliens.clear();
		//reset asteroids
		asteroids.clear();
		asteroidSpawnTimer = 0;
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
		
		int playerWorldX = player.x + offset;
		if (playerWorldX >= cPoint2X && currentCP < 2){//checkpoint 2 mark
			currentCP = 2;
		}
		
		if (player.x <=-150){//if player is too far into space on left
			voided = true;
		}
		
		//void detection ground
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

		//CHECKPOINT LOGIC W/ RESPAWNING
		if (voided == true){
		    hearts--;
		    
		    if (hearts <= 0){//no hearts left
		        endGame("gameOver");
		        return;
		    }
		    
		    //respawning based off camera
		    int respawnX = 0;
		    int respawnY = 500;
		    
		    if (currentCP == 1){//first checkpoint
		    	respawnX = cPoint1X;
		        respawnY = cPoint1Y;
		    }
		    else if (currentCP == 2){//second checkppoint
		    	respawnX = cPoint2X;
		        respawnY = cPoint2Y;
		    }

		    int oldOffset = offset;//reset offset
		    offset = respawnX;//new player spawning point
		    int shiftWorld = oldOffset - offset;//shifting world based off respawn and offset

		    //shifting objects back
		    for (Platform p : platforms){
		        p.x += shiftWorld;
		    }
		    ufo.x += shiftWorld;
		    bgX += shiftWorld;

		    //placing player back on the screen
		    player.x = respawnX - offset + 100;//bit more forward so not touching left
		    player.y = respawnY;
		    player.velocity = 0;
		    voided = false;
		    return;
		}//end of voiding

		// alien spawn
		if (alienSpawnRate > 0) {
			alienSpawnTimer++;
			if (alienSpawnTimer >= alienSpawnRate) {
				spawnAlien();
				alienSpawnTimer = 0;
			}
		}

		// asteroid spawn: only when enabled for the level
		if (asteroidSpawnRate > 0) {
			asteroidSpawnTimer++;
			if (asteroidSpawnTimer >= asteroidSpawnRate) {
				spawnAsteroid();
				asteroidSpawnTimer = 0;
			}
		}

		// update aliens
		for (int i = aliens.size() - 1; i >= 0; i--) {
			Alien a = aliens.get(i);
			// move alien by its own speed
			a.update();
			// aliens travel relative to the camera
			a.x -= scrollSpeed;

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

		// update asteroids
		for (int i = asteroids.size() - 1; i >= 0; i--) {
			Asteroid ast = asteroids.get(i);
			ast.update();
			ast.x -= scrollSpeed;

			if (ast.isOffScreen()) {
				asteroids.remove(i);
				continue;
			}

			if (ast.getBounds().intersects(player.getBounds())) {
				hearts--;
				asteroids.remove(i);

				if (hearts <= 0) {
					endGame("gameOver");
					return;
				}
			}
		}

		//Scrolling mechanism
		bgX -= scrollSpeed;
		if (bgX <= -bg.getWidth(null)){
			bgX = 0;
		}

		//platform scroll
		for (Platform p : platforms){
			p.x -= scrollSpeed;
		}

		//ufo scroll
		ufo.x -= scrollSpeed;

		//offset update using scrollspeed
		offset += scrollSpeed;

	}//end of update

	private void spawnAlien() {
		int startX = (getWidth() > 0) ? getWidth() + 200 : 1500;
		int startY = (int)(Math.random()*(500-100-1)+100); 
		Alien a = new Alien(startX, startY, 5);
		//a.y = startY - a.height;
		aliens.add(a);
	}

	private void spawnAsteroid() {
		int startX = (getWidth() > 0) ? getWidth() + 200 : 1500;
		int startY = 200; // fixed height; adjust if you want different lanes
		Asteroid ast = new Asteroid(startX, startY, 6);
		asteroids.add(ast);
	}

	private void loadLevel1() {
		
		// SECTION 1 — CHECKPOINT 1 SAFE START
		platforms.add(new Platform(0, 638, 400, 180, longPlatform));
		platforms.add(new Platform(398, 638, 400, 180, longPlatform));
		platforms.add(new Platform(796, 638, 400, 180, longPlatform));
		platforms.add(new Platform(1194, 638, 400, 180, longPlatform));

		// SECTION 2 — MID‑AIR RUN SHORT
		platforms.add(new Platform(1720, 500, 150, 160, tallPlatform));
		platforms.add(new Platform(2200, 500, 150, 160, tallPlatform));

		// SECTION 3 — MIDAIR LONG PLATFORM
		platforms.add(new Platform(2560, 528, 400, 180, longPlatform));
		platforms.add(new Platform(2958, 528, 400, 180, longPlatform));

		// SECTION 4 — CHECKPOINT 2 FLOOR PLATFORM
		platforms.add(new Platform(3658, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4056, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4454, 638, 400, 180, longPlatform));

		// SECTION 5 — MID‑AIR RUN SHORT
		platforms.add(new Platform(5000, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(5500, 600, 150, 160, tallPlatform));

		// SECTION 6 — CHECKPOINT 3 FLOOR PLATFORM
		platforms.add(new Platform(5800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6198, 638, 400, 180, longPlatform));
		platforms.add(new Platform(6596, 638, 400, 180, longPlatform));
		
		// SECTION 7 — TALL CLUSTER 1
		platforms.add(new Platform(7000, 520, 150, 160, tallPlatform));
		platforms.add(new Platform(7450, 470, 150, 160, tallPlatform));
		platforms.add(new Platform(7900, 500, 150, 160, tallPlatform));

		// SECTION 8 — MID‑AIR RUN 1
		platforms.add(new Platform(8400, 570, 400, 180, longPlatform));
		platforms.add(new Platform(9000, 540, 400, 180, longPlatform));

		// SECTION 9 — TALL CLUSTER 2
		platforms.add(new Platform(9500, 515, 150, 160, tallPlatform));
		platforms.add(new Platform(9950, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(10400, 500, 150, 160, tallPlatform));

		// SECTION 10 — MID‑AIR RUN 2
		platforms.add(new Platform(10950, 560, 400, 180, longPlatform));
		platforms.add(new Platform(11650, 545, 400, 180, longPlatform));

		// SECTION 11 — FINAL FLOOR (4 PIECES)
		platforms.add(new Platform(12150, 638, 400, 180, longPlatform));
		platforms.add(new Platform(12550, 638, 400, 180, longPlatform));
		platforms.add(new Platform(12950, 638, 400, 180, longPlatform));
		platforms.add(new Platform(13350, 638, 400, 180, longPlatform));



	}

	private void loadLevel2() {

		ufo.x = 20000;		
		// SAFE START
		platforms.add(new Platform(0, 638, 400, 180, longPlatform));
		platforms.add(new Platform(398, 638, 400, 180, longPlatform));
		platforms.add(new Platform(796, 638, 400, 180, longPlatform));

		// SECTION 1 — SMALL GAPS, MID HEIGHT
		platforms.add(new Platform(1300, 580, 300, 160, longPlatform));
		platforms.add(new Platform(1700, 520, 150, 160, tallPlatform));
		platforms.add(new Platform(2000, 580, 300, 160, longPlatform));

		// SECTION 2 — BIG GAP + RESCUE PLATFORM
		platforms.add(new Platform(2600, 500, 150, 160, tallPlatform));
		platforms.add(new Platform(3000, 638, 400, 180, longPlatform));

		// SECTION 3 — MID‑AIR RUN
		platforms.add(new Platform(3500, 480, 150, 160, tallPlatform));
		platforms.add(new Platform(3800, 530, 300, 160, longPlatform));
		platforms.add(new Platform(4200, 480, 150, 160, tallPlatform));

		// SECTION 4 — LONG HIGHWAY
		platforms.add(new Platform(4600, 638, 400, 180, longPlatform));
		platforms.add(new Platform(5000, 638, 400, 180, longPlatform));
		platforms.add(new Platform(5400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(5800, 638, 400, 180, longPlatform));

		// SECTION 5 — HIGH JUMP CHALLENGE
		platforms.add(new Platform(6300, 450, 150, 160, tallPlatform));
		platforms.add(new Platform(6700, 500, 300, 160, longPlatform));

		// SECTION 6 — CHAOTIC MIX
		platforms.add(new Platform(7200, 580, 300, 160, longPlatform));
		platforms.add(new Platform(7600, 520, 150, 160, tallPlatform));
		platforms.add(new Platform(7900, 580, 300, 160, longPlatform));

		// SECTION 7 — LONG RUNWAY
		platforms.add(new Platform(8400, 638, 400, 180, longPlatform));
		
		platforms.add(new Platform(9200, 638, 400, 180, longPlatform));
		
		platforms.add(new Platform(9600, 638, 400, 180, longPlatform));
		
		
		// SECTION 8 — EXTENDED HIGHWAY
		platforms.add(new Platform(10000, 638, 400, 180, longPlatform));
		platforms.add(new Platform(10400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(10800, 638, 400, 180, longPlatform));

		// SECTION 9 — MID‑AIR + TALL MIX
		platforms.add(new Platform(11200, 560, 300, 160, longPlatform));
		platforms.add(new Platform(11600, 500, 150, 160, tallPlatform));
		platforms.add(new Platform(11900, 560, 300, 160, longPlatform));

		// SECTION 10 — BIG GAP + RESCUE PLATFORM
		platforms.add(new Platform(12500, 480, 150, 160, tallPlatform));
		platforms.add(new Platform(12900, 638, 400, 180, longPlatform));

		// SECTION 11 — CHAOTIC MID‑AIR RUN
		platforms.add(new Platform(13400, 520, 300, 160, longPlatform));
		platforms.add(new Platform(13800, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(14100, 520, 300, 160, longPlatform));

		// SECTION 12 — LONG HIGHWAY 2.0
		platforms.add(new Platform(14600, 638, 400, 180, longPlatform));
		platforms.add(new Platform(15000, 638, 400, 180, longPlatform));
		platforms.add(new Platform(15400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(15800, 638, 400, 180, longPlatform));

		// SECTION 13 — HIGH JUMP + DROP
		platforms.add(new Platform(16300, 450, 150, 160, tallPlatform));
		platforms.add(new Platform(16700, 580, 300, 160, longPlatform));

		// SECTION 14 — FINAL SUPER‑LONG RUNWAY
		platforms.add(new Platform(17200, 638, 400, 180, longPlatform));
		platforms.add(new Platform(17600, 638, 400, 180, longPlatform));
		platforms.add(new Platform(18000, 638, 400, 180, longPlatform));
		platforms.add(new Platform(18400, 638, 400, 180, longPlatform));

	}

	private void loadLevel3() {

		ufo.x = 11500;
		// SAFE START
		platforms.add(new Platform(0, 638, 400, 180, longPlatform));
		platforms.add(new Platform(398, 638, 400, 180, longPlatform));

		// SECTION 1 — HUGE GAP + TALL PLATFORM
		platforms.add(new Platform(1100, 500, 150, 160, tallPlatform));

		// SECTION 2 — MID‑AIR RUN
		platforms.add(new Platform(1500, 450, 300, 160, longPlatform));
		platforms.add(new Platform(1900, 500, 150, 160, tallPlatform));
		platforms.add(new Platform(2200, 450, 300, 160, longPlatform));

		// SECTION 3 — GIANT GAP
		platforms.add(new Platform(2800, 580, 300, 160, longPlatform));

		// SECTION 4 — DOUBLE TOWER CLIMB
		platforms.add(new Platform(3300, 480, 150, 160, tallPlatform));
		platforms.add(new Platform(3600, 420, 150, 160, tallPlatform));

		// SECTION 5 — LONG HIGHWAY
		platforms.add(new Platform(4000, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(4800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(5200, 638, 400, 180, longPlatform));
		platforms.add(new Platform(5600, 638, 400, 180, longPlatform));

		// SECTION 6 — EVIL MID‑AIR PLATFORM
		platforms.add(new Platform(6100, 500, 300, 160, longPlatform));

		// SECTION 7 — TALL SPIKE‑LIKE TOWER
		platforms.add(new Platform(6500, 420, 150, 160, tallPlatform));

		// SECTION 8 — CHAOS RUN
		platforms.add(new Platform(6900, 580, 300, 160, longPlatform));
		platforms.add(new Platform(7300, 520, 150, 160, tallPlatform));
		platforms.add(new Platform(7600, 580, 300, 160, longPlatform));
		platforms.add(new Platform(8000, 520, 150, 160, tallPlatform));

		// SECTION 9 — FINAL SUPER HIGHWAY (VERY LONG)
		platforms.add(new Platform(8400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(8800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(9600, 638, 400, 180, longPlatform));
		
		platforms.add(new Platform(10400, 638, 400, 180, longPlatform));
		platforms.add(new Platform(10800, 638, 400, 180, longPlatform));
		platforms.add(new Platform(11200, 638, 400, 180, longPlatform));
		
		// SECTION 10 — CHAOTIC MID-AIR MIX
		platforms.add(new Platform(11600, 520, 300, 160, longPlatform));
		platforms.add(new Platform(12000, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(12300, 520, 300, 160, longPlatform));

		// SECTION 11 — BIG GAP + HIGH PLATFORM
		platforms.add(new Platform(12900, 430, 150, 160, tallPlatform));
		platforms.add(new Platform(13200, 580, 300, 160, longPlatform));

		// SECTION 12 — DOUBLE TOWER CLIMB 2.0
		platforms.add(new Platform(13700, 480, 150, 160, tallPlatform));
		platforms.add(new Platform(14000, 420, 150, 160, tallPlatform));
		platforms.add(new Platform(14300, 480, 150, 160, tallPlatform));

		// SECTION 13 — MID-AIR RUNWAY (short, not too long)
		platforms.add(new Platform(14700, 540, 300, 160, longPlatform));
		platforms.add(new Platform(15000, 500, 300, 160, longPlatform));

		// SECTION 14 — CHAOS JUMPS
		platforms.add(new Platform(15400, 580, 300, 160, longPlatform));
		platforms.add(new Platform(15800, 520, 150, 160, tallPlatform));
		platforms.add(new Platform(16100, 580, 300, 160, longPlatform));

		// SECTION 15 — HIGH/LOW MIX
		platforms.add(new Platform(16600, 450, 150, 160, tallPlatform));
		platforms.add(new Platform(16900, 638, 400, 180, longPlatform));
		platforms.add(new Platform(17300, 500, 300, 160, longPlatform));

		// SECTION 16 — EVIL GAP + RESCUE PLATFORM
		platforms.add(new Platform(17800, 430, 150, 160, tallPlatform));
		platforms.add(new Platform(18100, 580, 300, 160, longPlatform));

		// SECTION 17 — FINAL CHALLENGE SECTION (no long runway)
		platforms.add(new Platform(18600, 520, 300, 160, longPlatform));
		platforms.add(new Platform(19000, 460, 150, 160, tallPlatform));
		platforms.add(new Platform(19300, 520, 300, 160, longPlatform));
		platforms.add(new Platform(19700, 580, 300, 160, longPlatform));

		// SECTION 18 — SHORT FINAL RUNWAY (just enough for UFO)
		platforms.add(new Platform(20200, 638, 400, 180, longPlatform));
		platforms.add(new Platform(20600, 638, 400, 180, longPlatform));
		
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
		g.drawImage(bg, bgX, 0, getWidth(), getHeight(), null);
		g.drawImage(bg, bgX + getWidth(), 0, getWidth(), getHeight(), null);

		//checkpoint images x values
		int cp1X = (cPoint1X + 50) - offset;
		int cp2X = cPoint2X - offset;

		//checkpoint flags
		g.drawImage(cp1, cp1X, cPoint1Y, 100, 150, null);
		g.drawImage(cp2, cp2X, cPoint2Y, 100, 150, null);
		
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

		// draw asteroids
		for (Asteroid ast : asteroids) {
			ast.draw(g);
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
