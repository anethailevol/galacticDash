package galacticDash;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
/*
 * Athena Arun, Mithushaa Rajakumar
 * ICS4U1
 * January 12, 2025
 * Galactic Dash - Unit Behaviour
 */

public class UnitBehaviour { 
	HashMap<String, AnimatedAction> actions;
	int flipWidth; 
	UnitBehaviour(int flipWidth){
		this.flipWidth = flipWidth;
		actions = new HashMap<String, AnimatedAction>();
	}
}
