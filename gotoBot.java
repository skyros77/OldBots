/* TODO:
 * if speed is below set tolerance reverse direction
 * if(Math.abs(turn) > Math.PI/2) direction = -1;
 */

package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;


public class GoToBot extends AdvancedRobot {

	
	
	Point2D.Double target, pos, ePos, pivot, feeler;
	Rectangle2D.Double field, bbox;
	double rad, turn, buffer, dir, vel;
	
    public void run() {
		setColors(new Color(255,255,255), new Color(255,255,255), new Color(255,255,255));
		dir = 1;
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
		target = new Point2D.Double(getX(),getY());
        turnRadarLeftRadians(Double.POSITIVE_INFINITY);
    }
	
    public void onScannedRobot(ScannedRobotEvent e)
	{	

		buffer	= 36;
		field	= new Rectangle2D.Double(buffer/2, buffer/2, getBattleFieldWidth()-buffer, getBattleFieldHeight()-buffer);
		

		

		pos		= new Point2D.Double(getX(),getY());
		//ePos	= getPos(pos,e.getBearingRadians()+getHeadingRadians(),e.getDistance());
		vel		= Math.abs(getVelocity());
		rad		= vel/((10-0.75*vel)/(180/Math.PI));
		pivot 	= (turn>0) ? getPos(pos, getHeadingRadians()+Math.PI/2*dir, rad) : getPos(pos, getHeadingRadians()-Math.PI/2*dir, rad);	
		bbox	= new Rectangle2D.Double(pivot.x-rad, pivot.y-rad, rad*2, rad*2);	
		feeler	= (dir>0) ? getPos(pos, getHeadingRadians(), rad) : getPos(pos, getHeadingRadians()+Math.PI, rad);
		vel		= (!field.contains(bbox) && !field.contains(feeler)) ? vel-1 : 8; //if collision imminent reduce speed	
		turn 	= (dir>0) ? Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()) : Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()+Math.PI);
	

		Point2D.Double[] arr = {
	        new Point2D.Double(Math.random()*field.width, buffer),
	        new Point2D.Double(Math.random()*field.width, field.height),
	        new Point2D.Double(buffer, Math.random()*field.height),
	        new Point2D.Double(field.width, Math.random()*field.height)
	    };
	
		//set target location		
		if (pos.distance(target)<rad) target = arr[(int)(Math.random() * arr.length)];		
		//if (pos.distance(target)<rad) target = new Point2D.Double(randomNum((int)field.x, (int)field.width), randomNum((int)field.y, (int)field.height));


	
		//reverse direction if turn is more than PI
		//if (Math.abs(turn)>Math.PI/2) dir *= -1; //causes ping-pong when colliding with stuff
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians()+e.getBearingRadians()-getRadarHeadingRadians()));
		setTurnRightRadians(turn);	
		setMaxVelocity(vel);			
		setAhead(100*dir);



	}

	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance) {
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}

    private double absBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX()-source.getX(), target.getY()-source.getY());
    }

	int randomNum(int min, int max)	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	public void onHitRobot(HitRobotEvent e) {
		
		//I have no good solution for this
		target = getPos(pos, getHeadingRadians()+Math.PI, 150);
	}
	
	public void onPaint(Graphics2D g)
	{	
		g.setColor(new Color(255,0,0,50));		
		g.fillOval((int)(target.x-10),(int)(target.y-10),20,20);
		g.drawRect((int)field.x, (int)field.y, (int)field.width, (int)field.height);	
		
		g.setColor(new Color(0,255,0,50));
		g.drawLine((int)pos.x, (int)pos.y, (int)feeler.x, (int)feeler.y);
		
    	if(Math.abs(getTurnRemainingRadians())>0.15) {
			g.setColor(new Color(0,0,255,15));
			g.fillOval((int)(pivot.x-rad), (int)(pivot.y-rad), (int)(rad*2), (int)(rad*2));						
		}
	}
}
