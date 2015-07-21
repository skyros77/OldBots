package mo;
import java.awt.Graphics2D;
import mo.Data.*;
import mo.Utils.*;
import robocode.*;

public class PewPew extends AdvancedRobot {
	
	private BotData	myBot;	
	private EnemyData enemy;
	private RadarData radar;
	
	public void run() {
		myBot = new BotData(this);
		enemy = new EnemyData();	
		radar = new RadarData();	
 		while(true) {
 			setTurnRadarRightRadians(RadarData.radarDir * Double.POSITIVE_INFINITY);
 			scan();
 		}
	}
		
	public void onScannedRobot(ScannedRobotEvent e) {
		myBot.update();
		enemy.update(e);
		radar.update(e);
		
		//BotUtils.printMap(EnemyData.enemy);
	}
	
    public void onRobotDeath(RobotDeathEvent e) {
    	enemy.update(e);
    }
    
    public void onPaint(Graphics2D g) {
    	PaintUtils.drawPos(g, EnemyData.ePos); 	
    }
}
