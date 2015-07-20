package mo.Utils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BotUtils {
	
	//print out hashmap information
	public static void printMapKey(LinkedHashMap<String, HashMap<String, Object>> enemy) {
		for (Entry<String, HashMap<String, Object>> cursor : enemy.entrySet()) {
		}
	}
	
	//get X/Y position of a target
	public static Point2D.Double getPos(Point2D.Double pos, double angle, double distance) {
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
}
