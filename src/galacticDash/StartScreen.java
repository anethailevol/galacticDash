package galacticDash;

import javax.swing.*;
import java.awt.*;
/*
 * Athena Arun
 * ICS4U1
 * November 3, 2025
 * GUI Adding Images Part 2
 */
public class StartScreen extends JPanel {
	
    ImageIcon background;

    public StartScreen() {
    	
        background = new ImageIcon("background.gif");
        
        // Use a layout that lets us position components easily
        setLayout(new BorderLayout());

        // Title label
        JLabel title = new JLabel("Galactic Dash", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 70));
        title.setForeground(Color.WHITE); // contrast against background
        add(title, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // transparent so background shows through
        buttonPanel.setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        JButton helpButton = new JButton("Help");

        buttonPanel.add(playButton);
        buttonPanel.add(helpButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Example actions (replace with your game logic)
        playButton.addActionListener(e -> System.out.println("Play pressed!"));
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Instructions:\nUse arrow keys to move.\nAvoid obstacles.\nCollect stars!"));

        
    }

    public void paintComponent(Graphics comp) {
        super.paintComponent(comp);
        Graphics2D comp2D = (Graphics2D) comp;
        comp2D.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}