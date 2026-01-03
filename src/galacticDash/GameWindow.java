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
	private GamePanel2 gamePanel2;//game panel level 2
	private GamePanel3 gamePanel3;//game panel level 3

	private GameOverScreen gameOverPanel;//game over panel
	private GameWinScreen gameWinPanel;//game win panel

	private LevelsScreen levelsPanel;//levels panel

	private int currentLevel = 1;//default level is 1
	private int finalTime;//final gameplay time

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
		gamePanel2 = new GamePanel2(this);
		gamePanel3 = new GamePanel3(this);

		//levels panel
		levelsPanel = new LevelsScreen(this);

		//game over screen
		gameOverPanel = new GameOverScreen(this);
		//game win screen
		gameWinPanel = new GameWinScreen(this);

		mainPanel.add(startScreen, "start");
		mainPanel.add(gamePanel, "game");
		mainPanel.add(gamePanel2, "game2");
		mainPanel.add(gamePanel3, "game3");
		mainPanel.add(levelsPanel, "levels");
		mainPanel.add(gameOverPanel, "gameOver");
		mainPanel.add(gameWinPanel, "gameWin");

		add(mainPanel);
		setVisible(true);
	}


	public void showScreen(String name){
		cardLayout.show(mainPanel, name);
		if ("game".equals(name)) {
			currentLevel = 1;
			gamePanel.startGame();//only start game when clicked
			gamePanel.requestFocusInWindow();
		}
		if ("game2".equals(name)) {
			currentLevel = 2;
			gamePanel2.startGame();//only start game when clicked
			gamePanel2.requestFocusInWindow();
		}

		if ("game3".equals(name)) {
			currentLevel = 3;
			gamePanel3.startGame();//only start game when clicked
			gamePanel3.requestFocusInWindow();
		}

		if (name.equals("gameOver") || name.equals("gameWin")) {//both screen can access final time
			if (currentLevel == 1)
				setTimer(gamePanel.getElapsedTime());
			if (currentLevel == 2)
				setTimer(gamePanel2.getElapsedTime());
			if (currentLevel == 3)
				setTimer(gamePanel3.getElapsedTime());
		}

	}//end of showScreen


	public void setTimer(int time){
		this.finalTime = time;//final time for gameplay
	}//end of setTime

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