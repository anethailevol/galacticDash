package galacticDash;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.* ;

public class GameWindow extends JFrame  {
		
	public GameWindow() {
		setTitle("Galactic Dash");
        setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		IPanel panel = new IPanel();
	}
	
	public static void main(String[] args) {
		new GameWindow();
	}
}