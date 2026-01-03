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
		//game screen
		gamePanel = new GamePanel(this);
		//game over screen
		gameOverPanel = new GameOverScreen(this);
		//game win screen
		gameWinPanel = new GameWinScreen(this);

		mainPanel.add(startScreen, "start");
		mainPanel.add(gamePanel, "game");
		mainPanel.add(gameOverPanel, "gameOver");
		mainPanel.add(gameWinPanel, "gameWin");

		add(mainPanel);
		setVisible(true);
	}

	public void showScreen(String name){
		cardLayout.show(mainPanel, name);
		if ("game".equals(name)) {
			gamePanel.startGame();//only start game when clicked
			gamePanel.requestFocusInWindow();
		}//end of if

	}

	public void resetGame(){
		mainPanel.remove(gamePanel);
		gamePanel = new GamePanel(this);
		mainPanel.add(gamePanel, "game");
	}


}//end of class