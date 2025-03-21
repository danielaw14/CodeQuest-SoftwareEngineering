package edu.appstate.cs.codequest;

import java.awt.image.BufferedImage;

/**
 * Object representing the path
 */
public class PathObject extends GameObject {

	/**
	 * Constructor for pathObj
	 */
	public PathObject() {
		super(false);
		super.image = PathObject.getImage();
	}

	/**
	 * Reads png file and returns the image
	 * 
	 * @return returns image read for path object
	 */
	public static BufferedImage getImage() {
		return getImage("path.png");
	}
}
