package mo;
import robocode.*;
import robocode.util.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class strafeBot extends AdvancedRobot {
	private static List<Point2D.Double>	predictions	= new ArrayList<Point2D.Double>();
	Point2D.Double myPos, enemyPos;
	double pHeading, direction;
	
    public void run() {
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
		direction = 1;		
		while(true)	turnRadarLeftRadians(Double.POSITIVE_INFINITY);
	}
	
    public void onScannedRobot(ScannedRobotEvent e) {

		//reset
		predictions.clear();
		
		//scan
		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians() - getRadarHeadingRadians() + e.getBearingRadians()));
		
		//setup
		myPos = new Point2D.Double(getX(),getY());
		enemyPos = getPos(myPos,e.getBearingRadians() + getHeadingRadians(),e.getDistance());
		double distance = 200/e.getDistance() < 1 ? 0.2 : -0.2;	
		out.println(e.getDistance());	
		double firePower = Math.min(600/e.getDistance(), 3);
		double fireSpeed = 20-firePower*3;	
		double cHeading = e.getHeadingRadians();
		double delta = cHeading-pHeading;

		//move
		// || getVelocity() == 0
		if (getTime() % randomNum(5,30) == 0) direction *= -1;			
		setTurnRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2 - (distance*direction)));
		setAhead(100 * direction);		
		
		//predict
		for (int i=0; i<myPos.distance(enemyPos)/fireSpeed; i++)
		{
			cHeading += delta;
			enemyPos = getPos(enemyPos, cHeading, e.getVelocity());
			predictions.add(enemyPos);
		}		
		pHeading = e.getHeadingRadians();

		//turn gun
		double turn = absBearing(enemyPos,myPos);
		setTurnGunRightRadians(Utils.normalRelativeAngle(turn - getGunHeadingRadians()));			

		//fire	
		if (getGunHeat() == 0 && Math.abs(getGunTurnRemainingRadians()) < .1)		
			setFire(firePower);

	}
	
	public void onHitWall(HitWallEvent e) {
		if (getVelocity() == 0) direction *= -1;
	}	

	public void onHitRobot(HitRobotEvent e) {
		if (getVelocity() == 0) direction *= -1;
	}
	



	private Point2D.Double getPos(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}	

    private double absBearing(Point2D.Double target, Point2D.Double source) {
		return Math.atan2(target.x-source.x, target.y-source.y);
    }

	int randomNum(int min, int max)
	{
	   int range = (max - min) + 1;
	   return (int)(Math.random() * range) + min;
	}
		
	public void onPaint(Graphics2D g) {	

		g.setColor(new Color(255,255,255,100));
		for (int i=1; i<predictions.size(); i++){
			g.drawLine((int)predictions.get(i).x,(int)predictions.get(i).y,(int)predictions.get(i-1).x,(int)predictions.get(i-1).y);
		}		

		g.setColor(new Color(255,0,0,100));
		g.fillOval((int)enemyPos.x-6, (int)enemyPos.y-6, 12, 12);
		
		g.setColor(new Color(255,0,0,100));
		g.drawLine((int)enemyPos.x,(int)enemyPos.y,(int)myPos.x,(int)myPos.y);
	}
	
}
