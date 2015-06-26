/* turn radius based on velocity
 * 8: 114.627
 * 7: 84.464
 * 6: 62.519
 * 5: 45.862
 * 4: 32.764
 * 3: 22.186
 * 2: 13.492
 * 1: 6.205
 * 0: 0
 */

package mo;
import robocode.*;
import java.awt.geom.*;
import java.awt.geom.Arc2D;
import java.awt.Color;
import java.awt.Graphics2D;

public class SpinBot extends AdvancedRobot {
	Arc2D arc = new Arc2D.Double();
	Point2D.Double pos, pivot;
	double radius;

	public void run() {
		while (true) {
			//out.println(10 - 0.75 * Math.abs(getVelocity()));		
			setMaxVelocity(1);		
			setTurnRight(100);
			setAhead(100);

			
			radius = 6.205;
			pos = new Point2D.Double(getX(),getY());
			pivot = getPos(pos, getHeadingRadians()+(Math.PI/2), radius);
			out.println("pivot/pos: " +pivot+ "/" +pos);
			
			scan();
		}
	}

	private static Point2D.Double getPos(Point2D.Double pos, double angle, double distance) {
		double x = pos.x + distance * Math.sin(angle);
		double y = pos.y + distance * Math.cos(angle);
		return new Point2D.Double(x, y);
	}

	public void onPaint(Graphics2D g) {
		g.setColor(new Color(255,255,255,150));
		g.drawLine((int)pos.x,(int)pos.y,(int)pivot.x,(int)pivot.y);
    	arc.setArcByCenter(pivot.x, pivot.y,radius,0,360, Arc2D.OPEN);
    	g.draw(arc);
	}
}	
