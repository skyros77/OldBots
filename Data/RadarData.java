package mo.Data;
import robocode.*;
import robocode.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class RadarData extends EnemyData {

	private double radarDir = 1;
	
	//onScannedRobot
	public void update(ScannedRobotEvent e) {
		
		//control radar scan direction
		for (Map.Entry<String, HashMap<String, Object>> cursor : enemy.entrySet()) {
			double bearing = (double)cursor.getValue().get("bearing");
			radarDir = Utils.normalRelativeAngle(bearing - BotData.myBot.getRadarHeadingRadians());
			System.out.println("Name: " + e.getName() + "Radar Turn: " + radarDir);

		}
	}
}
