/* TODO:
 * if speed is below a set tolerance reverse direction instead
 * reversing direction when turn is ore than PI causes ping pong on collision
 */

package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;


public class GoToBot2 extends AdvancedRobot {
	Point2D.Double target, pos, ePos, pivot, feeler;
	Rectangle2D.Double field, bbox;
	double rad, turn, buffer, dir, vel, eDist, dist;
	Arc2D.Double arc;
	int[] edge = new int[] {1,2,4,8};
	
    public void run() {
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);	
		arc		= new Arc2D.Double();
		dir		= 1;
		buffer	= 18;
		field	= new Rectangle2D.Double(buffer, buffer, getBattleFieldWidth()-buffer*2, getBattleFieldHeight()-buffer*2);		
		//target	= new Point2D.Double(getX(),getY());

		while (true) turnRadarLeftRadians(Double.POSITIVE_INFINITY);
    }
	
    public void onScannedRobot(ScannedRobotEvent e)
	{	
	
		//scan
		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians() - getRadarHeadingRadians() + e.getBearingRadians()));
		
		pos	= new Point2D.Double(getX(),getY());
		ePos 	= getPos(pos,e.getBearingRadians() + getHeadingRadians(), e.getDistance());
		eDist	= e.getDistance();
		dist 	= 200/eDist < 1 ? -.2 : .2;			
		vel	= Math.abs(getVelocity());
		rad	= vel/((10-0.75*vel)/(180/Math.PI));

		pivot 	= getPos(pos, getHeadingRadians()-Math.PI/2, rad);	
		bbox	= new Rectangle2D.Double(pivot.x-rad, pivot.y-rad, rad*2, rad*2);	
		feeler	= getPos(pos, getHeadingRadians(), 115*dir);	
		vel	= (!field.contains(bbox)) ? vel-1 : 8; //if collision imminent reduce speed

		turn	= Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2 + (dist*dir));
		
		if (!field.contains(feeler))
			turn -= Double.POSITIVE_INFINITY*dir;
	
		//temp
		target = ePos;	

	//	turn 	= (!field.contains(bbox) && !field.contains(feeler)) ? turn : Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2);

	
		//turn	= (!field.contains(bbox) && !field.contains(feeler)) ? Utils.normalRelativeAngle(e.getBearingRadians() - (distance*dir)) :  Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2 - (distance*dir));	
		//turn 	= (dir>0) ? Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians() + Math.PI/2) : Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians() - Math.PI/2);
		//turn 	= (dir>0) ? Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()) : Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()+Math.PI);
		//turn = Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2 - (distance*dir));
		//turn = Utils.normalRelativeAngle(e.getBearingRadians()); //gotoTarget
		//check which edge my bot collides on
	
/*	
		if(!field.contains(bbox)) {
			for(int i : edge ) {
				if (field.outcode(feeler) == i) {
					turn = (dir>0) ? Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()) : Utils.normalRelativeAngle(absBearing(pos, target)-getHeadingRadians()+Math.PI);
				}
			}		
		}
*/
					
/*	
	if(!field.contains(bbox)) {
			int edge = field.outcode(feeler);
	        switch (edge) {
	            case 1:  out.println("left");
	            break;
				
				case 2:  out.println("bottom");
	            break;
				
				case 4:  out.println("right");
	            break;
				
				case 8:  out.println("top");
	            break;
				
				default: out.println("none");
			}
			
		}
		*/

		/*
		//set test location
		Point2D.Double[] arr = {
	        new Point2D.Double(Math.random()*field.width+buffer, buffer),
	        new Point2D.Double(Math.random()*field.width+buffer, field.height+buffer),
	        new Point2D.Double(buffer, Math.random()*field.height+buffer),
	        new Point2D.Double(field.width+buffer, Math.random()*field.height+buffer)
	    };		
		*/
		


		

		//set target location		
		//if (pos.distance(target)<rad) target = arr[(int)(Math.random() * arr.length)];	//random edge point	
		//if (pos.distance(target)<rad) target = new Point2D.Double(randomNum((int)field.x, (int)field.width), randomNum((int)field.y, (int)field.height)); //random field point

		//reverse direction if turn is more than PI
		//if (Math.abs(turn)>Math.PI/2) dir *= -1;
		
		//randomly reverse direction
		//if (getTime() % randomNum(20,40) == 0) dir *= -1;
		
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
		target = getPos(pos, getHeadingRadians()+Math.PI, 150); //I have no good solution for this
	}
	
	public void onPaint(Graphics2D g)
	{	
		g.setColor(new Color(255,0,0,100));		
		g.fillOval((int)(target.x-10),(int)(target.y-10),20,20);
		g.drawLine((int)pos.x, (int)pos.y, (int)target.x, (int)target.y);
		g.drawRect((int)field.x, (int)field.y, (int)field.width, (int)field.height);	
		
		g.setColor(new Color(0,255,0,100));
		g.drawLine((int)pos.x, (int)pos.y, (int)feeler.x, (int)feeler.y);
		
		arc.setArcByCenter(ePos.x,ePos.y,eDist,0,360, Arc2D.OPEN);
		g.draw(arc);
		
    	//if(Math.abs(getTurnRemainingRadians())>0.15) {
			g.setColor(new Color(0,0,255,50));
			g.fillOval((int)(pivot.x-rad), (int)(pivot.y-rad), (int)(rad*2), (int)(rad*2));						
		//}
	}
}
