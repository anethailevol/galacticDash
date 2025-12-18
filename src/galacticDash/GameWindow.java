package galacticDash;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game WIndow
 */

//import java.awt.*;
//import java.awt.event.* ;

public class GameWindow extends JFrame  {
	

	public GameWindow() {
		setTitle("Galactic Dash");
        setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
	
		//Start Screen
		StartScreen panel1 = new StartScreen();

		add(panel1);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		GameWindow frame = new GameWindow();
		
	}
}