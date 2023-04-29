package com.example.chessapp.Pieces;

import com.example.chessapp.Game.Player;
import com.example.chessapp.Game.Square;

/**
 * The Bishop class contains information on the bishop chess piece
 * 
 * @author William Gitoneas & Damon Lin
 */
public class Bishop extends Piece {
    /**
     * This is the constructor for the bishop, it uses a superclass constructor
     * calling to the Piece class
     * 
     * @param color The color of the bishop
     * @param x     The rank/x-position of the bishop in relation to the board
     * @param y     The file/y-position of the bishop in relation to the board
     */
    public Bishop(String color, int x, int y) {
        super(color, x, y);
    }

    /**
     * This method is a private recursive helper method for isValidPath to check
     * along the entire diagonal path
     * 
     * @param board The board of the game
     * @param x1    The old rank/row of the bishop
     * @param y1    The old file/column of the bishop
     * @param x2    The new rank/row of the bishop
     * @param y2    The new file/column of the bishop
     * @return
     *         false if the bishop cannot reach its destination without any conflict
     *         (for example, another piece was blocking it)
     *         true if the bishop reaches its destination without any problems
     */
    private boolean isValidPath(Square[][] board, int x1, int y1, int x2, int y2) {
        // base case: bishop reaches the destination square without any conflicts
        if ((x1 == x2) && (y1 == y2))
            return true;

        if (x1 < x2)
            x1++;
        else
            x1--;

        if (y1 < y2)
            y1++;
        else
            y1--;

        if (board[x1][y1].getPiece() != null) {
            // if theres a piece on the path, it not being on the destination square or the
            // same color piece causes it to be an invalid path
            if ((board[x1][y1].getPiece().getColor().equals(this.getColor()))
                    || ((x1 != x2) && (y1 != y2)))
                return false;
        }

        // recursive call on the next space in path to destination
        return isValidPath(board, x1, y1, x2, y2);
    }

    /**
     * The bishop moves in a diagonal pattern on the game board. This method checks
     * if
     * the bishop moves in the same valid manner.
     * 
     * @param player The player associated with the piece
     * @param board  The board
     * @param x2     The new rank/x-position of the piece
     * @param y2     The new file/y-position of the piece
     * @return
     *         false if not valid
     *         true if valid
     */
    @Override
    public boolean isValidMove(Player player, Square[][] board, int x2, int y2) {
        // entered coordinates are outside the board's bounds
        if ((x2 < 0) || (x2 > 7) || (y2 < 0) || (y2 > 7)) {
            return false;
        }

        int currX = getX();
        int currY = getY();

        // inputted move is current location
        if ((currX == x2) && (currY == y2))
            return false;

        int diffX = Math.abs(x2 - currX);
        int diffY = Math.abs(y2 - currY);

        if (diffX != diffY) {
            return false;
        }

        // checks if the entire path is valid
        return isValidPath(board, currX, currY, x2, y2);
    }
}
