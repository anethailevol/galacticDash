package galacticDash;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public class Player {
	
	static final UnitBehaviour playerBehaviour;
	
	static {
		playerBehaviour = new UnitBehaviour(110);
		//playerBehaviour.actions.put("attack", new AnimatedAction("skeleton-attack.gif", 800));
		//playerBehaviour.actions.put("die", new AnimatedAction("skeleton-death.gif", 1000));
		playerBehaviour.actions.put("idle", new AnimatedAction("assets/images/glorp-cat.gif", 500));
		playerBehaviour.actions.put("run", new AnimatedAction("assets/images/glorp-spin.gif", 500));
	}
	int x, y;
	String currentAction;
	final UnitBehaviour behaviour;
	boolean flip;
	public Player(int x, int y) {
		this.x = x; this.y = y;
		currentAction = "idle";
		behaviour = playerBehaviour;
		flip = false;
	}
	
	public void draw(Graphics g) {
	    AnimatedAction action = behaviour.actions.get(currentAction);
	    g.drawImage(action.animation, x, y, null);
	}

}

