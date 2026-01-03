package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game Over Screen
 */
public class GameOverScreen  extends JPanel {

	private Image gameOverbg;
	
	public GameOverScreen(GameWindow window) {
        setLayout(null);
		setBackground(Color.BLACK);//temp colour
		gameOverbg = new ImageIcon("assets/images/gameOverbg.gif").getImage();

		Font pixelFont;//game font

		//SET FONT
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);
		} 

		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}
		
        JLabel gameOver = new JLabel("GAME OVER");
        gameOver.setFont(pixelFont.deriveFont(Font.PLAIN, 100f));
        gameOver.setForeground(Color.WHITE); 
        gameOver.setBounds(250, 150, 1000, 100);
		add(gameOver, BorderLayout.NORTH);

        JButton home = new JButton("Home");
		home.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		home.setForeground(Color.BLACK); 
		home.setBackground(Color.WHITE); 

        home.setBounds(100, 600, 200, 50);
        add(home);

        home.addActionListener(e -> {
        	window.showScreen("start");
        });

    }
	
	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(gameOverbg, 0, 0, getWidth(), getHeight(), this);
	}//end of paintComponent
	
	

}//end of class
