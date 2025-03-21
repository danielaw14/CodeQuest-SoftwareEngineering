package edu.appstate.cs.codequest;

import java.awt.image.BufferedImage;

/**
 * Object representing the player.
 */
public class PlayerObject extends GameObject {

	// Players current x & y position.
	private int x;
	private int y;
	private Boolean isAtGoal;

	// Reference to the game board.
	private Board board;

	/**
	 * Constructor for playerObject
	 * 
	 * @param board  The game board for the player.
	 * @param StartX The initial x-coordinate of the player.
	 * @param StartY the initial y-coordinate of the player.
	 */
	public PlayerObject(Board board, int StartX, int StartY) {
		super(false);
		super.image = PlayerObject.getImage();
		this.board = board;
		this.x = StartX;
		this.y = StartY;
		isAtGoal = !board.getHasGoal();
	}

	/**
	 * returns player x postion
	 * @return int x
	 */
	public int getPlayerX()
	{
		return x;
	}

	/**
	 * return player y position
	 * @return int y
	 */
	public int getPlayerY()
	{
		return y;
	}
	
	/**
	 * Moves the player in the specified direction on the game board.
	 * 
	 * @param dir The Direction in which the player should move in.
	 */
	public void move(String dir) {
		int newX = x;
		int newY = y;

		// Player movement
		switch (dir) {
			case "up":
				if (y > 0 && board.canMove(x, y - 1)) {
					newY--;
				}
				break;
			case "down":
				if (y < board.getHeight() - 1 && board.canMove(x, y + 1)) {
					newY++;
				}
				break;
			case "left":
				if (x > 0 && board.canMove(x - 1, y)) {
					newX--;
				}
				break;
			case "right":
				if (x < board.getWidth() - 1 && board.canMove(x + 1, y)) {
					newX++;
				}
				break;
		}

		// Replaces existing position with a path object and moves the player to the new
		// coordinates in the board array.
		if (newX != x || newY != y) {
			if (isAtGoal && board.getHasGoal()) {
				board.setObject(new GoalObject(), x, y);
				isAtGoal = false;
			} else if (isAtGoal && !board.getHasGoal()) {
				board.setObject(new PathObject(), x, y);
				isAtGoal = true;
			} else {
				board.setObject(new PathObject(), x, y);
				if (board.getObject(newX, newY) instanceof GoalObject)
					isAtGoal = true;
			}
			x = newX;
			y = newY;
			board.setObject(this, x, y);
		}
	}

	/*
	 * Test if the players location is the same as the goals
	 */
	public boolean getIsAtGoal() {
		return isAtGoal;
	}

	/**
	 * Reads png file and returns the image
	 * 
	 * @return returns image read for player object
	 */
	public static BufferedImage getImage() {
		return getImage("player.png");
	}

	/**
	 * Checks whether the player can move in a given direction, returns true if yes
	 * and false otherwise. 'U' up, 'D' down, 'L' left, 'R' right
	 * 
	 * @param direction
	 * @return True if the player can move that direction, false otherwise.
	 */
	public boolean canMove(char direction) {
		switch (direction) {
			case 'U':
				return board.canMove(x, y - 1);
			case 'D':
				return board.canMove(x, y + 1);
			case 'L':
				return board.canMove(x - 1, y);
			case 'R':
				return board.canMove(x + 1, y);
			default:
				return false;
		}
	}
}
