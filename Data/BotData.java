package mo.Data;

import robocode.*;
import java.awt.geom.Point2D;

public class BotData{

	//VARIABLES
	private AdvancedRobot myBot;
	private Point2D.Double myPos;
	private double headingRadians;
	private double radarHeadingRadians;
	private int getOthers;

	// CONSTRUCTORS
	
	public BotData() {
	}

	public BotData(AdvancedRobot robot) {
		myBot = robot;
		myPos = new Point2D.Double(myBot.getX(), myBot.getY());
		headingRadians = myBot.getHeadingRadians();
		radarHeadingRadians = myBot.getRadarHeadingRadians();
		getOthers = myBot.getOthers();
	}

	// ACCESSORS
	
	public AdvancedRobot getBot() {
		return this.myBot;
	}
	
	public Point2D.Double getPos() {
		return this.myPos;
	}

	public double getHeadingRadians() {
		return this.headingRadians;
	}

	public double getRadarHeadingRadians() {
		return this.radarHeadingRadians;
	}

	public int getOthers() {
		return this.getOthers;
	}

	// METHODS
	/*
	public void update() {	
		myPos = new Point2D.Double(myBot.getX(), myBot.getY());
		headingRadians = myBot.getHeadingRadians();
		radarHeadingRadians = myBot.getRadarHeadingRadians();
		getOthers = myBot.getOthers();
	}
	*/
}
