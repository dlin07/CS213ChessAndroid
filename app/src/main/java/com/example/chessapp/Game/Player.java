package com.example.chessapp.Game;

import com.example.chessapp.Pieces.*;

/**
 * The Player class will contain information about the
 * player during the game
 * 
 * @author William Gitoneas & Damon Lin
 */
public class Player {
    /**
     * This boolean, NO_PAWN_DEBUG, allows the program to be run without pawns for debugging purposes
     */
    private final static boolean NO_PAWN_DEBUG = false;

    /**
     * The Color associated with the player
     */
    private final String color;

    /**
     * Boolean signifying whether the player's king is in check. Initialized to false.
     */
    private boolean inCheck = false;


    /**
     * Boolean signifying whether the player can castle. Initialized to true for both short and long castling.
     */
    public boolean canCastleShort = true, canCastleLong = true;

    /**
     * The x and y position of the player's king
     */
    public int kx, ky;

    /**
     * The constructor of Player that takes the board and color and creates
     * all pieces of the player's respective color and sets them on the board.
     * 
     * @param board The board
     * @param color The color associated with the player
     */
    public Player(Square[][] board, String color) {
        if (color.equals("white")) {
            // creates all white pieces and puts them on board
            Piece wPawn1 = new Pawn("white", 6, 0);
            Piece wPawn2 = new Pawn("white", 6, 1);
            Piece wPawn3 = new Pawn("white", 6, 2);
            Piece wPawn4 = new Pawn("white", 6, 3);
            Piece wPawn5 = new Pawn("white", 6, 4);
            Piece wPawn6 = new Pawn("white", 6, 5);
            Piece wPawn7 = new Pawn("white", 6, 6);
            Piece wPawn8 = new Pawn("white", 6, 7);

            Piece wRook1 = new Rook("white", 7, 0);
            Piece wRook2 = new Rook("white", 7, 7);

            Piece wKnight1 = new Knight("white", 7, 1);
            Piece wKnight2 = new Knight("white", 7, 6);

            Piece wBishop1 = new Bishop("white", 7, 2);
            Piece wBishop2 = new Bishop("white", 7, 5);

            Piece wQueen = new Queen("white", 7, 3);
            Piece wKing = new King("white", 7, 4);

            if (!NO_PAWN_DEBUG) {
                board[6][0].setPiece(wPawn1);
                board[6][1].setPiece(wPawn2);
                board[6][2].setPiece(wPawn3);
                board[6][3].setPiece(wPawn4);
                board[6][4].setPiece(wPawn5);
                board[6][5].setPiece(wPawn6);
                board[6][6].setPiece(wPawn7);
                board[6][7].setPiece(wPawn8);
            }

            board[7][0].setPiece(wRook1);
            board[7][7].setPiece(wRook2);

            board[7][1].setPiece(wKnight1);
            board[7][6].setPiece(wKnight2);

            board[7][2].setPiece(wBishop1);
            board[7][5].setPiece(wBishop2);

            board[7][3].setPiece(wQueen);
            board[7][4].setPiece(wKing);

            kx = 7;
            ky = 4;

        } else if (color.equals("black")) {
            // creates all black pieces and puts them on board
            Piece bPawn1 = new Pawn("black", 1, 0);
            Piece bPawn2 = new Pawn("black", 1, 1);
            Piece bPawn3 = new Pawn("black", 1, 2);
            Piece bPawn4 = new Pawn("black", 1, 3);
            Piece bPawn5 = new Pawn("black", 1, 4);
            Piece bPawn6 = new Pawn("black", 1, 5);
            Piece bPawn7 = new Pawn("black", 1, 6);
            Piece bPawn8 = new Pawn("black", 1, 7);

            Piece bRook1 = new Rook("black", 0, 0);
            Piece bRook2 = new Rook("black", 0, 7);

            Piece bKnight1 = new Knight("black", 0, 1);
            Piece bKnight2 = new Knight("black", 0, 6);

            Piece bBishop1 = new Bishop("black", 0, 2);
            Piece bBishop2 = new Bishop("black", 0, 5);

            Piece bQueen = new Queen("black", 0, 3);
            Piece bKing = new King("black", 0, 4);

            if (!NO_PAWN_DEBUG) {
                board[1][0].setPiece(bPawn1);
                board[1][1].setPiece(bPawn2);
                board[1][2].setPiece(bPawn3);
                board[1][3].setPiece(bPawn4);
                board[1][4].setPiece(bPawn5);
                board[1][5].setPiece(bPawn6);
                board[1][6].setPiece(bPawn7);
                board[1][7].setPiece(bPawn8);
            }

            board[0][0].setPiece(bRook1);
            board[0][7].setPiece(bRook2);

            board[0][1].setPiece(bKnight1);
            board[0][6].setPiece(bKnight2);

            board[0][2].setPiece(bBishop1);
            board[0][5].setPiece(bBishop2);

            board[0][3].setPiece(bQueen);
            board[0][4].setPiece(bKing);

            kx = 0;
            ky = 4;
        } else {
            throw new IllegalArgumentException("Invalid color argument");
        }

        this.color = color;
    }

    /**
     * Gets the color of the player
     * @return The color of the player
     */
    public String getColor() {
        return this.color;
    }

    // returns piece if piece exists and is owned by player, else null
    /**
     * Gets the piece in a specific position
     * @param board The board
     * @param x     The rank/x-position on the board
     * @param y     The file/y-position on the board
     * @return
     *         the piece at position board[x][y]
     *         null if there is no piece at board[x][y]
     */
    public Piece getPiece(Square[][] board, int x, int y) {
        Piece p = board[x][y].getPiece();

        if (p == null) {
            return null;
        }
        if (!p.getColor().equals(this.color)) {
            return null;
        }

        return p;
    }

    /**
     * Sets the boolean inCheck to true if the player is in check.
     */
    public void inCheck() {
        inCheck = true;
    }

    /**
     * Sets the boolean inCheck to false if the player is no longer in check
     */
    public void notCheck() {
        inCheck = false;
    }

    /**
     * Gets whether the player is in check
     * @return
     *         false if the player is not in check
     *         true if the player is in check
     */
    public boolean isCheck() {
        return inCheck;
    }
}
