package mo;
import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

public class PatternMatcher extends AdvancedRobot
{

	private static final double	FIRE_POWER		= .1;
	private static final double	FIRE_SPEED		= Rules.getBulletSpeed(FIRE_POWER); //20 - (3 * power)
	
	static StringBuilder pattern = new StringBuilder(0);
	private static List<Point2D.Double>	predictions	= new ArrayList<Point2D.Double>();
	static double pHeading;
	static Point2D.Double enemyPos;
	static Point2D.Double myPos;
	static char[] cArr;
	public void run()
	{
		predictions.clear();
		setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);	
		turnRadarLeftRadians(Double.POSITIVE_INFINITY);

		do {scan();}
		while (true);
	}
	
	public void onScannedRobot(ScannedRobotEvent e)
	{
	
		//scan
 	    setTurnRadarRightRadians(Utils.normalRelativeAngle(getHeadingRadians()+e.getBearingRadians()-getRadarHeadingRadians()));
		

		
		//record enemy info
		double cHeading = e.getHeadingRadians();
		double cVelocity = e.getVelocity();
		char symbol = (char)((19*(Math.rint(Math.toDegrees(cHeading-pHeading)+10)))+(cVelocity+8));
		pHeading = e.getHeadingRadians();		
		pattern.append(symbol);
		
		if (pattern.length()>500) pattern.deleteCharAt(0);		

		String match = pattern.substring(pattern.length()-30, pattern.length());
		int index = pattern.indexOf(match);
		
		//get Positions
		myPos = new Point2D.Double(getX(), getY());
		enemyPos = getPos(myPos,e.getBearingRadians()+getHeadingRadians(),e.getDistance());
		
		predictions.clear();
		//predict
		out.println("-- new scan --");
		int dist;
		for(int i=index; i<(dist = (int)(Math.rint(myPos.distance(enemyPos)/FIRE_SPEED))); i++) {
					
			char s = pattern.charAt(i);
			int p = (int)s;
			cHeading += Math.toRadians(p/19-10);
			double v = p%19-8;
			
			enemyPos = getPos(enemyPos, cHeading, v);
			predictions.add(enemyPos);

			out.println(
				"iteration: " +i+ "\n" +
				"offset: " + dist + "\n" +
				"pattern(" +pattern.length()+ "): " +pattern+ "\n" +
				"match(" +match.length()+ "): " +match+ "\n" +
				"symbol H: (" + p + ")" +  s + "\n" +
				"initial H: " + e.getHeadingRadians() + "\n" +	
				"decoded H/V: " + Math.toRadians(p/19-10) + "/" + cVelocity + "\n" +
				"combined H/V: " + cHeading + "/" + cVelocity + "\n\n"
			);
		}

		//turn gun
		double absoluteBearing = Math.atan2(enemyPos.x - myPos.x, enemyPos.y - myPos.y);
		double gunTurn = absoluteBearing - getGunHeadingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
		
		//get previous heading

		
 
	}
	
	public void onPaint(Graphics2D g) {	
		
		g.setColor(Color.WHITE);
		for (Point2D.Double p : predictions)
		{
			g.fillOval((int) p.x - 2, (int) p.y - 2, 4, 4);
		}
		
		g.setColor(Color.RED);
		g.fillOval((int)enemyPos.x-8, (int)enemyPos.y-8, 16, 16);
	}

	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance)
	{
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}
}
