package com.example.chessapp.Pieces;

import com.example.chessapp.Game.*;

/**
 * The Pawn class contains information on the pawn chess piece
 * 
 * @author Damon Lin & William Gitoneas
 */
public class Pawn extends Piece {
    /**
     * enPassent shows whether or not the pawn may be taken through en passent
     */
    boolean enPassent = false;

    /**
     * The constructor for the pawn, it uses a superclass constructor calling to the
     * Piece class
     * 
     * @param color The color of the pawn
     * @param x     The rank/x-position of the pawn in relation to the board
     * @param y     The file/y-position of the pawn in relation to the board
     */
    public Pawn(String color, int x, int y) {
        super(color, x, y);
    }

    /**
     * The pawn can move one or two squares vertically or one square diagonally on
     * the game
     * board. This method checks if the pawn moves in the same valid manner.
     * 
     * @param player The player associated with the piece
     * @param board  The board
     * @param newX   The new rank/x-position of the piece
     * @param newY   The new file/y-position of the piece
     * @return
     *         false if not valid
     *         true if valid
     */
    public boolean isValidMove(Player player, Square[][] board, int newX, int newY) {
        if ((newX < 0) || (newX > 7) || (newY < 0) || (newY > 7)) {
            return false;
        }

        int dx = newX - this.getX();
        int dy = newY - this.getY();

        // checks to see whether moving two spaces is valid

        if (this.getColor().equals("white")) { // movement of white's pawns
            if (dx == -2 && dy == 0 && this.getX() == 6) { // checks if advancing two squares is available -> en passent
                if (board[newX][newY].getPiece() == null && board[newX + 1][newY].getPiece() == null) {
                    setEnPassent();
                    return true;
                }
            } else if (dx == -1 && dy == 0) { // checks for regular pawn movement
                if (board[newX][newY].getPiece() == null) {
                    noEnPassent();
                    return true;
                }
            } else if (dx == -1 && (dy == 1 || dy == -1)) { // checks for diagonal (taking)
                if (board[newX][newY].getPiece() != null
                        && !board[newX][newY].getPiece().getColor().equals(this.getColor())) {
                    noEnPassent();
                    return true;
                } else if (board[newX][newY].getPiece() == null) {
                    Piece p;
                    if (board[newX + 1][newY].getPiece() != null) {
                        p = board[newX + 1][newY].getPiece();
                        if (p instanceof Pawn && !p.getColor().equals(this.getColor())) {
                            if (((Pawn) p).getEnPassent()) {
                                noEnPassent();
                                board[newX + 1][newY].setPiece(null);
                                return true;
                            }
                        }
                    }
                }
            }
        } else { // movement of black's pawns
            if (dx == 2 && dy == 0 && this.getX() == 1) { // checks if advancing two squares is available -> en passent
                if (board[newX][newY].getPiece() == null && board[newX - 1][newY].getPiece() == null) {
                    setEnPassent();
                    return true;
                }
            } else if (dx == 1 && dy == 0) { // checks for regular pawn movement
                if (board[newX][newY].getPiece() == null) {
                    noEnPassent();
                    return true;
                }
            } else if (dx == 1 && (dy == 1 || dy == -1)) { // checks for diagonal (taking)
                if (board[newX][newY].getPiece() != null
                        && !board[newX][newY].getPiece().getColor().equals(this.getColor())) {
                    noEnPassent();
                    return true;
                } else if (board[newX][newY].getPiece() == null) { //
                    Piece p;
                    if (board[newX - 1][newY].getPiece() != null) {
                        p = board[newX - 1][newY].getPiece();
                        if (p instanceof Pawn && !p.getColor().equals(this.getColor())) {
                            if (((Pawn) p).getEnPassent()) {
                                noEnPassent();
                                board[newX - 1][newY].setPiece(null);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Sets enPassent to true, meaning that this pawn may be taken via en passent
     */
    public void setEnPassent() {
        enPassent = true;
    }

    /**
     * Sets enPassent to false, meaning that this may
     */
    public void noEnPassent() {
        enPassent = false;
    }

    /**
     * Gets the whether this pawn may be taken via en passent
     * 
     * @return
     *         false if not able to be taken
     *         true if able to be taken
     */
    public boolean getEnPassent() {
        return enPassent;
    }
}
