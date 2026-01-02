package galacticDash;
import java.awt.Image;
import javax.swing.ImageIcon;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Animated Action
 */

public class AnimatedAction {
	int duration; 
	Image animation;
	public AnimatedAction(String animation, int duration) {
		this.duration = duration;
		this.animation = new ImageIcon(animation).getImage();	
	}
}