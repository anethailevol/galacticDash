package galacticDash;
import java.awt.*;
import javax.swing.*;
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

		mainPanel.add(startScreen, "start");
		mainPanel.add(gamePanel, "game");
		mainPanel.add(levelsPanel, "levels");
		mainPanel.add(gameOverPanel, "gameOver");
		mainPanel.add(gameWinPanel, "gameWin");

		add(mainPanel);
		setVisible(true);
	}


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

		if ("gameOver".equals(name)) {
			gameOverPanel.updateForGameOver();
		}
	}//end of showScreen


	public void setTimer(int time){
		this.finalTime = time;//final time for gameplay
	}//end of setTime

	public void setFinalHearts(int hearts) {
		this.finalHearts = hearts;
	}

	public int getFinalHearts() {
		return finalHearts;
	}

	public int getTimer() {
		return finalTime;//return final time
	}

	public void setCurrentLevel(int level) {
		this.currentLevel = level;//current level
	}

	public int getCurrentLevel() {
		return currentLevel;//return current level
	}


}//end of class