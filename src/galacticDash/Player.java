package galacticDash;
import java.awt.*;
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
		playerBehaviour.actions.put("idle", new AnimatedAction("assets/images/glorp-cat.gif", 500));
		playerBehaviour.actions.put("run", new AnimatedAction("assets/images/glorp-spin.gif", 500));
		playerBehaviour.actions.put("jump", new AnimatedAction("assets/images/glorp-spin.gif", 500));
	}

	int x, y;//coordinates
	int width, height;//dimensions
	int velocity = 0;//vertical velocity
	int groundY = 638;//ground level
	boolean onGround = false;//check if player on ground
	String currentAction;
	final UnitBehaviour behaviour;
	boolean flip;

	//constructor
	public Player(int x, int y) {
		this.x = x; this.y = y;
		currentAction = "idle";
		behaviour = playerBehaviour;

		//fixed width and height of sprite
		width = 128;
		height = 128;

		flip = false;
	}

	/* PURPOSE: to allow player to jump
	 * PRE: n/a
	 * POST: n/a - player jumps
	 */
	public void jump() {
		if (onGround){
			velocity = -20;//jumping strength
			onGround = false;
		}
	}

	/* PURPOSE: to tell player what animation to use, and fixes dimensions
	 * PRE: String action
	 * POST: n/a
	 */
	public void setAction(String action){
		currentAction = action;
		width = 128;
		height = 128;
	}

	/* PURPOSE: to return player bounds
	 * PRE: n/a
	 * POST: return rectangle bounds using x, y, width, height
	 */
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}

	/* PURPOSE: to draw player
	 * PRE: Graphics g
	 * POST: n/a
	 */
	public void draw(Graphics g) {
		AnimatedAction action = behaviour.actions.get(currentAction);
		if (action != null){
			g.drawImage(action.animation, x, y, null);
		}
	}

}

