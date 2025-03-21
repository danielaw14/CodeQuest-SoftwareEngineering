package edu.appstate.cs.codequest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class InterpreterTest {
	// TODO:

	private Board board = null;
	private Interpreter inter = null;
	private int x = -1;
	private int y = -1;

	private boolean initInterpreter() {
		board = null;
		inter = null;
		board = new Board();

		if (board == null)
			return false;

		x = 1;
		y = 1;

		for (int i = 0; i < board.board.length; i++) {
			for (int j = 0; j < board.board[i].length; j++)
				board.setObject(new PathObject(), j, i);
		}

		board.setObject(new GoalObject(), x + 1, y + 1);
		board.setObject(new PlayerObject(board, x, y), x, y);

		inter = new Interpreter(board.getPlayer(), board);
		return true;
	}

	/* MOVE TESTS */

	@Test
	public void testMoveUpCommand() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		try {
			inter.parse("moveU");
		} catch (Exception e) {
			e.printStackTrace();
			fail("IterpreterException occured");
			return;
		}
		assertTrue(board.getObject(x, y - 1) instanceof PlayerObject, "Failed to move player up");
	}

	@Test
	public void testMoveDownCommand() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		try {
			inter.parse("moveD");
		} catch (Exception e) {
			fail("IterpreterException occured");
			return;
		}
		assertTrue(board.getObject(x, y + 1) instanceof PlayerObject, "Failed to move player down");
	}

	@Test
	public void testMoveLeftCommand() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		try {
			inter.parse("moveL");
		} catch (Exception e) {
			fail("IterpreterException occured");
			return;
		}
		assertTrue(board.getObject(x - 1, y) instanceof PlayerObject, "Failed to move player left");
	}

	@Test
	public void testMoveRightCommand() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		try {
			inter.parse("moveR");
		} catch (Exception e) {
			fail("IterpreterException occured");
			return;
		}
		assertTrue(board.getObject(x + 1, y) instanceof PlayerObject, "Failed to move player right");
	}

	/* CONDITIONAL TESTS */

	@Test
	public void testIsOnGoal() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		assertTrue(!board.getPlayer().getIsAtGoal(), "Player is at goal but should not be");

		try {
			inter.parse("moveD\nmoveR");
		} catch (Exception e) {
			fail("IterpreterException occured");
			return;
		}
		assertTrue(board.getPlayer().getIsAtGoal(), "Player is not on goal but should be");
	}

	@Test
	public void testCanMove() {
		if (!initInterpreter()) {
			fail("Failed to init the interpreter");
			return;
		}

		PlayerObject player = board.getPlayer();
		assertTrue(player.canMove('U'), "Reported player can't move up, but should be able to");
		assertTrue(player.canMove('D'), "Reported player can't move down, but should be able to");
		assertTrue(player.canMove('L'), "Reported player can't move left, but should be able to");
		assertTrue(player.canMove('R'), "Reported player can't move right, but should be able to");
		board.setObject(new WallObject(), x - 1, y);
		board.setObject(new WallObject(), x + 1, y);
		board.setObject(new WallObject(), x, y - 1);
		board.setObject(new WallObject(), x, y + 1);
		assertTrue(!player.canMove('U'), "Reported player can move up, but shouldn't be able to");
		assertTrue(!player.canMove('D'), "Reported player can move down, but shouldn't be able to");
		assertTrue(!player.canMove('L'), "Reported player can move left, but shouldn't be able to");
		assertTrue(!player.canMove('R'), "Reported player can move right, but shouldn't be able to");
	}
}
