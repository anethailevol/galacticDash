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

	//game window constructor
	public GameWindow() {
		
		setTitle("Galactic Dash");
		setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null); //place in centre of screen

		cardLayout = new CardLayout();
		
		mainPanel = new JPanel(cardLayout);

		//Start Screen
		StartScreen panel1 = new StartScreen(this);

		//game screen
		GamePanel panel2 = new GamePanel(this);

		mainPanel.add(panel1, "start");
		mainPanel.add(panel2, "game");

		add(mainPanel);
		setVisible(true);
	}

	public void showScreen(String name){
		cardLayout.show(mainPanel, name);
	}
	
	
}//end of class