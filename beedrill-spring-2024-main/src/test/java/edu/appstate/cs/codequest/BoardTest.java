package edu.appstate.cs.codequest;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    final static private int EXPECTEDHEIGHT = 12;
    final static private int EXPECTEDWIDTH = 16;

    static private Board board;

    @Test
    public void testTrue() {
        assertTrue(true, "This should never fail");
    }

    @Test
    public void testBoardSize() {
        // create empty board
        board = new Board();

        assertEquals(EXPECTEDWIDTH, board.getWidth());
        assertEquals(EXPECTEDHEIGHT, board.getHeight());
    }

    @Test
    public void testCanMove() {
        board = new Board();

        GameObject object = new WallObject();
        board.setObject(object, 0, 0);
        GameObject goal = new GoalObject();
        board.setObject(goal, 0, 1);
        GameObject path = new PathObject();
        board.setObject(path, 1, 1);

        int[][] testCases = {
                { 0, 0, 0 },
                { 1, 0, 1 },
                { 0, 1, 1 },
                { 1, 1, 1 }
        };

        for (int[] testCase : testCases) {
            int x = testCase[0];
            int y = testCase[1];
            int expected = testCase[2];
            int returned = board.canMove(x, y) ? 1 : 0;
            assertEquals(expected, returned);
        }

    }

    @Test
    public void testInBounds() {
        // create empty board
        board = new Board();
        // array of test cases (x, y, expectedBool)
        int[][] testCases = {
                { 0, 0, 1 },
                { 15, 0, 1 },
                { 0, 11, 1 },
                { 15, 11, 1 },
                { 16, 0, 0 },
                { 0, 12, 0 }
        };
        // test every case in array
        for (int[] testCase : testCases) {
            int x = testCase[0];
            int y = testCase[1];
            int expected = testCase[2];
            // convert bool to int so we can check
            int result = board.inBounds(x, y) ? 1 : 0;
            assertEquals(expected, result);
        }
    }

    @Test
    public void testSetObject() {
        // create empty board
        board = new Board();

        GameObject object = new WallObject();
        board.setObject(object, 3, 4);

        GameObject foundObject = board.getObject(3, 4);
        assertEquals(object, foundObject, "Object at (3,4) should match the new object after setObject is called");

        GameObject newObject = new PathObject();
        board.setObject(newObject, 3, 4);

        GameObject updatedObject = board.getObject(3, 4);
        assertEquals(newObject, updatedObject, "Object at (3,4) failed to update after being overwritten");
    }

    @Test
    public void testGetObject() {
        // create empty board
        board = new Board();

        GameObject expectedObject = new PathObject();
        board.setObject(expectedObject, 2, 2);

        GameObject actualObject = board.getObject(2, 2);
        assertEquals(expectedObject, actualObject, "Objects at (2,2) should match");

        assertNull(board.getObject(0, 0), "Board position (0,0) should have no object");
    }

    @Test
    public void testCreateBoardFromFile() {
        try {
            // Test level5
            board = Board.createBoardFromFile("level5");

            // Test Player and Goal position (should hold true for levels 1-5)
            assertTrue(board.getObject(7, 11) instanceof PlayerObject);
            assertTrue(board.getObject(7, 0) instanceof GoalObject);
            // Test unique elements of level5 (these should only pass on level5)
            assertTrue(board.getObject(7, 6) instanceof WallObject);
            assertTrue(board.getObject(14, 10) instanceof PathObject);
            assertTrue(board.getObject(1, 1) instanceof PathObject);

            // For redundancy, test level4 as well
            board = Board.createBoardFromFile("level4");

            // Test Player and Goal position (should hold true for levels 1-5)
            assertTrue(board.getObject(7, 11) instanceof PlayerObject);
            assertTrue(board.getObject(7, 0) instanceof GoalObject);
            // Test unique elements of level5 against level4
            assertFalse(board.getObject(7, 8) instanceof PathObject);
            assertFalse(board.getObject(14, 10) instanceof PathObject);
            assertFalse(board.getObject(1, 1) instanceof PathObject);

        } catch (IOException e) {
            fail("IOException should not be thrown when loading from file");
        }
    }
}