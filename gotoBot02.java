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
import java.util.List;
import java.util.ArrayList;

public class GoToBot2 extends AdvancedRobot {
	Point2D.Double pos, ePos, pivot, feeler, eArc;
	Rectangle2D.Double field, bbox;
	double rad, turn, buffer, dir, vel, eDist, dist;
	//Arc2D.Double arc;
	int[] edge = new int[] {1,2,4,8};
	List<Point2D.Double> prev = new ArrayList<Point2D.Double>();
	
    public void run() {
		prev.clear();
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		//arc		= new Arc2D.Double();
		dir		= -1;
		buffer	= 19;
		field	= new Rectangle2D.Double(buffer, buffer, getBattleFieldWidth()-buffer*2, getBattleFieldHeight()-buffer*2);

		while (true) turnRadarLeftRadians(Double.POSITIVE_INFINITY);
    }

    public void onScannedRobot(ScannedRobotEvent e)
	{
	
		
		//scan
		setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians() - getRadarHeadingRadians() + e.getBearingRadians()));

		pos			= new Point2D.Double(getX(),getY());
		ePos 		= getPos(pos,e.getBearingRadians() + getHeadingRadians(), e.getDistance());
		eArc		= getPos(pos,e.getBearingRadians() + getHeadingRadians(), e.getDistance()/2);		
		eDist		= e.getDistance();
		dist 		= 300/eDist < 1 ? -.2 : .2;
		vel			= Math.abs(getVelocity());
		rad			= vel/((10-0.75*vel)/(180/Math.PI));

		pivot 		= getPos(pos, getHeadingRadians()-Math.PI/2, rad);
		bbox		= new Rectangle2D.Double(pivot.x-rad, pivot.y-rad, rad*2, rad*2);
		feeler		= getPos(pos, getHeadingRadians(), rad*dir);		
		vel			= (!field.contains(bbox) && !field.contains(feeler)) ? vel-1 : 8;
		turn 		= Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2 + (dist*dir));

		if (!field.contains(feeler))
			turn += Double.NEGATIVE_INFINITY*dir;

		prev.add(pos);
		if (prev.size()>50) prev.remove(0);	
	
		//turn 	= (!field.contains(bbox) && !field.contains(feeler)) ? turn : Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI/2);
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
		//if (getTime() % randomNum(5,30) == 0) dir *= -1;

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

	/*
    private double absBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX()-source.getX(), target.getY()-source.getY());
    }
	*/

	int randomNum(int min, int max)	{
	   int range = (max - min) + 1;
	   return (int)(Math.random() * range) + min;
	}

	public void onHitRobot(HitRobotEvent e){}

	public void onPaint(Graphics2D g)
	{
		g.setColor(new Color(255,0,0,100));
		for (int i=1; i < prev.size(); i++) {
			g.drawLine((int)prev.get(i).x, (int)prev.get(i).y, (int)prev.get(i-1).x, (int)prev.get(i-1).y);
		}

		g.setColor(new Color(255,0,0,100));
		g.fillOval((int)(ePos.x-6),(int)(ePos.y-6),12,12);
		g.drawLine((int)pos.x, (int)pos.y, (int)ePos.x, (int)ePos.y);
		g.drawRect((int)field.x, (int)field.y, (int)field.width, (int)field.height);
		
		g.setColor(new Color(0,255,0,100));
		g.drawOval((int)(eArc.x-eDist/2),(int)(eArc.y-eDist/2),(int)eDist,(int)eDist);
		
		g.setColor(new Color(0,255,0,100));
		g.drawLine((int)pos.x, (int)pos.y, (int)feeler.x, (int)feeler.y);

		//arc.setArcByCenter(ePos.x,ePos.y,eDist,0,360, Arc2D.OPEN);
		//g.draw(arc);
		
		//arc.setArcByCenter(arc.x, arc.y, eDist/2, 0, 360, Arc2D.OPEN);
		//g.draw(arc);		

    	//if(Math.abs(getTurnRemainingRadians())>0.15) {
			g.setColor(new Color(0,0,255,50));
			g.fillOval((int)(pivot.x-rad), (int)(pivot.y-rad), (int)(rad*2), (int)(rad*2));
		//}
	}
}
