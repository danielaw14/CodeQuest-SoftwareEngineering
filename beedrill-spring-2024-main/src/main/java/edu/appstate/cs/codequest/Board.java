/**
 * Board.java
 * (c) Team Beedrill 2024-03-27
 * Please refer to the license file
 */
package edu.appstate.cs.codequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {

	public static final int WIDTH = 16;
	public static final int HEIGHT = 12;

	/*
	 * row: y, col: x
	 * [[a,b,c],
	 * [a,b,c],
	 * [a,b,c]]
	 */
	GameObject[][] board;
	private static String filePath = Resource.resourcePath + "maps/";
	private PlayerObject player;
	private boolean hasGoal = false;

	/**
	 * Initialized the board with a width of 16 and height of 12.
	 */
	Board() {
		board = new GameObject[HEIGHT][WIDTH];
	}// Board()

	/**
	 * Initialized the board with input paramaters
	 * for width and height.
	 */
	Board(int x, int y) {
		board = new GameObject[y][x];
	}// Board(int x, int y)

	/**
	 * Creates a board from a map file.
	 * 
	 * @param level The level number to read from the map file.
	 * @return A new Board Object made from the map file.
	 * @throws IOException if an I/O error occured while reading file.
	 */
	public static Board createBoardFromFile(String level) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath + level));
		String line;

		Board board = new Board();

		int y = 0;
		while ((line = reader.readLine()) != null && y < board.getHeight()) {
			for (int x = 0; x < line.length() && x < board.getWidth(); x++) {
				char symbol = line.charAt(x);
				GameObject gameObject;
				switch (symbol) {
					case ' ':
						gameObject = new PathObject();
						break;
					case 'w':
						gameObject = new WallObject();
						break;
					case '=':
						gameObject = new GoalObject(x, y);
						board.hasGoal = true;
						break;
					case '+':
						board.player = new PlayerObject(board, x, y);
						gameObject = board.player;
						break;
					default:
						System.out.println("Error reading symbol from File");
						gameObject = null;
						break;
				}
				board.board[y][x] = gameObject;
			}
			y++;
		}
		reader.close();

		return board;
	}

	/**
	 * Returns the width of the board.
	 * 
	 * @return An int width of the board
	 */
	public int getWidth() {
		return board[0].length;
	}// getWidth

	/**
	 * Returns the height of the board.
	 * 
	 * @return An int height of the board
	 */
	public int getHeight() {
		return board.length;
	}// getHeight

	/**
	 * Updates specified object at specified position.
	 * 
	 * @param object The object to be set.
	 * @param x      The x-position to set the object.
	 * @param y      The y-position to set the object.
	 */
	public void setObject(GameObject object, int x, int y) {

		if (board[y][x] instanceof GoalObject)
			hasGoal = false;

		board[y][x] = object;
		if (object instanceof PlayerObject)
			player = (PlayerObject) object;
		else if (object instanceof GoalObject)
			hasGoal = true;
	}

	/**
	 * Returns the game object at the specified position.
	 *
	 * @param x The x coordinate of the position
	 * @param y The y coordinate of the position
	 * @return A game object at the specified position, or null if no object is
	 *         found
	 */
	public GameObject getObject(int x, int y) {
		return board[y][x];
	}// getObject(int, int)

	/**
	 * Returns the PlayerObject from the board.
	 * 
	 * @return The PlayerObject from the board.
	 */
	public PlayerObject getPlayer() {
		return player;
	}

	/**
	 * A getter for hasGoal. Returns true if the board has a goal, and false
	 * otherwise.
	 * 
	 * @return True if the board has a goal, false otherwise.
	 */
	public boolean getHasGoal() {
		return hasGoal;
	}

	/**
	 * Returns whether or not a specified tile can be moved to. Returns true of the
	 * specified tile
	 * can be moved to, false otherwise.
	 * 
	 * @param x The x coordinate of the specified tile
	 * @param y The y coordinate of the specified tile
	 * @return A boolean representing whether the tile can be moved to
	 */
	public boolean canMove(int x, int y) {
		return inBounds(x, y) && (getObject(x, y) == null || !getObject(x, y).getIsObstacle());
	}

	public boolean inBounds(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
	}

}// Board
