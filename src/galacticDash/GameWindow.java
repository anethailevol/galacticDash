package galacticDash;
import javax.swing.*;
import java.awt.*;
/* Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game WIndow
 */

//import java.awt.event.* ;

public class GameWindow extends JFrame  {

	private CardLayout cardLayout;
	private JPanel mainPanel;
	private GamePanel gamePanel;//game panel

	private GameOverScreen gameOverPanel;//game over panel
	private GameWinScreen gameWinPanel;//game win panel

	private LevelsScreen levelsPanel;//levels panel

	private int currentLevel = 1;//default level is 1
	private int finalTime;//final gameplay time
	private int finalHearts = 0; // store hearts left at end

	//game window constructor
	public GameWindow() {

		//window details
		setTitle("Galactic Dash");
		setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null); //place in centre of screen

		//layouts
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		//Start Screen
		StartScreen startScreen = new StartScreen(this);

		//game screen (level 1-3)
		gamePanel = new GamePanel(this);

		//levels panel
		levelsPanel = new LevelsScreen(this);

		//game over screen
		gameOverPanel = new GameOverScreen(this);
		//game win screen
		gameWinPanel = new GameWinScreen(this);

		//adding panels to main panel
		mainPanel.add(startScreen, "start");
		mainPanel.add(gamePanel, "game");
		mainPanel.add(levelsPanel, "levels");
		mainPanel.add(gameOverPanel, "gameOver");
		mainPanel.add(gameWinPanel, "gameWin");

		add(mainPanel);
		setVisible(true);
	}

	/* PURPOSE: to show screen depending on outcome
	 * PRE: String name - name of screen
	 * POST: n/a - shows screen
	 */
	public void showScreen(String name){
	    cardLayout.show(mainPanel, name);

	    if ("game".equals(name)) {
	        gamePanel.loadLevel(currentLevel); // load selected level
	        gamePanel.startGame();
	        gamePanel.requestFocusInWindow();
	    }

	    if (name.equals("gameOver") || name.equals("gameWin")) {
	        setTimer(gamePanel.getElapsedTime());
	    }
	}//end of showScreen

/* PURPOSE: to set the timer
 * PRE: int time
 * POST: n/a - sets time - int finalTime
 */
	public void setTimer(int time){
		this.finalTime = time;//final time for gameplay
	}//end of setTime

	/* PURPOSE: to set the number of final hearts
	 * PRE: int hearts
	 * POST: n/a - sets hearts - int hearts
	 */
	public void setFinalHearts(int hearts) {
		this.finalHearts = hearts;
	}

	/* PURPOSE: to return number of ifnal hearts
	 * PRE: n/a
	 * POST: return final hearts - int finalHearts
	 */
	public int getFinalHearts() {
		return finalHearts;
	}

	/* PURPOSE: to return final time
	 * PRE: n/a
	 * POST: reeturn final time - int finalTime
	 */
	public int getTimer() {
		return finalTime;//return final time
	}

	/* PURPOSE: to set current level number
	 * PRE: int level
	 * POST: n/a - sets level number
	 */
	public void setCurrentLevel(int level) {
		this.currentLevel = level;//current level
	}

	/* PURPOSE: to return current level number
	 * PRE: n/a
	 * POST: returns current level number - int currentLevel
	 */
	public int getCurrentLevel() {
		return currentLevel;//return current level
	}


}//end of class