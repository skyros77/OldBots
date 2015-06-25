
package mo;
import robocode.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.*;
import robocode.util.*;


public class Walls extends AdvancedRobot {
	private static final double	arcRadius = 138;
	private static final Arc2D arc = new Arc2D.Double();
	private static Rectangle2D.Double field;	
	private static Point2D.Double pos, tPos;
	
	private static Point2D.Double p1, p2, arcPos;
	//getBattleFieldWidth(), getBattleFieldHeight()

	//scaledValue = getVelocity()/8 //normalize velocity

	
	public void run() {

		//setTurnLeft(getHeading() % 90);

		while (true) {

			pos = new Point2D.Double(getX(),getY());
			tPos = getPos(pos,getHeadingRadians(),arcRadius);
			field = new Rectangle2D.Double(0,0,getBattleFieldWidth(),getBattleFieldHeight());
			
			int coords = (int)(Math.floor(getHeadingRadians()/(Math.PI/2)));
			switch (coords) {
	            case 0:  
					p1 = getPos(pos,Math.PI/2,arcRadius);
					p2 = getPos(pos,Math.PI*2,arcRadius);
	                break;
				case 1:	
					p1 = getPos(pos,Math.PI/2,arcRadius);
					p2 = getPos(pos,Math.PI,arcRadius);
					break;
				case 2:	
					p1 = getPos(pos,(3*Math.PI)/2,arcRadius);
					p2 = getPos(pos,Math.PI,arcRadius);
					break;
				default:	
					p1 = getPos(pos,(3*Math.PI)/2,arcRadius);
					p2 = getPos(pos,Math.PI*2,arcRadius);			
			}

/*
			//get direction of travel
			int coords = (int)(Math.floor(getHeadingRadians()/(Math.PI/2)));
			switch (coords) {
	            case 0:  
					pX=new Point2D.Double(getBattleFieldWidth(),pos.y);
					pY=new Point2D.Double(pos.x,getBattleFieldHeight());
	                break;
				case 1:	
					pX=new Point2D.Double(getBattleFieldWidth(),pos.y);
					pY=new Point2D.Double(pos.x,0);
					break;
				case 2:	
					pX=new Point2D.Double(0,pos.y);
					pY=new Point2D.Double(pos.x,0);
					break;
				default:	
					pX=new Point2D.Double(0,pos.y);
					pY=new Point2D.Double(pos.x,getBattleFieldHeight());
			}
		*/

/*
			//return collision edge points
			int coords = (int)(Math.floor(getHeadingRadians()/(Math.PI/2)));
			switch (coords) {
	            case 0:  
					a=new Point2D.Double(0,getBattleFieldHeight());
					b=new Point2D.Double(getBattleFieldWidth(),getBattleFieldHeight());
	                break;
				case 1:	
					a=new Point2D.Double(getBattleFieldWidth(),getBattleFieldHeight());
					b=new Point2D.Double(getBattleFieldWidth(),0);
					break;
				case 2:	
					a=new Point2D.Double(getBattleFieldWidth(),0);
					b=new Point2D.Double(0,0);
					break;
				default:	
					a=new Point2D.Double(0,0);
					b=new Point2D.Double(0,getBattleFieldHeight());
			}



			/* 
			* kind of does what I want
			//triangle area	
			double area = Math.abs((b.x-a.x)*(pos.y-a.y)-(b.y-a.y)*(pos.x-a.x));
			//length of AB
			double LAB = a.distance(b);
			//height of intersecting vector
			double h = area/LAB;
			// AB direction vector
			d = new Point2D.Double((b.x-a.x)/LAB,(b.y-a.y)/LAB);
			//tangent
			double t = d.x*(pos.x-a.x) + d.y*(pos.y-a.y);
			//distance of intersection from tangent
			double dt = Math.sqrt(arcRadius*2 - h*2);
			pA = new Point2D.Double(a.x+(t-dt)*d.x,a.y+(t-dt)*d.y);
			pB = new Point2D.Double(a.x+(t+dt)*d.x,a.y+(t+dt)*d.y);

			//tPos = (pos.distance(pX) > pos.distance(pY)) ? pX : pY;
			//Point2D.Double delta = new Point2D.Double(pos.x - tPos.x, pos.y - tPos.y);
			//double a = Math.atan2(delta.y, delta.x);
			out.println(d);
			*/
			
			//double a = Math.atan2(pos.y - deltay,pos.x - deltaX);
			//out.println(getHeadingRadians() - a);
			
			/*
			if(coords==0) dir=new Point2D.Double(1,1);
			if(coords==1) dir=new Point2D.Double(1,-1);
			if(coords==2) dir=new Point2D.Double(-1,-1);
			if(coords==3) dir=new Point2D.Double(-1,1);	
			*/

			//out.println(getHeading());	

			doMove();
			execute();
			
		}
	}
	

