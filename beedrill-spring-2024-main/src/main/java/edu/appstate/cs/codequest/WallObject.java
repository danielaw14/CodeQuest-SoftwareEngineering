package edu.appstate.cs.codequest;

import java.awt.image.BufferedImage;

/**
 * Object representing the wall
 */
public class WallObject extends GameObject {

	/*
	 * Constructor for wallObj
	 */
	public WallObject() {
		super(true);
		super.image = WallObject.getImage();
	}

	/**
	 * Reads png file and returns the image
	 * 
	 * @return returns image read for wall object
	 */
	public static BufferedImage getImage() {
		return getImage("wall.png");
	}
}
