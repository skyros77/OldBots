package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class GoToBot extends AdvancedRobot {
	Point2D.Double destination, myPos;
	Arc2D arc;
	Rectangle2D.Double field;
	double radius;
	
    public void run() {
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);	
		arc = new Arc2D.Double();
		field = new Rectangle2D.Double(18,18,getBattleFieldWidth()-36,getBattleFieldHeight()-36);
		destination = new Point2D.Double(getX(),getY());
        turnRadarLeftRadians(Double.POSITIVE_INFINITY);
    }
    public void onScannedRobot(ScannedRobotEvent e) {
		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians()+e.getBearingRadians()-getRadarHeadingRadians()));
		radius = getVelocity() / ((10-0.75* getVelocity())/(180/Math.PI));
		myPos = new Point2D.Double(getX(),getY());
        setTurnRight(normalRelativeAngle(absoluteBearing(myPos, destination) - getHeading()));
		setAhead(100);

        if (myPos.distance(destination) < 50) {
			destination = new Point2D.Double(randomNum((int)field.x,(int)field.width),randomNum((int)field.y,(int)field.height));
			setMaxVelocity(randomNum(1,8));
        }
    }

    private double absoluteBearing(Point2D source, Point2D target) {
        return Math.toDegrees(Math.atan2(target.getX() - source.getX(), target.getY() - source.getY()));
    }

    private double normalRelativeAngle(double angle) {
        angle = Math.toRadians(angle);
        return Math.toDegrees(Math.atan2(Math.sin(angle), Math.cos(angle))); 
    }
	
	/*
	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance) {
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
	*/
	int randomNum(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	public void onPaint(Graphics2D g) {	
		g.setColor(new Color(255,0,0,255));
		g.fillOval((int)destination.x-3, (int)destination.y-3, 6, 6);
		g.drawLine((int)myPos.x,(int)myPos.y,(int)destination.x,(int)destination.y);
		
		g.setColor(new Color(0,255,0,50));
		g.drawRect((int)field.x, (int)field.y, (int)field.width, (int)field.height);
		
		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(myPos.x, myPos.y, radius, 0, 360, Arc2D.OPEN);
    	g.draw(arc);
	}
}
