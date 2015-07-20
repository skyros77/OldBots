package mo.Data; 
import robocode.*;
import robocode.util.*;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map.Entry;



public class RadarData extends EnemyData {

	public static double radarDir = 1;
	public static Entry<String, HashMap<String, Object>> scanTarget;
	public static Point2D.Double scanTargetPos;
	
	//onScannedRobot
	public void update(ScannedRobotEvent e) {

		//try {
			scanTarget		= enemy.entrySet().iterator().next();
			scanTargetPos	= (Point2D.Double)scanTarget.getValue().get("ePos");
			radarDir 		= Utils.normalRelativeAngle((double)scanTarget.getValue().get("absBearing") - BotData.myBot.getRadarHeadingRadians());
			//System.out.println("target/turn: " + scanTarget +"/"+ radarDir);
			
			//PaintUtils.drawPos(g, pos);
			//System.out.println(EnemyData.ePos);
			//System.out.println("target/turn" + scanTarget.getKey() +"/"+ radarDir);
				//radarDir = (double)scanTarget.getValue().get("absBearing") - BotData.myBot.getRadarHeadingRadians();
				//System.out.println(scanTarget.getValue().get("ePos"));
				
				//System.out.println("target/turn" + scanTarget.getKey() +"/"+ radarDir);
				//System.out.println("target/scan: " + name +"/"+  e.getName());
				//System.out.println("target/pos: " + scanTarget.getKey() + "/" + EnemyData.ePos);
		//}
		//catch (NullPointerException Exception) {
		//	System.out.println("NullPointerException Exception");
		//}
/*
		//control radar scan direction
		for (Map.Entry<String, HashMap<String, Object>> cursor : enemy.entrySet()) {
			double absBearing = (double)cursor.getValue().get("absBearing");
			radarDir = Utils.normalRelativeAngle(absBearing - BotData.myBot.getRadarHeadingRadians());
			//System.out.println("Name: " + e.getName() + "Radar Turn: " + radarDir);
			System.out.println(cursor);


		}
			*/		
	}
}
