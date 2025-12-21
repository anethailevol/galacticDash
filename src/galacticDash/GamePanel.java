package galacticDash;
import java.awt.*;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Main Game Panel
 */

public class GamePanel extends JPanel {
	private GameWindow window;

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		setBackground(Color.LIGHT_GRAY);//temp colour
		setLayout(null);
		setFocusable(true);
	}


}//end of class
