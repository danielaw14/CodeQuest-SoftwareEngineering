package edu.appstate.cs.codequest;

import java.awt.image.BufferedImage;

/**
 * Object representing the end goal
 */
public class GoalObject extends GameObject {

	// x and y position of goal
	public static int getX;
	public static int getY;

	/**
	 * constructor for goalObj
	 */
	public GoalObject() {
		super(false);
		super.image = GoalObject.getImage();
	}

	/**
	 * constructor to set Goal location
	 * 
	 * @param x
	 * @param y
	 */
	public GoalObject(int x, int y) {
		super(false);
		super.image = GoalObject.getImage();
		getX = x;
		getY = y;
	}

	/**
	 * Reads png file and returns the image
	 * 
	 * @return returns image read for goal object
	 */
	public static BufferedImage getImage() {
		return getImage("goal.png");
	}
}
