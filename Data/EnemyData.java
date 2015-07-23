package mo.Data;

import robocode.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedHashMap;
import mo.Utils.*;

public class EnemyData {

	// VARIABLES

	BotUtils utils = new BotUtils();
	private String eName;
	private double eAbsBearing;
	private double eBearing;
	private double eDistance;
	private double eVelocity;
	private Point2D.Double ePos;
	private LinkedHashMap<String, HashMap<String, Object>> eMap = new LinkedHashMap<String, HashMap<String, Object>>(5, 2, true);

	
	// ACCESSORS
	public String getName() {
		return this.eName;
	}

	public double getAbsBearing() {
		return this.eAbsBearing;
	}

	public double getBearing() {
		return this.eBearing;
	}

	public double getDistance() {
		return this.eDistance;
	}
	
	public double getVelocity() {
		return this.eVelocity;
	}

	public Point2D.Double getPos() {
		return this.ePos;
	}

	public LinkedHashMap<String, HashMap<String, Object>> getMap() {
		return this.eMap;
	}

	// METHODS
	public void update(ScannedRobotEvent e) {
		/*		
		eName = e.getName();
		eDistance = e.getDistance();
		eVelocity = e.getVelocity();
		eBearing = e.getBearingRadians();
		eAbsBearing = myBot.getHeadingRadians() + e.getBearingRadians();	
		
		ePos = utils.getPos(myBot.getPos(), eAbsBearing, eDistance);

		// Add enemy information to Map
		eMap.put(eName, new HashMap<String, Object>());
		eMap.get(eName).put("eVelocity", eVelocity);
		eMap.get(eName).put("eAbsBearing", eAbsBearing);
		eMap.get(eName).put("eBearing", eBearing);
		eMap.get(eName).put("ePos", ePos);
		*/
		
		System.out.println("EnemyData: ");		
		
	}

	public void update(RobotDeathEvent e) {
		eMap.remove(e.getName());
	}
}
