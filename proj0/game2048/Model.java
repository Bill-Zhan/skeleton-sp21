package game2048;

import javax.imageio.stream.ImageInputStream;
import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Bill Zhan
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed = false;

        // TODO: ? Modify this.board (and perhaps this.score) to account

        /* board will not change iff there is no move exists */
//        changed = atLeastOneMoveExists(board);

        /* set new view */
        board.setViewingPerspective(side);

        /* use an array to store the farthest row that can move to */
        int[] farthestTop = new int[board.size()];
        for (int i = 0; i < board.size(); i++) {
            farthestTop[i] = board.size()-1;
        }

        /* loop from the second row (row size-2), move them to farthest top */
        for (int row = board.size()-2; row >= 0; row--) {
            for (int col = 0; col < board.size(); col++) {
                /* corner case: no need to do anything if current tile null */
                Tile currTile = board.tile(col, row);
                if (currTile == null) { continue; }

                /* declare variable */
                int topRow = farthestTop[col];  // the top possible row that can move t
                int currVal = currTile.value();
                boolean isTopRowValid;


                /* move top down to a valid row */
                do {
                    farthestTop[col] = topRow;
                    Tile topTile = board.tile(col, topRow);
                    isTopRowValid = (topTile == null || topTile.value() == currVal);
                    topRow -= 1;
                } while (!(isTopRowValid || topRow == 0));

                /* try moving */
                boolean merged =  board.move(col, farthestTop[col], currTile);
                if (merged) {
                    /* update score */
                    score += currVal*2;
                    if (farthestTop[col] > 0) {
                        farthestTop[col] -= 1;
                    }
                }

                /* check if moved */
                boolean ifMoved = board.tile(col, row) == null;
                if (ifMoved) { changed = true; }
            }
        }


        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        checkGameOver();
        if (changed) {
            setChanged();
        }

        // set the view back to ordinary north
        board.setViewingPerspective(Side.NORTH);

        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /**
     * Returns true if at least one space on the Board is empty.
     * Empty spaces are stored as null.
     *
     */
    public static boolean emptySpaceExists(Board b) {
        // TODO: ! Completed emptySpaceExists
        /**Check if any of the tiles is null
         *
         * Given a Board b, use the tile(int col, int row) and size method to check
         * if any of the tiles is null.
         *
         * @param Board b:
         *              an object from the Board class.
         * @return boolean:
         *              true - any tile is null.
         */
        int bSize = b.size();
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                if (b.tile(j, i) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value()
     * @param b: Board
     *         a Board object
     *
     * @return boolean: true if any tile == MAX_PIECE
     */
    public static boolean maxTileExists(Board b) {
        // TODO: ! Completed maxTileExists
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                Tile currTile = b.tile(j, i);
                if (currTile == null) { continue; }
                int tileVal = currTile.value();
                if (tileVal == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: ! Completed atLeastOneMoveExists
        // 1. there is at least one empty space
        if (emptySpaceExists(b)) {
            return true;
        }

        // 2. there are two adjacent tiles with the same value
        int[]   up = new int[] {-1, 0},
                down = new int[] {1, 0},
                left = new int[] {0, -1},
                right = new int[] {0, 1};
        int[][] dirs = new int[][] {up, down, left, right};

        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                // current row and column and tile
                int row = i, col = j;
                Tile currTile = b.tile(j, i);
                // adjacent tile
                for (int[] d: dirs) {
                    // check if new tile is valid
                    boolean isNewTileInRange =
                            0 <= d[0] + row && d[0] + row < b.size() &&  // valid row
                            0 <= d[1] + col && d[1] + col < b.size(); // valid col
                    if (isNewTileInRange) {
                        Tile newTile = b.tile(d[1] + col, d[0] + row);
                        int currVal = currTile.value(), newVal = newTile.value();
                        if (currVal == newVal) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
