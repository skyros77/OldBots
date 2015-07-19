package mo.Data;
import mo.Utils.*;

import robocode.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class EnemyData{

	public static LinkedHashMap<String, HashMap<String, Object>> enemy = new LinkedHashMap<String, HashMap<String, Object>>(5, 2, false);
	
	protected double velocity;
	protected double bearing;
	protected double absBearing;
	
	//onScannedRobot
	public void update(ScannedRobotEvent e) {
		String name = e.getName();
		if (!enemy.containsKey(name))
		    enemy.put(name, new HashMap<String, Object>());
		    
		enemy.get(name).put("velocity", e.getVelocity());
		enemy.get(name).put("absBearing", BotData.myBot.getHeadingRadians() + e.getBearingRadians());
		enemy.get(name).put("bearing", e.getBearingRadians());
		//Utils.printMap(enemy); //print out debug info
	}
	
	//onRobotDeath
	public void update(RobotDeathEvent e) {	
		enemy.remove(e.getName());
	}
}
