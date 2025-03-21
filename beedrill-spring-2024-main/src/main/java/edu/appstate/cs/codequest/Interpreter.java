package edu.appstate.cs.codequest;

import java.util.ArrayList;

public class Interpreter {
	private PlayerObject player;

	private boolean isInterpreting;

	ArrayList<String> lines = new ArrayList<>();

	public Interpreter(PlayerObject player, Board board) {
		this.player = player;
		isInterpreting = false;
	}

	/**
	 * Parses the string param, and returns a string with the completion/error
	 * message. Tries to parse and run all the provided commands. If an error
	 * occurs, the message will be returned.
	 * 
	 * @param str The command/script the player entered.
	 * @return The completion/error message as a result of parsing the string.
	 */
	public void parse(String str) throws InterpretingException {
		if (isInterpreting)
			return;
		try {
			isInterpreting = true;
			String[] tmpAr = str.split("\n");
			lines = new ArrayList<String>();
			for (String tmpStr : tmpAr)
				lines.add(tmpStr.trim());

			// Run the code
			parse(0, lines.size());

			// while (lines.size() > 0) {
			// parseLine();
			// }
		} catch (Exception e) {
			throw e;
		}
	}// parse(String)

	private void parse(int start, int end) throws InterpretingException {
		while (start < end) {
			String line = lines.get(start);
			// Handle comments and empty lines.
			if (line.startsWith("#") || line.isEmpty()) {
				start++;
				continue;
			}

			// Handle admin commands
			else if (line.startsWith("admin ")) {
				switch (line.split(" ")[1]) {
					case "forceComplete":
						boolean condition = parseCondition(line.split(" ")[2]);
						Level.getLevel().setComplete(condition);
				}
			}

			// Handle movement
			else if (line.startsWith("move")) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				switch (line.substring("move".length())) {
					case "U":
						player.move("up");
						break;
					case "D":
						player.move("down");
						break;
					case "L":
						player.move("left");
						break;
					case "R":
						player.move("right");
						break;
					default:
						throw new InterpretingException("Missing move direction, IE `moveU`");
				}
			}

			// Handle if
			else if (line.startsWith("if ")) {
				// Remove the 'if ' and the ':'
				boolean condition = parseCondition(
						line.substring(0, line.length() - 1).substring("if ".length()));
				int fi = findEndIf(start, end);
				if (condition) {
					parse(start + 1, fi);
				}
				start = fi;
			}

			// Handle while
			else if (line.startsWith("while ")) {
				// Remove the 'if ' and the ':'
				String conditionString = line.substring(0, line.length() - 1).substring("while ".length());
				int elihw = findEndWhile(start, end);
				while (parseCondition(conditionString)) {
					parse(start + 1, elihw);
				}
				start = elihw;
			}

			// Else, throw exception
			else
				throw new InterpretingException("Unknown command '" + line + "'");

			start++;
		}
	}

	private int findEndIf(int start, int end) throws InterpretingException {
		int cnt = 0;
		int loc = start + 1;
		while (!(lines.get(loc).startsWith("fi") && cnt == 0)) {
			if (loc >= end)
				throw new InterpretingException("LINE: " + start + " Unable to find closing fi");

			if (lines.get(loc).startsWith("if "))
				cnt++;
			else if (lines.get(loc).startsWith("fi"))
				cnt--;
			loc++;
		}
		return loc;
	}

	private int findEndWhile(int start, int end) throws InterpretingException {
		int cnt = 0;
		int loc = start + 1;
		while (!(lines.get(loc).startsWith("elihw") && cnt == 0)) {
			if (loc >= end)
				throw new InterpretingException("LINE: " + start + " Unable to find closing elihw");

			if (lines.get(loc).startsWith("while "))
				cnt++;
			else if (lines.get(loc).startsWith("elihw"))
				cnt--;
			loc++;
		}
		return loc;
	}

	/**
	 * Parses the passed condition, and returns true or false depending on the
	 * condition.
	 * 
	 * @param str
	 * @return True or false depending on the provided condition.
	 */
	private boolean parseCondition(String str) throws InterpretingException {
		boolean condition = false;
		boolean negated = str.startsWith("!");
		if (negated)
			str = str.substring(1);
		switch (str) {
			case "isOnGoal":
				condition = player.getIsAtGoal();
				break;
			case "canMoveU":
				condition = player.canMove('U');
				break;
			case "canMoveD":
				condition = player.canMove('D');
				break;
			case "canMoveL":
				condition = player.canMove('L');
				break;
			case "canMoveR":
				condition = player.canMove('R');
				break;
			case "true":
				condition = true;
				break;
			case "false":
				condition = false;
				break;
			default:
				throw new InterpretingException("Unknown condition `" + str + "`");
		}
		if (!negated)
			return condition;
		else
			return !condition;
	}

}// Interpreter

class InterpretingException extends Exception {
	String messageString;

	InterpretingException() {
		this("");
	}

	InterpretingException(String msg) {
		super(msg);
		messageString = msg;
	}
}