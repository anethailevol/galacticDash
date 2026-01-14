package galacticDash;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
/* Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Game WIndow
 */

//import java.awt.event.* ;

public class GameWindow extends JFrame {

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
	public List<String> readLeaderboard() throws IOException {
		
		List<String> entries = new ArrayList<>();
		
		try (Scanner sc = new Scanner((new File("leaderboard.txt")))) {
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (!line.isEmpty()) entries.add(line);
			}
			
		}
		
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
	public void appendScore(String name, int score) {
		
		try (FileWriter fw = new FileWriter("leaderboard.txt", true);
		         PrintWriter pw = new PrintWriter(fw)) {

		        pw.println(name + ":" + score);

		 } catch (IOException e) {
		        System.out.println("Unable to write to file: " + e.getMessage());
		 }

		
	}

	/**
	 * Purpose: Record the player's result for the play by appending a line.
	 * Pre: String name, int heartsThisPlay
	 * Post: n/a
	 */
	public void recordPlayerResult(String name, int heartsThisPlay) {
		// record this play by appending a line to the leaderboard file
		appendScore(name, heartsThisPlay);
	}

	/**
	 * Read the leaderboard file with Scanner and aggregate totals per player.
	 * Returns top N strings formatted: "Rank. Name — X/Y (Z%)"
	 */
	public List<String> getTopLeaderboard(int n) {
		
		Map<String, int[]> map = new HashMap<>();
		
		List<String> out = new ArrayList<>();
		
		
		try (Scanner sc = new Scanner(new File("leaderboard.txt"))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.isEmpty()) continue;
				String[] parts = line.split(":" );

				if (parts.length >= 2) {
						int hearts = Integer.parseInt(parts[1]);
						int[] cur = map.getOrDefault(parts[0], new int[] {0,0});
						cur[0] += hearts;
						cur[1] += 1;
						map.put(parts[0], cur);
				}
			}
		} catch (FileNotFoundException e) {}

		class Entry {
		    String name;
		    int total;
		    int plays;
		    double rate;

		    public Entry(String name, int total, int plays) {
		        this.name = name;
		        this.total = total;
		        this.plays = plays;
		        this.rate = (double) total / (plays * 3.0);
		    }
		}
				
		List<Entry> list = new ArrayList<>();

		// turn the map into Entry objects
		for (Map.Entry<String, int[]> e : map.entrySet()) {
		    String name = e.getKey();
		    int total = e.getValue()[0];
		    int plays = e.getValue()[1];

		    list.add(new Entry(name, total, plays));
		}


		for (int i = 1; i < list.size(); i++) {

		    Entry key = list.get(i); // assign key
		    
		    int j = i - 1;

		    while (j >= 0) {
		    	
		    	//condition for hashmap
		        boolean greater = (list.get(j).rate < key.rate) || (list.get(j).rate == key.rate && list.get(j).total < key.total);

		        if (!greater) break;

		        list.set(j + 1, list.get(j)); // assign
		        j--;
		    }

		    list.set(j + 1, key); // assign
		}
		
		int rank = 1;
		for (Entry e : list) {
		    if (rank > n) break;

		    int possible = e.plays * 3;
		    int percentage = (int) Math.round(100.0 * e.total / possible);

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