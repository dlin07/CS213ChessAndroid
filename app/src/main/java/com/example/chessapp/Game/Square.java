package com.example.chessapp.Game;

import com.example.chessapp.Pieces.Piece;

/**
 * This class contains the information on what piece
 * is on which square. The class is able to get that
 * piece or change what piece it is. Each square will
 * point to a piece or null meaning empty. The chess
 * board will be a 2D array of Squares.
 * 
 * @author Damon Lin & William Gitoneas
 */
public class Square {
    /**
     * An instance of the Piece class initialized to null
     */
    private Piece chessPiece = null;

    /**
     * Gets the chess piece located on this square
     * 
     * @return
     *         the chess piece on a square
     */
    public Piece getPiece() {
        return chessPiece;
    }

    /**
     * Will place the piece accepted from as a param onto this square
     * 
     * @param piece the piece to place onto the square
     */
    public void setPiece(Piece piece) {
        chessPiece = piece;
    }

}
