package edu.appstate.cs.codequest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

/**
 * Main sets up and displays the JFrame
 */
public class App {

	static String inputStr = "";
	protected static boolean b;

	/**
	 * Main method that sets up the JFram and start the game
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Code Quest");

		// calls DisplayBoard class to get the JPanel
		DisplayBoard gameBoard = new DisplayBoard();
		window.add(gameBoard);

		// Split pane to display instructions on top and input area on bottom
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		// text pane to dispay instruction noneditable to users
		JTextPane instructionsPane = gameBoard.getInstructionsPane();
		instructionsPane.setPreferredSize(new Dimension(350, 290));
		instructionsPane.setMaximumSize(new Dimension(350, 290));
		instructionsPane.setEditable(false);

		// Panel to hold text area and submit button
		JPanel textAreaJPanel = new JPanel();

		// Text area to allow user input. Set specific max size and linewrap
		JTextArea inputArea = new JTextArea();
		inputArea.setRows(13);
		// inputArea.setPreferredSize(new Dimension(350, 220));
		// inputArea.setMaximumSize(new Dimension(350, 220));
		inputArea.setLineWrap(true);
		inputArea.setTabSize(4);
		inputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		// Buttons for users to press
		JButton submitButton = new JButton();
		submitButton.setText("Submit");
		JButton levelButton = gameBoard.levelButton;

		// adds text area and submit button to panel
		textAreaJPanel.setLayout(new BorderLayout());
		// textAreaJPanel.add(inputArea, BorderLayout.NORTH);
		textAreaJPanel.add(new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.NORTH);
		textAreaJPanel.add(submitButton, BorderLayout.CENTER);

		// temporary until movement is done
		// Will test for player at goal and add the appropriate button.
		textAreaJPanel.add(levelButton, BorderLayout.SOUTH);
		levelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameBoard.setButton(true);
				if (levelButton.getText().equals("Reset")) {
					gameBoard.setIndex(gameBoard.getIndex());
					inputArea.setText("");
				} else {
					gameBoard.setIndex(gameBoard.getIndex() + 1);
				}
			}
		});

		// adds instructions panel and text area panel to the top/botton of
		// the split area
		splitPane.setTopComponent(new JScrollPane(instructionsPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 31));
		splitPane.setBottomComponent(textAreaJPanel);

		// sets a defined black border between the two areas
		splitPane.setBorder(BorderFactory.createLineBorder(Color.black));

		// adds the total split pane to the window frame
		window.add(splitPane, BorderLayout.EAST);

		// \starts the game thread from DisplayBoard
		gameBoard.startGame();

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// Action Listner to read user input when enter is pressed
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String getInput = inputArea.getText();
				// Create a new thread for updating the movement to prevent locking the
				// displaying.
				Thread t1 = new Thread(new Runnable() {
					public void run() {
						gameBoard.level.runCode(getInput);
					}
				});
				t1.start();
			}
		});
	}

	/**
	 * gets and returns instructions from corresponding level file
	 * and adds it to the JTextPane
	 * 
	 * @return returns Instructions String
	 */
	public static String getInstructions(int index) {
		String instr = ReadInstructions.readFile(Integer.toString(index));
		return instr;
	}
}
