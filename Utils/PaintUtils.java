package mo.Utils;

import mo.Data.BotData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class PaintUtils {

	// CONSTRUCTOR

	// VARIABLES
	BotData myBot = new BotData();
	private Point2D.Double myPos = myBot.getPos();

	// ACCESSORS

	// METHODS
	public void drawPos(Graphics2D g, Point2D.Double ePos) {
		g.setColor(new Color(255, 0, 0, 200));
		g.fillOval((int) (ePos.x - 10), (int) (ePos.y - 10), 20, 20);
		g.drawLine((int) myPos.x, (int) myPos.y, (int) ePos.x, (int) ePos.y);
	}
}
