package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Start Screen
 */

public class StartScreen extends JPanel{
	private GameWindow window;//reference main window

	ImageIcon background;

	//constructor
	public StartScreen(GameWindow window) {
		this.window = window;
		Font pixelFont; //main game font

		background = new ImageIcon("assets/images/background.gif");

		//SET FONT

		try {

			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);

		} 

		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}

		//setLayout
		setLayout(new BorderLayout());

		// Title 
		JLabel title = new JLabel("Galactic Dash", SwingConstants.CENTER);
		title.setFont(pixelFont.deriveFont(Font.PLAIN, 70f));
		title.setForeground(Color.WHITE); 
		add(title, BorderLayout.NORTH);

		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false); // transparent so background shows through
		buttonPanel.setLayout(new FlowLayout());

		JButton playButton = new JButton("Play");
		JButton helpButton = new JButton("Help");

		//adding buttons to panel
		buttonPanel.add(playButton);
		buttonPanel.add(helpButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// Example actions
		playButton.addActionListener(e -> System.out.println("Play pressed!"));
		playButton.addActionListener(e -> window.showScreen("game"));

		helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Instructions:\nUse arrow keys to move.\nAvoid obstacles.\nCollect stars!"));
	}

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}


}