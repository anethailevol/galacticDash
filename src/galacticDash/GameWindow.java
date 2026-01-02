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
	private GamePanel panel2;//game panel

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
		StartScreen panel1 = new StartScreen(this);

		//game screen
		panel2 = new GamePanel(this);

		mainPanel.add(panel1, "start");
		mainPanel.add(panel2, "game");

		add(mainPanel);
		setVisible(true);
	}

	public void showScreen(String name){
		cardLayout.show(mainPanel, name);
		if ("game".equals(name)) {
			panel2.startGame();//only start game when clicked
			panel2.requestFocusInWindow();
		}//end of if
	}


}//end of class