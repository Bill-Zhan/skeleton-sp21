package game2048;

import org.junit.Test;

import static org.junit.Assert.*;

/** Tests the emptySpaceExists() static method of Model.
 *
 * @author Bill Zhan
 */
public class TestTile {

    /** The Board that we'll be testing on. */
    static Board b;

    @Test
    /** Note that this isn't a possible board state. */
    public void testNorth() {
        int[][] rawVals = new int[][] {
                {1, 2, 3, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        int expected = 4;
        int actual = b.tile(3, 3).value();
        assertEquals(expected, actual);
    }

    @Test
    /** Tests a board that is completely full except for the top row. */
    public void testWest() {
        int[][] rawVals = new int[][] {
                {1, 2, 3, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        b.setViewingPerspective(Side.WEST);
        int expected = 4;
        int actual = b.tile(3, 0).value();
        assertEquals(expected, actual);
    }

    @Test
    public void testEast() {
        int[][] rawVals = new int[][] {
                {1, 2, 3, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        b.setViewingPerspective(Side.EAST);
        int expected = 1;
        int actual = b.tile(0, 0).value();
        assertEquals(expected, actual);
    }

}
