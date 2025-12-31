package galacticDash;

import java.awt.Image;
import javax.swing.ImageIcon;

public class AnimatedAction {
	int duration; 
	Image animation;
	public AnimatedAction(String animation, int duration) {
		this.duration = duration;
		this.animation = new ImageIcon(animation).getImage();	
	}
}