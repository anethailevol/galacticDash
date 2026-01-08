package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Levels Screen
 */
public class LevelsScreen extends JPanel{
	private GameWindow window;//reference main window
	private ImageIcon bg;//background

	//constructor
	public LevelsScreen(GameWindow window) {
		this.window = window;
		setBackground(Color.BLACK);//temp colour
		bg = new ImageIcon("assets/images/background.gif");

		Font pixelFont;//game font
		//SET FONT
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);
		} 
		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}

		//home button
		JButton home = new JButton("Home");
		home.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		home.setForeground(Color.BLACK); 
		home.setBackground(Color.WHITE); 

		home.setBounds(100, 620, 200, 50);
		add(home);

		home.addActionListener(e -> {
			window.showScreen("start");
		});

		setLayout(new BorderLayout());//border layout for level buttons

		//levels title
		JLabel title = new JLabel("LEVELS", SwingConstants.CENTER);
		title.setFont(pixelFont.deriveFont(Font.PLAIN, 100f));
		title.setForeground(Color.WHITE); 
		title.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));//moving title down using padding
		add(title, BorderLayout.NORTH);

		//buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false); // transparent so background shows through
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));//vertical layout for buttons

		//buttons
		JButton level1 = new JButton("Level 1");
		JButton level2 = new JButton("Level 2");
		JButton level3 = new JButton("Level 3");

		//fonts
		Font font = pixelFont.deriveFont(40f);
		level1.setFont(font);
		level2.setFont(font);
		level3.setFont(font);

		//button sizes
		level1.setPreferredSize(new Dimension(320, 100));
		level1.setMaximumSize(new Dimension(320, 100));

		level2.setPreferredSize(new Dimension(320, 100));
		level2.setMaximumSize(new Dimension(320, 100));

		level3.setPreferredSize(new Dimension(320, 100));
		level3.setMaximumSize(new Dimension(320, 100));

		//center alignment
		level1.setAlignmentX(Component.CENTER_ALIGNMENT);
		level2.setAlignmentX(Component.CENTER_ALIGNMENT);
		level3.setAlignmentX(Component.CENTER_ALIGNMENT);

		//play and help button colours
		level1.setBackground(Color.BLACK);
		level1.setForeground(Color.WHITE);
		level2.setBackground(Color.BLACK);
		level2.setForeground(Color.WHITE);
		level3.setBackground(Color.BLACK);
		level3.setForeground(Color.WHITE);

		//remove white box when clicked
		level1.setFocusPainted(false);
		level2.setFocusPainted(false);
		level3.setFocusPainted(false);

		//adding buttons to panel
		buttonPanel.add(level1);
		buttonPanel.add(Box.createVerticalStrut(40));//spacing between buttons
		buttonPanel.add(level2);
		buttonPanel.add(Box.createVerticalStrut(40));//spacing between buttons
		buttonPanel.add(level3);

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));//moving buttons lower using padding
		add(buttonPanel, BorderLayout.CENTER);

		//action listeners
		level1.addActionListener(e ->{
		    window.setCurrentLevel(1);
		    window.showScreen("game");
		});

		level2.addActionListener(e ->{
		    window.setCurrentLevel(2);
		    window.showScreen("game");
		});

		level3.addActionListener(e ->{
		    window.setCurrentLevel(3);
		    window.showScreen("game");
		});

	}

	/* PURPOSE: to draw graphics
	 * PRE: Graphics comp
	 * POST: n/a
	 */
	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
	}//end of paintComponent

}//end of class
