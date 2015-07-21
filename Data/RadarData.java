package mo.Data; 
import java.util.HashMap;
import java.util.Map.Entry;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class RadarData extends EnemyData {

	public static double radarDir = 1;
	public static Entry<String, HashMap<String, Object>> scanTarget;
	//public static Point2D.Double scanTargetPos;

	//onScannedRobot
	public void update(ScannedRobotEvent e) {
		
		//scan for oldest target
		if (enemy.size() == BotData.myBot.getOthers()) {
			scanTarget		= enemy.entrySet().iterator().next();
			radarDir 		= Utils.normalRelativeAngle((double)scanTarget.getValue().get("eAbsBearing") - BotData.myBot.getRadarHeadingRadians());
			//scanTargetPos = (Point2D.Double)scanTarget.getValue().get("ePos"); //get target ePos
		}
	}
}
