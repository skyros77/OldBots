package mo.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class RadarData {

	// VARIABLES
	EnemyData eBot = new EnemyData();
	BotData myBot = new BotData();
	
	private LinkedHashMap<String, HashMap<String, Object>> eMap = eBot.getMap();
	private double radarDir = 1;
	private Entry<String, HashMap<String, Object>> eTarget;
	// private Point2D.Double scanTargetPos;

	// CONSTRUCTORS
	public RadarData() {
	}

	// ASSESSORS
	public double getRadarDir() {
		return this.radarDir;
	}

	// CONSTRUCTORS

	// METHODS
	public void update(ScannedRobotEvent e) {

		// radar scan for oldest target
		if (eMap.size() == myBot.getOthers()) {
			eTarget = eMap.entrySet().iterator().next();
			radarDir = Utils.normalRelativeAngle((double) eTarget.getValue().get("eAbsBearing") - myBot.getHeadingRadians());
			// scanTargetPos = (Point2D.Double)scanTarget.getValue().get("ePos"); //get target
		}
	}
}
