package com.example.chessapp.Pieces;

import com.example.chessapp.Game.*;

/**
 * This abstract Piece class will be the foundation for the other,
 * more specific piece classes
 * 
 * @author Damon Lin & William Gitoneas
 */
public abstract class Piece {
    /**
     * The color of the piece
     */
    private final String color;

    /**
     * The position of the piece in relation to the board (top left is (0,0))
     */
    private int x, y; // translated to standard notation for user output

    // constructor: takes color and position coordinates as args
    /**
     * The Piece constructor
     * 
     * @param color The color of the piece
     * @param x     The rank/x-position of the piece
     * @param y     The file/y-position of the piece
     */
    public Piece(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /**
     * A getter method for the color of the piece
     * 
     * @return The color of the piece
     */
    public String getColor() {
        return color;
    }

    /**
     * A getter method for the rank/x-position of the piece
     * 
     * @return The rank/x-position of the piece
     */
    public int getX() {
        return x;
    }

    /**
     * A getter method for the file/y-position of the piece
     * 
     * @return The file/y-position of the piece
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the current position of the piece on the board.
     * 
     * @param x The rank/x-position of the piece
     * @param y The file/y-position of the piece
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    } // only called when isValidMove is true

    /**
     * An abstract method for all the subclasses to inherit to check whether the
     * move to make is valid. If the move is valid, the piece is moved with
     * the move method
     * 
     * @param player The player associated with the piece
     * @param board  The board
     * @param x      The rank/x-position of the piece
     * @param y      The file/y-position of the piece
     * @return 
     *         false if the piece move was not valid
     *         true if the piece move was valid
     */
    public abstract boolean isValidMove(Player player, Square[][] board, int x, int y);

    /**
     * This method is performed in the game after isValidMove returns true. The
     * method
     * moves the piece from its original position to the position in the parameters
     * 
     * @param board The board
     * @param x     The new rank/x-position of the piece
     * @param y     The new file/y-position of the piece
     */
    public boolean move(Player player, Square[][] board, int x, int y) {
        // saves current state
        Piece pieceAtSpaceToMove = board[x][y].getPiece();

        // moves piece to destination square / empties old square
        board[x][y].setPiece(this);
        board[this.x][this.y].setPiece(null);

        // detects if player puts itself into/maintains check status
        if (Check.kingInCheck(board, player.getColor(), player.kx, player.ky)) {
            board[this.x][this.y].setPiece(this);
            board[x][y].setPiece(pieceAtSpaceToMove);
            return false;
        }

        // updates (x,y) to new position
        this.x = x;
        this.y = y;

        // if a player can move it is no longer in check
        player.notCheck();

        return true;
    }
}
