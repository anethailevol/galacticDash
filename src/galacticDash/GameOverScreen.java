package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game Over Screen
 */
public class GameOverScreen extends JPanel {
	
	private Image gameOverbg;
	private GameWindow window;//reference main window
	private Leaderboard leaderboard;
	private String playerRateText = "";
	private List<String> topEntries = null;

	public GameOverScreen(GameWindow window) {
		this.window = window;
		leaderboard = new Leaderboard("assets/leaderboard.txt");
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
		gameOver.setBounds(300, 150, 1000, 100);
		add(gameOver, BorderLayout.NORTH);

		
		//home button
		JButton home = new JButton("Home");
		home.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		home.setForeground(Color.BLACK); 
		home.setBackground(Color.WHITE); 
		home.setBounds(300, 600, 200, 50);
		add(home);
		
		home.addActionListener(e -> {
			window.showScreen("start");
		});
		
		//retry button
		JButton retry = new JButton ("Retry");
		retry.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		retry.setForeground(Color.BLACK); 
		retry.setBackground(Color.WHITE); 
		retry.setBounds(650, 600, 200, 50);
		add(retry);
		
		retry.addActionListener(e -> {
			
			window.showScreen("game");
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
		

		// expose update hook for when the panel is shown
	}

	public void updateForGameOver() {
		int finalHearts = window.getFinalHearts();
		String name = JOptionPane.showInputDialog(this, "Enter your name for the leaderboard:", "Player");
		if (name == null || name.trim().isEmpty()) name = "Player";
		leaderboard.update(name, finalHearts);
		double rate = leaderboard.getSuccessRate(name);
		playerRateText = String.format("%s Success: %.1f%%", name, rate * 100.0);
		topEntries = leaderboard.topEntries(5);
		repaint();
	}

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(gameOverbg, 0, 0, getWidth(), getHeight(), this);

		//transparent black box to dim bg
		comp.setColor(new Color(0, 0, 0, 100));
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

		// draw player success text if available
		comp.setFont(pixelFont.deriveFont(Font.PLAIN, 24f));
		if (playerRateText != null && !playerRateText.isEmpty()) {
			comp.drawString(playerRateText, 300, 520);
		}

		// draw top leaderboard entries
		comp.setFont(pixelFont.deriveFont(Font.PLAIN, 20f));
		if (topEntries != null) {
			int ty = 560;
			comp.drawString("Leaderboard:", 300, ty);
			ty += 30;
			for (String s : topEntries) {
				comp.drawString(s, 320, ty);
				ty += 26;
			}
		}

	}//end of paintComponent


}//end of class
