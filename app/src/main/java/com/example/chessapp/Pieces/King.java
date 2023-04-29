package com.example.chessapp.Pieces;

import com.example.chessapp.Game.*;

/**
 * The King class contains information on the king chess piece
 * 
 * @author William Gitoneas & Damon Lin
 */
public class King extends Piece {
    /**
     * The constructor for the king, it uses a superclass constructor calling to the
     * Piece class
     * 
     * @param color The color of the king
     * @param x     The rank/x-position of the king in relation to the board
     * @param y     The file/y-position of the king in relation to the board
     */
    public King(String color, int x, int y) {
        super(color, x, y);
    }

    /**
     * This method is a helper method to check whether castling is viable
     * 
     * @param board The board
     * @param kx    The current rank/x-position of the king
     * @param ky    The current file/y-position of the king
     * @param rx    The current rank/x-position of the rook
     * @param ry    The current file/y-position of the rook
     * @return
     *         false if the king cannot castle
     *         true if the king can castle (either long or short)
     */
    private boolean checkAlongCastle(Player player, Square[][] board, int kx, int ky, int rx, int ry) {
        // can't castle while in check
        if (player.isCheck())
            return false;

        // insight: king and rook are on the same x but different y
        if (ry > ky) {
            // check if spaces between rook and king are null
            for (int i = ky + 1; i < ry; i++) {
                if (board[kx][i].getPiece() != null)
                    return false;
            }
            // make sure the two spaces king passes don't put it in check
            if (Check.kingInCheck(board, player.getColor(), kx, ky + 1))
                return false;
            // may not be necessary since move method will check this position for check
            if (Check.kingInCheck(board, player.getColor(), kx, ky + 2))
                return false;
        } else {
            // check if spaces between rook and king are null
            for (int i = ky - 1; i > ry; i--) {
                if (board[kx][i].getPiece() != null)
                    return false;
            }
            // make sure the two spaces king passes don't put it in check
            if (Check.kingInCheck(board, player.getColor(), kx, ky - 1))
                return false;
            // may not be necessary since move method will check this position for check
            if (Check.kingInCheck(board, player.getColor(), kx, ky - 2))
                return false;
        }

        Piece rook = board[rx][ry].getPiece();
        if (ry > ky) { // short castle
            rook.setPosition(rx, ky + 1);

            board[rx][ky + 1].setPiece(rook);

            // update player king's y position
            player.ky = ky + 2;
        } else { // long castle
            rook.setPosition(rx, ky - 1);

            board[rx][ky - 1].setPiece(rook);

            // update player king's y position
            player.ky = ky - 2;
        }
        board[rx][ry].setPiece(null);

        return true;
    }

    /**
     * The king can only move one space in every direction. This method checks if
     * the king's move is valid.
     * 
     * @param player The player associated with the piece
     * @param board  The board
     * @param x2     The new rank/x-position of the piece
     * @param y2     The new file/y-position of the piece
     * @return
     *         false if the move was invalid
     *         true if the move was valid
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

        /*
         * checks if a castle attempt:
         * if white, castle can be on one of these squares: (7,6) (7,2)
         * if black, castle can be on one of these squares: (0,6) (0,2)
         */
        if (this.getColor().equals("white") && ((x2 == 7) && (y2 == 6))) { // white short castle
            if (board[7][7].getPiece() == null)
                return false;
            if (player.canCastleShort && (board[7][7].getPiece() instanceof Rook)
                    && board[7][7].getPiece().getColor().equals(player.getColor()))
                return checkAlongCastle(player, board, currX, currY, 7, 7);
        } else if (this.getColor().equals("black") && ((x2 == 0) && (y2 == 6))) { // black short castle
            if (board[0][7].getPiece() == null)
                return false;
            if (player.canCastleShort && (board[0][7].getPiece() instanceof Rook)
                    && board[0][7].getPiece().getColor().equals(player.getColor()))
                return checkAlongCastle(player, board, currX, currY, 0, 7);
        } else if (this.getColor().equals("white") && ((x2 == 7) && (y2 == 2))) { // white long castle
            if (board[7][0].getPiece() == null)
                return false;
            if (player.canCastleLong && (board[7][0].getPiece() instanceof Rook)
                    && board[7][0].getPiece().getColor().equals(player.getColor()))
                return checkAlongCastle(player, board, currX, currY, 7, 0);
        } else if (this.getColor().equals("black") && ((x2 == 0) && (y2 == 2))) { // black long castle
            if (board[0][0].getPiece() == null)
                return false;
            if (player.canCastleLong && (board[0][0].getPiece() instanceof Rook)
                    && board[0][0].getPiece().getColor().equals(player.getColor()))
                return checkAlongCastle(player, board, currX, currY, 0, 0);
        }

        int diffX = Math.abs(x2 - currX);
        int diffY = Math.abs(y2 - currY);

        // tries to move more than one space
        if ((diffX > 1) || (diffY > 1))
            return false;

        // cannot move to space containing piece of same color
        if (board[x2][y2].getPiece() != null) {
            if (board[x2][y2].getPiece().getColor().equals(this.getColor()))
                return false;
        }

        // cannot put itself into/maintain check status
        if (Check.kingInCheck(board, this.getColor(), x2, y2))
            return false;

        // sets castle booleans to false in player object
        player.canCastleShort = false;
        player.canCastleLong = false;

        // if the king can successfully move, then it's not in check anymore
        player.notCheck();

        // if the king can successfully move, then update its position
        player.kx = x2;
        player.ky = y2;

        return true;
    }

}
