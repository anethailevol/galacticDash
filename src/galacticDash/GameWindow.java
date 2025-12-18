package galacticDash;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.* ;

public class GameWindow extends JFrame  {
		
	public GameWindow() {
		setTitle("Galactic Dash");
        setSize(1500,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		//Start Screen
		StartScreen panel1 = new StartScreen();
		add(panel1);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new GameWindow();
		
	}
}