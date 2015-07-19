package mo; 
import mo.Data.*;
import robocode.*;

public class PewPew extends AdvancedRobot
{
	private BotData myBot;	
	private EnemyData enemy;
	private RadarData radar;	
	
	public void run() {
		myBot = new BotData(this);
		enemy = new EnemyData();	
		radar = new RadarData();	

 		while(true) turnRadarRightRadians(Double.POSITIVE_INFINITY);
	}
		
	public void onScannedRobot(ScannedRobotEvent e) {
		enemy.update(e);
		radar.update(e);
	}
	
    public void onRobotDeath(RobotDeathEvent e) {
    	enemy.update(e);
    }
}
