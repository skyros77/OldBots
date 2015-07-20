package mo.Data; 
import java.awt.geom.Point2D;

import robocode.*;

public class BotData extends AdvancedRobot{
	
	public static AdvancedRobot	myBot;
	public static Point2D.Double	myPos;	
	
	public BotData(AdvancedRobot r) {
		myBot = r;
	}

	public void update() {
		myPos = new Point2D.Double(myBot.getX(),myBot.getY());
	}

}
