package edu.appstate.cs.codequest;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PlayerTest {

    static private Board board;

    @Test
    public void testIsAtGoal()
    {
        board = new Board();

        GameObject goal = new GoalObject();
        board.setObject(goal,1,2);
        PlayerObject player = new PlayerObject(board, 2, 2);

        assertFalse(player.getIsAtGoal());

        player.move("left");
        assertTrue(player.getIsAtGoal());

    }

    @Test
    public void testMove()
    {
        board = new Board();
        
        GameObject goal = new GoalObject();
        board.setObject(goal,3,4);
        PlayerObject player = new PlayerObject(board, 3, 3);
        
        //test basic movement
        player.move("up");
        assertEquals(player, board.getObject(3,2));
        assertTrue(board.getObject(3,3) instanceof PathObject);

        player.move("down");
        assertEquals(player, board.getObject(3,3));
        assertTrue(board.getObject(3,2) instanceof PathObject);

        player.move("left");
        assertEquals(player, board.getObject(2,3));
        assertTrue(board.getObject(3,3) instanceof PathObject);

        player.move("right");
        assertEquals(player, board.getObject(3,3));
        assertTrue(board.getObject(2,3) instanceof PathObject);
    }

    @Test
    public void testPlayerCanMove()
    {
        board = new Board();

        PlayerObject player = new PlayerObject(board, 3, 3);
        GameObject wall = new WallObject();
        GameObject goal = new GoalObject();
        GameObject path = new PathObject();

        board.setObject(wall, 3, 2);
        board.setObject(goal, 2, 3);
        board.setObject(path, 3, 4);

        assertFalse(player.canMove('U'));
        assertTrue(player.canMove('L'));
        assertTrue(player.canMove('D'));
    }
}