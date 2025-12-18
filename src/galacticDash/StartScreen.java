package galacticDash;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;


public class StartScreen extends JPanel {
	
    ImageIcon background;

    public StartScreen() {
    	        
    	Font pixelFont; //main game font

        background = new ImageIcon("background.gif");
        
        //SET FONT

		try {

		pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P.ttf")).deriveFont(18f);
		GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(pixelFont);
        
		} 

		catch (IOException|FontFormatException e){pixelFont = new Font("Monospaced", Font.PLAIN, 18);}
        
        //setLayout
        setLayout(new BorderLayout());

        // Title 
        JLabel title = new JLabel("Galactic Dash");
        title.setFont(pixelFont.deriveFont(Font.PLAIN, 70f));
        title.setForeground(Color.WHITE); 
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

        // Example actions
        playButton.addActionListener(e -> System.out.println("Play pressed!"));
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Instructions:\nUse arrow keys to move.\nAvoid obstacles & Aliens"));

                
    }

    public void paintComponent(Graphics comp) {
        super.paintComponent(comp);
        Graphics2D comp2D = (Graphics2D) comp;
        comp2D.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}