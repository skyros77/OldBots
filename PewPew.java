package mo; 
import robocode.*;
import robocode.util.Utils;

import java.util.*;

public class PewPew extends AdvancedRobot
{
	static LinkedHashMap<String, HashMap<String, Object>> myArray = new LinkedHashMap<String, HashMap<String, Object>>(5, 2, false);
	
	
	static LinkedHashMap<String, Double> target = new LinkedHashMap<String,Double>(5, 2, true);
	double scanDir = 1;
	static Object sought;	
	
	public void run() {
		while(true) {
			setTurnRadarRightRadians(Double.POSITIVE_INFINITY*scanDir);
			scan();
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

		
	    String name = e.getName();
		
		if (!myArray.containsKey(name)) {
		    myArray.put(name, new HashMap<String, Object>());
		}
		myArray.get(name).put("velocity", e.getVelocity());
		myArray.get(name).put("bearing", getHeadingRadians() + e.getBearingRadians());
		printMap(myArray);

		
		//for (Map.Entry<String, HashMap<String, Object>> cursor : myArray.entrySet())
		//	scanDir = Utils.normalRelativeAngle(cursor.getKey().getValue() - getRadarHeadingRadians())
			
			
		//out.println(myArray.get(name).get("distance"));
		
		/*
	    target.put(name, getHeadingRadians() + e.getBearingRadians());
	 
	    if ((name == sought || sought == null) && target.size() == getOthers()) {
		scanDir = Utils.normalRelativeAngle(target.values().iterator().next() - getRadarHeadingRadians());
	        sought = target.keySet().iterator().next();
	    }
	    out.println(scanDir);
	    */
	}

	public void printMap(LinkedHashMap<String, HashMap<String, Object>> map) {
		for (Map.Entry<String, HashMap<String, Object>> cursor : map.entrySet())
			out.println(cursor.getKey() +" = "+ cursor.getKey());
	}
	
    public void onRobotDeath(RobotDeathEvent e) {
        target.remove(e.getName());
        sought = null;
    }
}
	
