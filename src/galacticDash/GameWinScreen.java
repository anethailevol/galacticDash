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

	private Image gameWinbg;//bg
	private GameWindow window;//reference main window
	private JTextArea leaderboardArea;
	private JScrollPane leaderboardScroll;
	
	//constructor
	public GameWinScreen(GameWindow window) {
		this.window = window;
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
		home.setBounds(300, 600, 200, 50);
		add(home);

		home.addActionListener(e -> {
			window.showScreen("start");
		});

		//levels button
		JButton levels = new JButton("Levels");
		levels.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		levels.setForeground(Color.BLACK); 
		levels.setBackground(Color.WHITE); 
		levels.setBounds(1000, 600, 200, 50);
		add(levels);

		levels.addActionListener(e -> {
			window.showScreen("levels");
		});

		// Leaderboard display area (hidden by default) wrapped in a scroll pane
		leaderboardArea = new JTextArea();
		// make the text area read-only and visually transparent so it overlays nicely
		leaderboardArea.setEditable(false);
		leaderboardArea.setOpaque(false);
		leaderboardArea.setForeground(Color.WHITE);
		leaderboardArea.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		leaderboardArea.setLineWrap(true);
		leaderboardArea.setWrapStyleWord(true);

		// wrap the text area in a scroll pane so long leaderboards can scroll
		leaderboardScroll = new JScrollPane(leaderboardArea);
		leaderboardScroll.setOpaque(false);
		leaderboardScroll.getViewport().setOpaque(false);
		leaderboardScroll.setBorder(null);
		// position at x = 800 per request
		leaderboardScroll.setBounds(800, 320, 700, 300);
		leaderboardScroll.setVisible(false);
		add(leaderboardScroll);
	}

	/**
	 * Purpose: Populate leaderboard area when the win screen is shown.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void updateForGameWin() {
		if (window.getCurrentLevel() == 3) {
			// get aggregated top 5 entries from GameWindow
			java.util.List<String> top = window.getTopLeaderboard(5);
			// clear previous text
			leaderboardArea.setText("");
			if (top.isEmpty()) {
				// no entries yet
				leaderboardArea.append("No leaderboard entries yet.\n");
			} else {
				// append a small header then each leaderboard line
				leaderboardArea.append("LEADERBOARD (Top 5)\n\n");
				for (String s : top) leaderboardArea.append(s + "\n");
			}
			// ensure visible area shows the top
			leaderboardArea.setCaretPosition(0);
			// make the scroll pane visible
			leaderboardScroll.setVisible(true);
		} else {
			// hide leaderboard when not level 3
			leaderboardScroll.setVisible(false);
		}
	}


	/* PURPOSE: to draw graphics
	 * PRE: Graphics comp
	 * POST: n/a
	 */
	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(gameWinbg, 0, 0, getWidth(), getHeight(), this);

		//transparent black box to dim bg
		comp.setColor(new Color(0, 0, 0, 150));
		comp.fillRect(0, 0, getWidth(), getHeight());

		Font pixelFont;//game font
		//SET FONT
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PressStart2P.ttf")).deriveFont(18f);
			GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(pixelFont);
		} 
		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}

		//draw final time
		int totalSeconds = window.getTimer();
		int minutes = totalSeconds/60;
		int seconds = totalSeconds%60;
		String timeString = String.format("%02d:%02d", minutes, seconds);

		//time appereance
		comp.setColor(Color.WHITE);
		comp.setFont(pixelFont.deriveFont(Font.PLAIN, 30f));
		comp.drawString("Time: " + timeString, 300, 350);

		// draw final hearts (larger)
		int finalHearts = window.getFinalHearts();
		int heartSize = 96;
		int startX = 300;
		int y = 400;
		for (int i = 0; i < 3; i++) {
			Image heartImg = new ImageIcon(i < finalHearts ? "assets/images/heart.png" : "assets/images/deadheart.png").getImage();
			comp.drawImage(heartImg, startX + i * (heartSize + 10), y, heartSize, heartSize, null);
		}


	}//end of paintComponent


}//end of screen
