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
	private Image glorpSpin;//cat sprite 2

	//cat position and velocity
	private int catX = 200;
	private int catSpeed = 3;//speed for left right

	//jumping (y) variables
	private int catY;
	private int yVelocity = 0;
	private int gravity = 1;//gravity velocity
	private boolean onGround = true;//checking if sprite on ground
	private int groundY = 450;//ground level

	private Input input;//input

	//constructor
	public GamePanel(GameWindow window){
		this.window = window;
		setBackground(Color.LIGHT_GRAY);//temp colour
		setLayout(null);
		setFocusable(true);

		catY = groundY;//starting cat position on ground

		//importing cat
		ImageIcon glorp1 = new ImageIcon("assets/images/glorp-cat.gif");
		glorpCat = glorp1.getImage();

		//import cat spin
		ImageIcon glorp2 = new ImageIcon("assets/images/glorp-spin.gif");
		glorpSpin = glorp2.getImage();

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
		if(input.left)//left
			catX -= catSpeed;
		if(input.right)//right
			catX += catSpeed;

		if (input.jump && onGround){//jumping if sprite on ground
			yVelocity = -20;//jump amount
			onGround = false;
		}//end of if

		//gravity
		yVelocity += gravity;
		catY += yVelocity;

		if (catY >= groundY){//sprite touches ground
			catY = groundY;
			yVelocity = 0;
			onGround = true;
		}//end of if

		repaint();
		requestFocusInWindow();
	}//end of actionPerformed

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		if (onGround){
			comp.drawImage(glorpCat, catX, catY, null);//default sprite
		}//end of if 
		else{
			comp.drawImage(glorpSpin, catX, catY, 70, 70, null);//jump sprite, scaled
		}//end of else
	}//end of paintComponent
	
}//end of class
