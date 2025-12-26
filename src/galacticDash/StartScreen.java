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
		title.setFont(pixelFont.deriveFont(Font.PLAIN, 100f));
		title.setForeground(Color.WHITE); 
		title.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));//moving title down using padding
		add(title, BorderLayout.NORTH);

		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false); // transparent so background shows through
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));//vertical layout for buttons

		
		JButton playButton = new JButton("Play");
		JButton helpButton = new JButton("Help");

		//button font
		Font buttonFont = pixelFont.deriveFont(30f);
		playButton.setFont(buttonFont);
		helpButton.setFont(buttonFont);
		
		//button sizes
		playButton.setPreferredSize(new Dimension(200, 60));
		playButton.setMaximumSize(new Dimension(200, 60));

		helpButton.setPreferredSize(new Dimension(200, 60));
		helpButton.setMaximumSize(new Dimension(200, 60));

		//center alignment
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		//adding buttons to panel
		buttonPanel.add(playButton);
		buttonPanel.add(Box.createVerticalStrut(40));//spacing between buttons
		buttonPanel.add(helpButton);

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));//moviing buttons lowerr using padding
		add(buttonPanel, BorderLayout.CENTER);

		// Example actions
		playButton.addActionListener(e -> System.out.println("Play pressed!"));
		playButton.addActionListener(e -> window.showScreen("game"));
		helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Instructions:\nUse arrow keys to move.\nAvoid obstacles.\nCollect stars!"));
	}//end of startScreen

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}//end of paintComponent


}//end of class