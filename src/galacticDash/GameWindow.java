package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
/* Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game WIndow
 */

//import java.awt.event.* ;

public class GameWindow extends JFrame  {

	private CardLayout cardLayout;
	private JPanel mainPanel;
	private GamePanel gamePanel;//game panel

	private GameOverScreen gameOverPanel;//game over panel
	private GameWinScreen gameWinPanel;//game win panel

	private LevelsScreen levelsPanel;//levels panel

	private int currentLevel = 1;//default level is 1
	private int finalTime;//final gameplay time
	private int finalHearts = 0; // store hearts left at end

	private String playerName = "Player"; // stores the current player's name
	private static final String LEADERBOARD_FILE = "leaderboard.txt";

	//game window constructor
	public GameWindow() {

		//window details
		setTitle("Galactic Dash");
		setSize(1500, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null); //place in centre of screen

		// Ask player for their name at startup
		String name = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.QUESTION_MESSAGE);
		if (name != null && !name.trim().isEmpty()) {
			this.playerName = name.trim();
		}



		//layouts
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		//Start Screen
		StartScreen startScreen = new StartScreen(this);

		//game screen (level 1-3)
		gamePanel = new GamePanel(this);

		//levels panel
		levelsPanel = new LevelsScreen(this);

		//game over screen
		gameOverPanel = new GameOverScreen(this);
		//game win screen
		gameWinPanel = new GameWinScreen(this);

		//adding panels to main panel
		mainPanel.add(startScreen, "start");
		mainPanel.add(gamePanel, "game");
		mainPanel.add(levelsPanel, "levels");
		mainPanel.add(gameOverPanel, "gameOver");
		mainPanel.add(gameWinPanel, "gameWin");

