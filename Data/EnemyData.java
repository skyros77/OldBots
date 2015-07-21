package mo.Data; 
import mo.Utils.*;
import robocode.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EnemyData {

	public static LinkedHashMap<String, HashMap<String, Object>> enemy = new LinkedHashMap<String, HashMap<String, Object>>(5, 2,  true);
	public static String eName;
	public static double eVelocity;
	public static double eBearing;
	public static double eAbsBearing;
	public static Point2D.Double ePos;
	
	//onScannedRobot
	public void update(ScannedRobotEvent e) {
		eName			= e.getName();
		eVelocity		= e.getVelocity();
		eBearing		= e.getBearingRadians();
		eAbsBearing 	= BotData.myBot.getHeadingRadians() + e.getBearingRadians();
		ePos			= BotUtils.getPos(BotData.myPos, eAbsBearing, e.getDistance());
				
		//Add enemy information to Map
		enemy.put(eName, new HashMap<String, Object>());		
		enemy.get(eName).put("eVelocity", eVelocity);
		enemy.get(eName).put("eAbsBearing", eAbsBearing);
		enemy.get(eName).put("eBearing", eBearing);
		enemy.get(eName).put("ePos", ePos);
	}

	//onRobotDeath
	public void update(RobotDeathEvent e) {	
		enemy.remove(e.getName());
	}
}
