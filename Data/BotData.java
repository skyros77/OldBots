package mo.Data;
import robocode.*;
import java.awt.geom.Point2D;

public class BotData {
	
	public static AdvancedRobot	myBot;
	public static Point2D.Double myPos;	
	
	public BotData(AdvancedRobot robot) {
		myBot = robot;
	}

	public void update() {
		myPos = new Point2D.Double(myBot.getX(),myBot.getY());
	}

}
