package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

public class PatternMatcher_v2 extends AdvancedRobot
{

	private static final double	FIRE_POWER		= .1;
	private static final double	FIRE_SPEED		= Rules.getBulletSpeed(FIRE_POWER); //20 - (3 * power)
	private static List<Point2D.Double>	predictions	= new ArrayList<Point2D.Double>();
	double pHeading;
	Point2D.Double enemyPos, myPos;

	public void run()
	{
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);	
		turnRadarLeftRadians(Double.POSITIVE_INFINITY);
		while (true) {scan();}
	}


	public void onScannedRobot(ScannedRobotEvent e)
	{
		//clear
		predictions.clear();
		
		//scan
 	    setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians()+e.getBearingRadians()-getRadarHeadingRadians()));

		//get positions
		myPos = new Point2D.Double(getX(),getY());
		enemyPos = getPos(myPos,e.getBearingRadians()+getHeadingRadians(),e.getDistance());

		double cHeading = e.getHeadingRadians();
		double diff = cHeading-pHeading;

		for (int i=0; i<myPos.distance(enemyPos)/FIRE_SPEED; i++)
		{		
			cHeading += diff;
			enemyPos = getPos(enemyPos, diff+cHeading, e.getVelocity());
			predictions.add(enemyPos);			
		}
		
		pHeading = e.getHeadingRadians();
		

		//turn gun
		double absoluteBearing = Math.atan2(enemyPos.x - myPos.x, enemyPos.y - myPos.y);
		double gunTurn = absoluteBearing - getGunHeadingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
		
		//fire gun
		if (getGunTurnRemainingRadians()<.1)  
			setFire(FIRE_POWER);

	}

	public void onPaint(Graphics2D g) {	
		
		g.setColor(Color.WHITE);
		for (Point2D.Double p : predictions)
		{
			g.fillOval((int) p.x - 2, (int) p.y - 2, 4, 4);
		}
		
		g.setColor(Color.RED);
		g.fillOval((int)enemyPos.x-4, (int)enemyPos.y-4, 8, 8);
		
		g.setColor(new Color(255,0,0,100));
		g.drawLine((int)enemyPos.x,(int)enemyPos.y,(int)myPos.x,(int)myPos.y);
	}

	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
}
