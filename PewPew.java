package mo;

import java.awt.Graphics2D;
import mo.Data.*;
import mo.Utils.*;
import robocode.*;

public class PewPew extends AdvancedRobot {

	BotData myBot;
	EnemyData enemy = new EnemyData();
	RadarData radar = new RadarData();
	PaintUtils paint = new PaintUtils();

	public void run() {
		myBot = new BotData(this);
		while (true) {
			System.out.println("PewPew: " + myBot.getPos());	
			setTurnRadarRightRadians(radar.getRadarDir() * Double.POSITIVE_INFINITY);
			scan();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//myBot.update();
		enemy.update(e);
		// radar.update(e);
	}

	public void onRobotDeath(RobotDeathEvent e) {
		enemy.update(e);
	}

	public void onPaint(Graphics2D g) {
		/*
		if (enemy.getPos() != null) {
			paint.drawPos(g, enemy.getPos());
		}
		*/
	}
}
