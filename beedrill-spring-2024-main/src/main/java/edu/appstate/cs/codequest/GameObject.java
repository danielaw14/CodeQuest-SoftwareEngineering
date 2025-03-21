package edu.appstate.cs.codequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;;

/**
 * An object which represents the objects/characters/entities in the game.
 */
public abstract class GameObject {

	// Whether or not this object is an obstacle, and will block movement.
	protected boolean isObstacle;

	// BufferedImage object to store image objects
	protected BufferedImage image;

	/**
	 * Constructor for GameObject
	 * 
	 * @param isObstacle Whether this object is an obstacle
	 */
	GameObject(boolean isObstacle) {
		this.isObstacle = isObstacle;

	}// GameObject

	GameObject() {
		this.isObstacle = false;
	}

	/**
	 * Returns the isObstacle flag. If isObstacle, the player cannot traverse this
	 * area.
	 * 
	 * @return A bool flag marking whether this object can be traversed.
	 */
	public boolean getIsObstacle() {
		return isObstacle;
	}// getIsObstacle

	/**
	 * Overrides GameObject get image.
	 * Reads png file and returns the image
	 * 
	 * @return returns image read for goal object
	 */
	public static BufferedImage getImage(String fileName) {
		try {
			File wall = new File(Resource.resourcePath + "images/" + fileName);
			return ImageIO.read(wall);
		} catch (IOException e) {
			return null;
		}
	}
}// GameObject
