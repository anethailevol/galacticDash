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
		Font font = pixelFont.deriveFont(40f);//new font size
		playButton.setFont(font);
		helpButton.setFont(font);

		//button sizes
		playButton.setPreferredSize(new Dimension(250, 100));
		playButton.setMaximumSize(new Dimension(250, 100));

		helpButton.setPreferredSize(new Dimension(250, 100));
		helpButton.setMaximumSize(new Dimension(250, 100));

		//play font and bg colour
		playButton.setBackground(Color.BLACK);
		playButton.setForeground(Color.WHITE);

		//help font and bg colour
		helpButton.setBackground(Color.BLACK);
		helpButton.setForeground(Color.WHITE);

		//center alignment
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		//adding buttons to panel
		buttonPanel.add(playButton);
		buttonPanel.add(Box.createVerticalStrut(40));//spacing between buttons
		buttonPanel.add(helpButton);

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(90, 0, 0, 0));//moviing buttons lower using padding
		add(buttonPanel, BorderLayout.CENTER);

		// Example actions
		playButton.addActionListener(e -> window.showScreen("game"));

		helpButton.addActionListener(e -> {
			JDialog helpDialog = new JDialog(window, "Help", true);
			helpDialog.setSize(500, 250);
			JTextArea helpText = new JTextArea(
					"\nInstructions:" +
							"\nYou are an alien cat, trying to avoid astronauts!" +
							"\nCollect stars!" +
							"\nTry to beat all 3 levels!" +
							"\n\nControls:" +
							"\nUse arrow keys or AD to move left and right." +
							"\nSpace/Up Arrow to jump."
					);//end of text area

			helpText.setFont(font.deriveFont(17f));// + changing font size
			helpText.setEditable(false);
			helpText.setLineWrap(true);
			helpText.setWrapStyleWord(true);//wrap words around boundaries

			//font and bg colour
			helpText.setBackground(Color.BLACK);
			helpText.setForeground(Color.WHITE);

			helpDialog.add(new JScrollPane(helpText), BorderLayout.CENTER);
			helpDialog.setVisible(true);

		});//end of help button pop up

	}//end of startScreen

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}//end of paintComponent


}//end of class