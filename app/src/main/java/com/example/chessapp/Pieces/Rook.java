package com.example.chessapp.Pieces;

import com.example.chessapp.Game.*;

/**
 * The Rook class contains information on the rook chess piece
 * 
 * @author William Gitoneas & Damon Lin
 */
public class Rook extends Piece {
    /**
     * This is the constructor for the rook, it uses a superclass constructor
     * calling to the Piece class
     * 
     * @param color The color of the rook
     * @param x     The rank/x-position of the rook in relation to the board
     * @param y     The file/y-position of the rook in relation to the board
     */
    public Rook(String color, int x, int y) {
        super(color, x, y);
    }

    /**
     * This method is a private recursive helper method for isValidPath to check
     * along the entire vertical path
     * 
     * @param board The board of the game
     * @param x1    The old rank/row of the rook
     * @param y1    The old file/column of the rook
     * @param x2    The new rank/row of the rook
     * @param y2    The new file/column of the rook
     * @return
     *         false if the rook cannot reach its destination without any conflict
     *         (for example, another piece was blocking it)
     *         true if the rook reaches its destination without any problems
     */
    private boolean isValidPathVertical(Square[][] board, int x1, int y1, int x2, int y2) {
        // base case: rook reaches the destination square without any conflicts
        if (x1 == x2)
            return true;

        if (x1 < x2)
            x1++;
        else
            x1--;

        if (board[x1][y1].getPiece() != null) {
            // if theres a piece on the path, it not being on the destination square or the
            // same color piece causes it to be an invalid path
            if ((board[x1][y1].getPiece().getColor().equals(this.getColor()))
                    || ((x1 != x2) && (y1 != y2)))
                return false;
        }

        // recursive call on the next space in path to destination
        return isValidPathVertical(board, x1, y1, x2, y2);
    }

    /**
     * This method is a private recursive helper method for isValidPath to check
     * along the entire horizontal path
     * 
     * @param board The board of the game
     * @param x1    The old rank/row of the rook
     * @param y1    The old file/column of the rook
     * @param x2    The new rank/row of the rook
     * @param y2    The new file/column of the rook
     * @return
     *         false if the rook cannot reach its destination without any conflict
     *         (for example, another piece was blocking it)
     *         true if the rook reaches its destination without any problems
     */
    private boolean isValidPathHorizontal(Square[][] board, int x1, int y1, int x2, int y2) {
        // base case: rook reaches the destination square without any conflicts
        if (y1 == y2)
            return true;

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
        return isValidPathHorizontal(board, x1, y1, x2, y2);
    }

    /**
     * The rook moves in a straight-line pattern (horizontal and vertical) on the
     * game
     * board. This method checks if the rook moves in the same valid manner.
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
        // checks out of bounds
        if ((x2 < 0) || (x2 > 7) || (y2 < 0) || (y2 > 7)) {
            return false;
        }

        int currX = getX();
        int currY = getY();

        // inputted move is current location
        if ((currX == x2) && (currY == y2))
            return false;

        // tried to move diagonally
        if ((currX != x2) && (currY != y2))
            return false;

        // checks if the entire path is valid
        if ((currX != x2)) { // vertical movement
            if (!isValidPathVertical(board, currX, currY, x2, y2))
                return false;
        } else { // horizontal movement
            if (!isValidPathHorizontal(board, currX, currY, x2, y2))
                return false;
        }

        if (((currX == 0) || (currX == 7)) && (currY == 0))
            player.canCastleLong = false;

        if (((currX == 0) || (currX == 7)) && (currY == 7))
            player.canCastleShort = false;

        return true;
    }
}
