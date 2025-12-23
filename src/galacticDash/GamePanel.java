package galacticDash;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Main Game Panel
 */

public class GamePanel extends JPanel implements ActionListener{
	private GameWindow window;//game window panel
	private Image glorpCat;//cat sprite 1

	//cat position and velocity
	private int catX = 200;
	private int catY = 200;
	private int catSpeed = 3;

	private Input input;//input

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		setBackground(Color.LIGHT_GRAY);//temp colour
		setLayout(null);
		setFocusable(true);

		//importing cat
		ImageIcon glorp1 = new ImageIcon("assets/images/glorp-cat.gif");
		glorpCat = glorp1.getImage();

		//adding input
		input = new Input();
		addKeyListener(input);

		new Timer(12, this).start();//refresh
	}//end of GamePanel

	/*PURPOSE: To update game state every frame
	 * PRE: ActionEvent e - event
	 * POST: n/a - Cat position updated and panel repainted
	 */
	public void actionPerformed(ActionEvent e) {
		//movement
		if(input.left)
			catX -= catSpeed;
		if(input.right)
			catX += catSpeed;
		if(input.up)
			catY -= catSpeed;
		if(input.down)
			catY += catSpeed;

		repaint();
		requestFocusInWindow();
	}//end of actionPerformed

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		comp.drawImage(glorpCat, catX, catY, null);//drawing cat sprite
	}

}//end of class