	public void doMove()
	{
		if (p1.x<=field.x || p1.x>=field.width) arcPos = p2;
		if (p2.y<=field.y || p2.y>=field.height) arcPos = p1;
		
/*
		//Point2D.Double delta = new Point2D.Double((pX.x - pos.x),(pX.y - pos.y));
		//double theta = Math.atan2(delta.y,delta.x);
		Point2D.Double delta = new Point2D.Double((stick.x - pos.x),(stick.y - pos.y));
		double theta = Math.atan2(delta.y,delta.x);
		out.println(theta +"/"+ getHeadingRadians());
		//g.drawLine((int)pos.x,(int)pos.y,(int)pX.x,(int)pX.y);
*/

		if (tPos.x<=field.x || tPos.x>=field.width || tPos.y<=field.y || tPos.y>=field.height)
		{
			//	setTurnRight(getHeading() % 90);
			setTurnRight(30);
		}

		setAhead(64);
	}
	

	
	public void onPaint(Graphics2D g)
	{	
			/*
		g.setColor(new Color(0,255,0,255));
		g.drawRect((int)field.x, (int)field.y, (int)(field.width-18), (int)(field.height-18));
	
		g.setColor(new Color(255,0,0,100));
		g.drawRect((int)fieldArc.x, (int)fieldArc.y, (int)fieldArc.width, (int)fieldArc.height);

		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(rotP.x, rotP.y,arcRadius,0,360, Arc2D.PIE);
    	g.fill(arc);
	
		g.setColor(new Color(255,0,0,100));
		g.drawLine((int)getX(), (int)getY(),(int)stick.x, (int)stick.y);
		
		g.setColor(new Color(255,255,255,255));
		g.drawLine((int)getX(), (int)getY(),(int)stick.x,(int)getY() );
		g.drawLine((int)getX(), (int)getY(),(int)getX(),(int)stick.y );
		
		g.setColor(new Color(255,255,255,255));
		g.drawLine((int)getX(), (int)getY(),(int)feeler.x,(int)feeler.y );
		
		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(feeler.x, feeler.y,arcRadius+18,0,360, Arc2D.OPEN);
    	g.draw(arc);
		*/

		g.setColor(new Color(255,0,0,100));
		g.drawLine((int)pos.x,(int)pos.y,(int)p1.x,(int)p1.y);
		g.setColor(new Color(0,255,0,100));
		g.drawLine((int)pos.x,(int)pos.y,(int)p2.x,(int)p2.y);
		g.setColor(new Color(0,0,255,100));		
		g.drawLine((int)pos.x,(int)pos.y,(int)tPos.x,(int)tPos.y);

		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(arcPos.x, arcPos.y,arcRadius,0,360, Arc2D.OPEN);
    	g.draw(arc);
		
		//g.fillOval((int)tPos.x-10, (int)tPos.y-10, 20, 20);
		//g.fillOval((int)pA.x-6, (int)pA.y-6, 12, 12);
		//g.fillOval((int)d.x-6, (int)d.y-6, 12, 12);
		//g.fillOval((int)pB.x-10, (int)pB.y-10, 20, 20);
		//g.fillOval((int)pA.x-10, (int)pA.y-10, 20, 20);
		
	}
	
	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
	
	private void goTo(Point2D destination) {
		double angle = Utils.normalRelativeAngle(absBearing(pos,destination) - getHeadingRadians());
		double turnAngle = Math.atan(Math.tan(angle));
		setTurnRightRadians(turnAngle);
	}
	
    static double absBearing(Point2D source, Point2D target) {
        return Math.atan2(target.getX() - source.getX(), target.getY() - source.getY());
    }
}
