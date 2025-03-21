package edu.appstate.cs.codequest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Class to display game images onto a JPanel
 */
public class DisplayBoard extends JPanel implements Runnable {

	// Sets values needed for screen display
	private final int originalTileSize = 16; // 16x16 tile
	private final int scale = 3;
	private final int tileSize = originalTileSize * scale; // 48x48 tile size
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 12;
	private final int screenWidth = tileSize * maxScreenCol; // 768 pixels aka 16 48x48 tiles
	private final int screenHeight = tileSize * maxScreenRow; // 576 pixels aka 12 48x48 tiles
	private final int FPS = 60;
	Level level;
	private int index = 0;
	static Boolean button = false;
	JButton levelButton = new JButton();
	private JTextPane instructionsPane = new JTextPane();

	// Board obj and GameOnject array of objects
	public Board board = new Board(maxScreenCol, maxScreenRow);
	Thread gameThread;

	// Constructor that sets JPanel Parameters
	DisplayBoard() throws IOException {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		level = Level.newLevel(index);
	}

	// sets level
	// temporary until level class is implemeted
	public void setIndex(int i) {
		index = i;
	}

	// temporary untili level class is implemented
	public int getIndex() {
		return index;
	}

	// creates a JTextPane to update
	public JTextPane getInstructionsPane() {
		return instructionsPane;
	}

	// sets button boolean to only update a new board when either
	// reset or nextLevel is pressed
	public void setButton(Boolean b) {
		button = b;
	}

	/**
	 * Method to start the game thread
	 * 
	 * @throws IOException
	 */
	public void startGame() throws IOException {
		gameThread = new Thread(this);
		gameThread.start();
		level = Level.newLevel(index);
		board = level.getBoard();
		instructionsPane.setText(App.getInstructions(index));
	}

	/**
	 * Method that updates and redraws the gui
	 * once every 60 seconds and prints the FPS
	 * AKA 60 fps
	 */
	public void run() {
		double drawInterval = 1000000000 / FPS; // 0.0166667 sec
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				try {
					update();
				} catch (IOException e) {
				}
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
		}
	}

	/**
	 * Method to be called to update the board with new information
	 * to be drawn.
	 * 
	 * @throws IOException
	 */
	void update() throws IOException {
		if (button == true) {
			level = Level.newLevel(index);
			board = level.getBoard();
			instructionsPane.setText(App.getInstructions(index));
			button = false;
		}
		if (!level.getIsAtGoal()) {
			levelButton.setText("Reset");
		} else if (level.getIsAtGoal()) {
			levelButton.setText("Next Level");
		}
	}

	/**
	 * method to draw images onto the entire screen set by
	 * maxScreenWidth and maxScreenHeight
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < maxScreenCol && row < maxScreenRow) {
			g2.drawImage(board.getObject(col, row).image, x, y, tileSize, tileSize, null);
			col++;
			x += tileSize;

			if (col == maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += tileSize;
			}
		}
	}

	/**
	 * Method to actually draw and deal with images using
	 * Graphics2D to paint the images onto the JPanel
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		draw(g2);

		g2.dispose();
	}
}
