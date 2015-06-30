package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class GoToBot extends AdvancedRobot {
	Point2D.Double destination, pos, ePos;
	Arc2D.Double arc;
	Rectangle2D.Double bounds,field,field2;
	double radius, tRadius;
	
    public void run() {
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);	
		destination = new Point2D.Double(getX(),getY());
        turnRadarLeftRadians(Double.POSITIVE_INFINITY);
		//arc = new Arc2D.Double();
    }
    public void onScannedRobot(ScannedRobotEvent e) {
		radius = getVelocity() / ((10-0.75*getVelocity()) / (180/Math.PI));
		field = new Rectangle2D.Double(0,0,getBattleFieldWidth(),getBattleFieldHeight());
		field2= new Rectangle2D.Double(radius,radius,getBattleFieldWidth()-(radius*2),getBattleFieldHeight()-(radius*2));
		
		pos = new Point2D.Double(getX(),getY());
		ePos = getPos(pos,e.getBearingRadians()+getHeadingRadians(),e.getDistance());
		
		arc = new Arc2D.Double(pos.x,pos.y, radius, radius, 0, 360, Arc2D.OPEN);
		bounds = new Rectangle2D.Double(pos.x-radius-18,pos.y-radius-18,radius*2+36,radius*2+36);

		double toDest = pos.distance(destination);
		double toTarget = pos.distance(ePos);

		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians()+e.getBearingRadians()-getRadarHeadingRadians()));
		setTurnRightRadians(Utils.normalRelativeAngle(absBearing(pos,destination)-getHeadingRadians()));
		//out.println(Utils.normalRelativeAngle(absBearing(pos,destination)-getHeadingRadians()));  //pos=right
		setAhead(100);

		if (field.contains(bounds)) {
			destination = new Point2D.Double(randomNum((int)field.x,(int)field.width),randomNum((int)field.y,(int)field.height));
			setMaxVelocity(randomNum(8,8));
		}
		else setTurnRight(90);
    }



	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance) {
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}

    private double absBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX()-source.getX(), target.getY()-source.getY());
    }
	
/*
	private static Point2D.Double getTurnArc(double velocity) {
		getVelocity() / ((10-0.75*getVelocity()) / (180/Math.PI));
		return velocity;
	}
	*/
	int randomNum(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	public void onPaint(Graphics2D g) {	
		g.setColor(new Color(255,0,0,50));
		g.fillOval((int)destination.x-6, (int)destination.y-6, 12, 12);
		g.drawLine((int)pos.x,(int)pos.y,(int)destination.x,(int)destination.y);
		
		g.setColor(new Color(0,0,255,50));
		g.drawLine((int)pos.x,(int)pos.y,(int)ePos.x,(int)ePos.y);
		
		g.setColor(new Color(0,255,0,30));
		g.drawRect((int)field.x, (int)field.y, (int)field.width, (int)field.height);
		g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
		
		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(pos.x, pos.y, radius, 0, 360, Arc2D.OPEN);
    	g.draw(arc);
	}
}
