package com.example.chessapp.Pieces;

import com.example.chessapp.Game.*;

/**
 * The Knight class contains information on the knight chess piece
 * 
 * @author Damon Lin & William Gitoneas
 */
public class Knight extends Piece {
    /**
     * This is the constructor for the knight, it uses a superclass constructor
     * calling to the Piece class
     * 
     * @param color The color of the knight
     * @param x     The rank/x-position of the knight in relation to the board
     * @param y     The file/y-position of the knight in relation to the board
     */
    public Knight(String color, int x, int y) {
        super(color, x, y);
    }

    /**
     * The knight moves in a L-shape pattern. The method checks whether the knight
     * piece moves in the same valid manner.
     * 
     * @param player The player associated with the piece
     * @param board  The board
     * @param newX   The new rank/horizontal position of the piece
     * @param newY   The new file/vertical position of the piece
     * @return
     *         false if not valid
     *         true if valid
     */
    @Override
    public boolean isValidMove(Player player, Square[][] board, int newX, int newY) {
        // checks out-of-bounds
        if ((newX < 0) || (newX > 7) || (newY < 0) || (newY > 7)) {
            return false;
        }

        int dx = Math.abs(newX - this.getX());
        int dy = Math.abs(newY - this.getY());

        if (!((dx == 1) && (dy == 2)) && !((dx == 2) && (dy == 1)))
            return false;

        if (board[newX][newY].getPiece() != null) {
            return !board[newX][newY].getPiece().getColor().equals(this.getColor());
        }

        return true;
    }
}
