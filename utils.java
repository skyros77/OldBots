package mo.utils;
import java.awt.*;
import java.awt.geom.*;
 
public class debug {
 
	public static void paintTarget(Graphics2D g, Point2D.Double pos) {
		g.setColor(new Color(255,0,0,75));
		g.fillOval((int)pos.x-30, (int)pos.y-30, 60, 60);
	}
 
	public static void paintBoundary(Graphics2D g, Rectangle2D.Double f) {
		g.setColor(new Color(0,255,0,75));
		g.drawRect((int)f.x, (int)f.y, (int)f.width, (int)f.height);
	}
}
