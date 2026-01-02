package galacticDash;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Player
 */

public class Player {

	static final UnitBehaviour playerBehaviour;

	static {
		playerBehaviour = new UnitBehaviour(110);
		//playerBehaviour.actions.put("attack", new AnimatedAction("skeleton-attack.gif", 800));
		//playerBehaviour.actions.put("die", new AnimatedAction("skeleton-death.gif", 1000));
		playerBehaviour.actions.put("idle", new AnimatedAction("assets/images/glorp-cat.gif", 500));
		playerBehaviour.actions.put("run", new AnimatedAction("assets/images/glorp-spin.gif", 500));
		playerBehaviour.actions.put("jump", new AnimatedAction("assets/images/glorp-spin.gif", 500));
	}

	int x, y;
	int velocity = 0;//vertical velocity
	int groundY = 500;//ground level
	boolean onGround = true;//check if player on ground
	String currentAction;
	final UnitBehaviour behaviour;
	boolean flip;
	
	public Player(int x, int y) {
		this.x = x; this.y = y;
		currentAction = "idle";
		behaviour = playerBehaviour;
		flip = false;
	}

	public void jump() {
	    if (onGround){
	        velocity = -23;//jumping strength
	        onGround = false;
	    }
	}
	    
	public void setAction(String action){//tells player which animation to use
		currentAction = action;
	}


	public void draw(Graphics g) {
		AnimatedAction action = behaviour.actions.get(currentAction);
		if (action != null){
			g.drawImage(action.animation, x, y, null);//not scaled, add dimensions if needed
		}
	}

}