		add(mainPanel);
		setVisible(true);
	}

	/* PURPOSE: return player's name
	 * PRE: n/a
	 * POST: return playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/* PURPOSE: read leaderboard file (simple lines: name:score)
	 * PRE: String filePath - path to leaderboard file
	 * POST: returns list of raw lines from file
	 */
	public List<String> readLeaderboard(String filePath) {
		// legacy helper: read raw lines (kept for compatibility)
		List<String> entries = new ArrayList<>();
		File file = new File(filePath);
		if (!file.exists()) {
			try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); return entries; }
		}
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (!line.isEmpty()) entries.add(line);
			}
		} catch (IOException e) { e.printStackTrace(); }
		return entries;
	}

	/* PURPOSE: append a score to leaderboard file as "name:score" (primitive)
	 * PRE: String filePath, String name, int score
	 * POST: appends entry to file
	 */
	/**
	 * Append a single play result as a line using PrintWriter: "name:hearts".
	 * This intentionally only appends; aggregation across plays is done when
	 * reading the leaderboard with Scanner.
	 */
	public void appendScore(String filePath, String name, int score) {
		File file = new File(filePath);
		try {
			// ensure the leaderboard file exists before appending
			if (!file.exists()) file.createNewFile();
			// open the file for appending and write a single line "name:hearts"
			try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
				pw.println(name + ":" + score); // append one play result
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Record this player's result for the play by appending a line.
	 */
	public void recordPlayerResult(String name, int heartsThisPlay) {
		// record this play by appending a line to the leaderboard file
		appendScore(LEADERBOARD_FILE, name, heartsThisPlay);
	}

	/**
	 * Read the leaderboard file with Scanner and aggregate totals per player.
	 * Returns top N strings formatted: "Rank. Name — X/Y (Z%)"
	 */
	public java.util.List<String> getTopLeaderboard(int n) {
		// map will hold aggregated data per player: [totalHearts, plays]
		java.util.Map<String, int[]> map = new HashMap<>();
		java.util.List<String> out = new ArrayList<>();
		File file = new File(LEADERBOARD_FILE);
		if (!file.exists()) return out;
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.isEmpty()) continue;
				String[] parts = line.split(":" );
				if (parts.length >= 2) {
					try {
						// parse the hearts value from the line
						int hearts = Integer.parseInt(parts[1]);
						// get or create the aggregate array [totalHearts, plays]
						int[] cur = map.getOrDefault(parts[0], new int[] {0,0});
						cur[0] += hearts; // add this play's hearts to total
						cur[1] += 1; // increment play count for this player
						map.put(parts[0], cur); // store back in map
					} catch (NumberFormatException e) { /* skip malformed */ }
				}
			}
		} catch (IOException e) { e.printStackTrace(); }

		class Entry { String name; int total; int plays; double rate; }
		java.util.List<Entry> list = new java.util.ArrayList<>();
		// convert the aggregated map entries into sortable Entry objects
		for (java.util.Map.Entry<String,int[]> e : map.entrySet()) {
			Entry en = new Entry();
			en.name = e.getKey();
			en.total = e.getValue()[0]; // total hearts accumulated
			en.plays = e.getValue()[1]; // number of plays
			// success rate = total hearts / (plays * 3)
			en.rate = (double)en.total / (en.plays * 3.0);
			list.add(en);
		}

		list.sort((a,b) -> {
			int cmp = Double.compare(b.rate, a.rate);
			if (cmp != 0) return cmp;
			return Integer.compare(b.total, a.total);
		});

		int rank = 1;
		// build the readable top-N strings with percentage and return
		for (Entry e : list) {
			if (rank > n) break; // stop after top-n
			int possible = e.plays * 3; // max hearts possible across plays
			int percentage = (int)Math.round(100.0 * e.total / possible); // as percent
			out.add(rank + ". " + e.name + " — " + e.total + "/" + possible + " (" + percentage + "%)");
			rank++;
		}
		return out;
	}

	/* PURPOSE: to show screen depending on outcome
	 * PRE: String name - name of screen
	 * POST: n/a - shows screen
	 */
	public void showScreen(String name){
	    cardLayout.show(mainPanel, name);

	    if ("game".equals(name)) {
	        gamePanel.loadLevel(currentLevel); // load selected level
	        gamePanel.startGame();
	        gamePanel.requestFocusInWindow();
	    }

	    if (name.equals("gameOver") || name.equals("gameWin")) {
	        setTimer(gamePanel.getElapsedTime());
	    }

		if ("gameOver".equals(name)) {
			// record and update
			recordPlayerResult(playerName, finalHearts);
			gameOverPanel.updateForGameOver();
		}
		else if ("gameWin".equals(name)) {
			// record and show leaderboard for wins
			recordPlayerResult(playerName, finalHearts);
			gameWinPanel.updateForGameWin();
		}
	}//end of showScreen

/* PURPOSE: to set the timer
 * PRE: int time
 * POST: n/a - sets time - int finalTime
 */
	public void setTimer(int time){
		this.finalTime = time;//final time for gameplay
	}//end of setTime

	/* PURPOSE: to set the number of final hearts
	 * PRE: int hearts
	 * POST: n/a - sets hearts - int hearts
	 */
	public void setFinalHearts(int hearts) {
		this.finalHearts = hearts;
	}

	/* PURPOSE: to return number of ifnal hearts
	 * PRE: n/a
	 * POST: return final hearts - int finalHearts
	 */
	public int getFinalHearts() {
		return finalHearts;
	}

	/* PURPOSE: to return final time
	 * PRE: n/a
	 * POST: reeturn final time - int finalTime
	 */
	public int getTimer() {
		return finalTime;//return final time
	}

	/* PURPOSE: to set current level number
	 * PRE: int level
	 * POST: n/a - sets level number
	 */
	public void setCurrentLevel(int level) {
		this.currentLevel = level;//current level
	}

	/* PURPOSE: to return current level number
	 * PRE: n/a
	 * POST: returns current level number - int currentLevel
	 */
	public int getCurrentLevel() {
		return currentLevel;//return current level
	}


}//end of class