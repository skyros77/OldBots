
package mo;
import robocode.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.*;


public class Walls extends AdvancedRobot {
	private static final double	botWidth = 36;
	private static final double	arcRadius = 120;
	private static final Arc2D arc = new Arc2D.Double();
	private static Rectangle2D.Double field, fieldArc;	
	private static Point2D.Double pos, rotP, stick, feeler, dir;
	
	private static Point2D.Double pX, pY;
	//getBattleFieldWidth(), getBattleFieldHeight()

	//scaledValue = getVelocity()/8 //normalize velocity
	
	private static int quad;
	
	public void run() {


		setTurnLeft(getHeading() % 90);

		while (true) {

			pos = new Point2D.Double(getX(),getY());
			rotP = getPos(pos,getHeadingRadians()+(Math.PI/2),arcRadius);
			stick = getPos(pos,getHeadingRadians(),arcRadius);
			field = new Rectangle2D.Double(18,18,getBattleFieldWidth()-18,getBattleFieldHeight()-18);
			feeler = getFeeler(pos,getHeadingRadians(),200);
			Point2D.Double t = new Point2D.Double(0.0,getY());
		//	pY = new Point2D.Double(getX(),getBattleFieldHeight());
			
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
			
			Point2D.Double dir = (pos.distance(pX) > pos.distance(pY)) ? pX : pY;	
			double a = Math.atan2(pos.y - dir.y,pos.x - dir.x);
			out.println(getHeadingRadians() - a);
			
//get angle between heading and longest vector
			/*	
			if(coords==0)
			{
				pX=new Point2D.Double(getBattleFieldWidth(),pos.y);
				pY=new Point2D.Double(pos.x,getBattleFieldHeight());
			}
			if(coords==1)
			{
				pX=new Point2D.Double(getBattleFieldWidth(),pos.y);
				pY=new Point2D.Double(pos.x,0);
			} 
			if(coords==2)
			{
				pX=new Point2D.Double(0,pos.y);
				pY=new Point2D.Double(pos.x,0);
			} 
			if(coords==3)
			{
				pX=new Point2D.Double(0,pos.y);
				pY=new Point2D.Double(pos.x,getBattleFieldHeight());
			} 
*/
			/*
			if(coords==0) dir=new Point2D.Double(1,1);
			if(coords==1) dir=new Point2D.Double(1,-1);
			if(coords==2) dir=new Point2D.Double(-1,-1);
			if(coords==3) dir=new Point2D.Double(-1,1);	
			*/

			//out.println(getHeadingRadians());	

			doMove();
			

/*
 * if abs cos>sin vertical else horizontal

			double rot = Math.abs(Math.cos(getHeadingRadians()));
			if (rot<0.707 && rot>-0.707)
				out.println("right turn (" + rot + ")");
			else
				out.println("left turn (" + rot + ")");
				*/
			execute();
			
		}
	}
	

	public void doMove()
	{
		int quad = getQuadrant();
		if (stick.x<=field.x || stick.x>=field.width || stick.y<=field.y || stick.y>=field.height)
		{
			//	setTurnRight(getHeading() % 90);
			setTurnLeft(30);
		}
		setAhead(64);
	}
	
	public int getQuadrant()
	{
		double sin = Math.sin(getHeadingRadians());
		double cos = Math.cos(getHeadingRadians());
		
		if (sin<0 && cos>0) quad=1;
		if (sin>0 && cos<0) quad=3;
		if (sin<0 && cos<0) quad=2;
		if (sin>0 && cos>0) quad=4;

		//out.println("heading/sin/cos (" +quad+ ") : " +getHeading()+ "/" +sin+ "/" +cos);
		return quad;
	}
	
	public void onPaint(Graphics2D g)
	{	

		g.setColor(new Color(0,255,0,255));
		g.drawRect((int)field.x, (int)field.y, (int)(field.width-18), (int)(field.height-18));
			/*	
		g.setColor(new Color(255,0,0,100));
		g.drawRect((int)fieldArc.x, (int)fieldArc.y, (int)fieldArc.width, (int)fieldArc.height);

		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(rotP.x, rotP.y,arcRadius,0,360, Arc2D.PIE);
    	g.fill(arc);
		*/
		
/*
		g.setColor(new Color(0,0,255,100));
    	arc.setArcByCenter(pos.x, pos.y,arcRadius+18,0,360, Arc2D.OPEN);
    	g.draw(arc);
		
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
		g.drawLine((int)pos.x,(int)pos.y,(int)pX.x,(int)pX.y);
		g.drawLine((int)pos.x,(int)pos.y,(int)pY.x,(int)pY.y);
		g.drawLine((int)getX(),(int)getY(),(int)stick.x,(int)stick.y);
		
	}
	
	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
	
	private static Point2D.Double getFeeler(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		
		if (x<field.x)		x = field.x;
		if (x>field.width)	x = field.width;
		if (y<field.y)		y = field.y;
		if (y>field.height)	y = field.height;

		return new Point2D.Double(x, y);
	}
}
