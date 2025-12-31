package galacticDash;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

public class UnitBehaviour { 
	HashMap<String, AnimatedAction> actions;
	int flipWidth; 
	UnitBehaviour(int flipWidth){
		this.flipWidth = flipWidth;
		actions = new HashMap<String, AnimatedAction>();
	}
}
