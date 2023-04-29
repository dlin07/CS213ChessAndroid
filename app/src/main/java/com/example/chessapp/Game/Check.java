package com.example.chessapp.Game;

import com.example.chessapp.Pieces.*;

/**
 * The Check class will check whether the player's respective king is in check.
 * It provides
 * all functions regarding checks and checkmates.
 * 
 * @author William Gitoneas & Damon Lin
 */
public class Check {
    /**
     * Private constructor for Check because the Check class is a utility class.
     */
    private Check() {
    }

    /**
     * Checks if the player's respective king piece is in check from any other piece
     * on the board.
     * 
     * @param board     The board
     * @param kingColor The respective king's color
     * @param kx        The respective king's rank/x-position.
     * @param ky        The respective king's file/y-position.
     * @return
     *         false if the king is not in check
     *         true is the king is in check
     */
    public static boolean kingInCheck(Square[][] board, String kingColor, int kx, int ky) {

        // Functionality 1: inspect intermediate surrounding
        for (int i = kx - 1; i <= kx + 1; i++) {
            for (int j = ky - 1; j <= ky + 1; j++) {
                // out of bounds
                if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) {
                    continue;
                }
                // same as king space
                if ((i == kx) && (j == ky))
                    continue;

                // get the piece at the surrounding spot
                Piece surroundingPiece = board[i][j].getPiece();

                if (surroundingPiece == null)
                    continue;

                // check if surrounding piece is king
                if ((surroundingPiece instanceof King) && (!surroundingPiece.getColor().equals(kingColor))) {
                    return true;
                }

                // check for proper pawns putting king in check
                if ((surroundingPiece instanceof Pawn) && (!surroundingPiece.getColor().equals(kingColor))) {
                    if (kingColor.equals("white")) {
                        if ((i == (kx - 1)) && ((j == (ky - 1)) || (j == (ky + 1))))
                            return true;
                    } else {
                        if ((i == (kx + 1)) && ((j == (ky - 1)) || (j == (ky + 1))))
                            return true;
                    }
                }
            }
        }

        // Functionality 2: inspect knight check
        for (int i = kx - 2; i <= kx + 2; i += 4) {
            for (int j = ky - 1; j <= ky + 1; j += 2) {
                // out of bounds
                if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) {
                    continue;
                }

                // get the piece at the surrounding spot
                Piece surroundingPiece = board[i][j].getPiece();

                if (surroundingPiece == null)
                    continue;

                // check if surrounding piece is knight
                if ((surroundingPiece instanceof Knight) && (!surroundingPiece.getColor().equals(kingColor))) {
                    return true;
                }
            }
        }

        for (int i = kx - 1; i <= kx + 1; i += 2) {
            for (int j = ky - 2; j <= ky + 2; j += 4) {
                // out of bounds
                if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) {
                    continue;
                }

                // get the piece at the surrounding spot
                Piece surroundingPiece = board[i][j].getPiece();

                if (surroundingPiece == null)
                    continue;

                // check if surrounding piece is knight
                if ((surroundingPiece instanceof Knight) && (!surroundingPiece.getColor().equals(kingColor))) {
                    return true;
                }
            }
        }

        // Functionality 3: inspect diagonals
        // check northeast diagonal
        int tempY = ky;
        for (int i = kx - 1; i >= 0; i--) {
            tempY++;
            if (tempY > 7)
                break;

            // get the piece at the diagonal spot
            Piece surroundingPiece = board[i][tempY].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if diagonal piece is queen/bishop
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Bishop))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check northwest diagonal
        tempY = ky;
        for (int i = kx - 1; i >= 0; i--) {
            tempY--;
            if (tempY < 0)
                break;

            // get the piece at the diagonal spot
            Piece surroundingPiece = board[i][tempY].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if diagonal piece is queen/bishop
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Bishop))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check southeast diagonal
        tempY = ky;
        for (int i = kx + 1; i <= 7; i++) {
            tempY++;
            if (tempY > 7)
                break;

            // get the piece at the diagonal spot
            Piece surroundingPiece = board[i][tempY].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if diagonal piece is queen/bishop
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Bishop))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check southwest diagonal
        tempY = ky;
        for (int i = kx + 1; i <= 7; i++) {
            tempY--;
            if (tempY < 0)
                break;

            // get the piece at the diagonal spot
            Piece surroundingPiece = board[i][tempY].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if diagonal piece is queen/bishop
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Bishop))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }

        // Functionality 4: inspect vertical/horizontal
        // check east
        for (int i = ky + 1; i <= 7; i++) {
            // get the piece at the eastern spot
            Piece surroundingPiece = board[kx][i].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if eastern piece is queen/rook
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Rook))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check west
        for (int i = ky - 1; i >= 0; i--) {
            // get the piece at the western spot
            Piece surroundingPiece = board[kx][i].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if western piece is queen/rook
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Rook))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check north
        for (int i = kx - 1; i >= 0; i--) {
            // get the piece at the northern spot
            Piece surroundingPiece = board[i][ky].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if northern piece is queen/rook
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Rook))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }
        // check south
        for (int i = kx + 1; i <= 7; i++) {
            // get the piece at the southern spot
            Piece surroundingPiece = board[i][ky].getPiece();

            if (surroundingPiece == null)
                continue;

            // check if southern piece is queen/rook
            if (((surroundingPiece instanceof Queen) || (surroundingPiece instanceof Rook))
                    && (!surroundingPiece.getColor().equals(kingColor))) {
                return true;
            } else if ((surroundingPiece instanceof King) && (surroundingPiece.getColor().equals(kingColor)))
                continue; // ignore the king in question being looked at on the previous space
            else // it is a piece blocking check
                break;
        }

        return false;
    }

    /**
     * A helper method for the method isCheckMate
     * 
     * @param board         The board
     * @param playerInCheck The player that is in check
     * @param currentPiece  The piece that may or may not get the player out of
     *                      check
     * @return
     *         false if the currentPiece does not get the player out of check
     *         true if the currentPiece does get the player out of check
     */
    private static boolean outOfCheck(Square[][] board, Player playerInCheck, Piece currentPiece) {
        int currX = currentPiece.getX();
        int currY = currentPiece.getY();

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                // skip check of current position
                if ((currX == i) && (currY == j))
                    continue;

                // check if the piece placed in the new position:
                // -- is a valid move
                // -- puts the player out of check
                // return true if one of these tests pass

                // move is not valid
                if (!currentPiece.isValidMove(playerInCheck, board, i, j))
                    continue;

                // piece on testPosition
                Piece testPositionPiece = board[i][j].getPiece();

                // move does not bring player out of check
                if (!currentPiece.move(playerInCheck, board, i, j))
                    continue;

                // must revert move and set position back to original
                board[currX][currY].setPiece(currentPiece);
                currentPiece.setPosition(currX, currY);

                board[i][j].setPiece(testPositionPiece);

                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the respective player is checkmated
     * 
     * @param board         The board
     * @param playerInCheck The player that is in check
     * @return
     *         false if the player is not in checkmate
     *         true if the player is checkmated
     */
    public static boolean isCheckmate(Square[][] board, Player playerInCheck) {
        // iterate through the chess board
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Piece currentPiece = board[i][j].getPiece();

                if (currentPiece == null)
                    continue;

                // checks if piece at current position is owned by the player in check
                if (currentPiece.getColor().equals(playerInCheck.getColor())) {
                    // test all possible moves for that piece in hopes of getting player out of
                    // check
                    if (outOfCheck(board, playerInCheck, currentPiece)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
