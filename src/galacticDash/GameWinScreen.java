package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game Win Screen
 */
public class GameWinScreen extends JPanel{
	
	private Image gameWinbg;
	
	public GameWinScreen(GameWindow window) {
        setLayout(null);
		setBackground(Color.BLACK);//temp colour
		gameWinbg = new ImageIcon("assets/images/gameWinbg.gif").getImage();

		Font pixelFont;//game font

		//SET FONT
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);
		} 

		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}
		
        JLabel gameOver = new JLabel("YOU WIN");
        gameOver.setFont(pixelFont.deriveFont(Font.PLAIN, 100f));
        gameOver.setForeground(Color.WHITE); 
        gameOver.setBounds(400, 150, 1000, 100);
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
		comp2D.drawImage(gameWinbg, 0, 0, getWidth(), getHeight(), this);
		
		//transparent black box to dim bg
		comp.setColor(new Color(0, 0, 0, 150));
		comp.fillRect(0, 0, getWidth(), getHeight());

	}//end of paintComponent
	
	
}//end of screen
