package galacticDash;

import javax.swing.*;
import java.awt.*;
/*
 * Athena Arun
 * ICS4U1
 * November 3, 2025
 * GUI Adding Images Part 2
 */
public class IPanel extends JPanel {
	
    Image background;

    public IPanel() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        background = kit.getImage("background.png");
    }

    public void paintComponent(Graphics comp) {
        super.paintComponent(comp);
        Graphics2D comp2D = (Graphics2D) comp;
        comp2D.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}