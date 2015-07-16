package mo.Data;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import robocode.*;

public class EnemyData extends AdvancedRobot {
	
	static LinkedHashMap<String, HashMap<String, Object>> enemy = new LinkedHashMap<String, HashMap<String, Object>>(5, 2, false);
	protected double velocity;
	protected double bearing;
	protected double absBearing;
	
	
	//onScannedRobot
	public void update(ScannedRobotEvent e) {
		String name = e.getName();
		if (!enemy.containsKey(name))
		    enemy.put(name, new HashMap<String, Object>());
		    
		enemy.get(name).put("velocity", e.getVelocity());
		enemy.get(name).put("absBearing", getHeadingRadians() + e.getBearingRadians());
		enemy.get(name).put("bearing", e.getBearingRadians());
		printMap(enemy); //print out debug info
	}
	
	//onRobotDeath
	public void update(RobotDeathEvent e) {	
		enemy.remove(e.getName());
	}
	
	
	/* DEBUG */
	
	
	//print out Map information
	public void printMap(LinkedHashMap<String, HashMap<String, Object>> map) {
		for (Map.Entry<String, HashMap<String, Object>> cursor : map.entrySet())
			System.out.println(cursor.getKey() +"="+ cursor.getValue() +"\r");
		System.out.println("\r");
	}	
}

