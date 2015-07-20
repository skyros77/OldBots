package mo.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class PaintUtils {

	/*
	public static Graphics2D g = getGraphics();
	public PaintUtils(Graphics2D graphics) {
		g = graphics;
	}
	 */
	
	public static void drawPos(Graphics2D g, Point2D.Double pos) {
		g.setColor(new Color(255,0,0,255));
		g.fillOval((int)(pos.x-20),(int)(pos.y-20),40,40);
	}
}
