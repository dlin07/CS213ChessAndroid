package com.example.chessapp.Game;

import java.io.*;

import com.example.chessapp.Pieces.*;

/**
 * The Chess Board game that will manage the inputs and the state of the game.
 * 
 * @author William Gitoneas & Damon Lin
 */
public class Chess {

    /**
     * This boolean, DEBUG, allows certain messages to appear to debug the program.
     */
    private final static boolean DEBUG = false;

    /**
     * This boolean, SCAN_DEBUG, allows the moves in the text file named 'moves.txt'
     * to be played in the console.
     */
    private final static boolean SCAN_DEBUG = false;

    /**
     * This method prints the game board with each piece, its colors, the file, and
     * the rank.
     * 
     * @param board The board
     */
    public static void printBoard(Square[][] board) {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Piece currPiece = board[i][j].getPiece();

                if (currPiece != null) {
                    String pieceString = "";

                    if (currPiece instanceof Pawn)
                        pieceString = "p";
                    else if (currPiece instanceof Rook)
                        pieceString = "R";
                    else if (currPiece instanceof Knight)
                        pieceString = "N";
                    else if (currPiece instanceof Bishop)
                        pieceString = "B";
                    else if (currPiece instanceof Queen)
                        pieceString = "Q";
                    else if (currPiece instanceof King)
                        pieceString = "K";

                    if (currPiece.getColor().equals("white"))
                        System.out.print("w" + pieceString + " ");
                    else
                        System.out.print("b" + pieceString + " ");
                } else {
                    // fill in black/white spaces
                    if ((i % 2) == 0) {
                        if ((j % 2) == 0)
                            System.out.print("   ");
                        else
                            System.out.print("## ");
                    } else {
                        if ((j % 2) == 0)
                            System.out.print("## ");
                        else
                            System.out.print("   ");
                    }
                }
            }
            int row = 8 - i;
            System.out.println(row);
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    /**
     * The method takes in the first character of the user's file|rank input and
     * converts it into a number in relation to the board's positioning. Since the
     * top left of the board is at index [0][0], the number it returns relates the
     * file/column of the board.
     * 
     * @param c The character that corresponds to each file/column
     * @return
     *         0 if c was 'a'
     *         1 if c was 'b'
     *         2 if c was 'c'
     *         3 if c was 'd'
     *         4 if c was 'e'
     *         5 if c was 'f'
     *         6 if c was 'g'
     *         7 if c was 'h'
     */
    public static int letterToIndex(char c) {
        switch (c) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return -1;
        }
    }

    /**
     * The method takes in the second character of the user's file|rank input and
     * converts it into a number in relation to the board's positioning. Since the
     * top left of the board is at index [0][0], the number it returns corresponds
     * to
     * the rank/row of the board.
     * 
     * @param c The character that corresponds to each rank/row
     * @return
     *         0 if c was '8'
     *         1 if c was '7'
     *         2 if c was '6'
     *         3 if c was '5'
     *         4 if c was '4'
     *         5 if c was '3'
     *         6 if c was '2'
     *         7 if c was '1'
     */
    public static int numberToIndex(char c) {
        int i = Character.getNumericValue(c);

        return 8 - i;
    }

    /**
     * The main driver code of the chess program. It handles all user inputs and the
     * state of the game.
     * 
     * @param args The user inputs from console
     * @throws IOException An exception thrown when the program runs into an
     *                     input/output exception
     */
    public static void main(String[] args) throws IOException {
        // create board
        Square[][] board = new Square[8][8];

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Square temp = new Square();
                board[i][j] = temp;
            }
        }

        // create players
        Player whitePlayer = new Player(board, "white");
        Player blackPlayer = new Player(board, "black");

        // initialize starting player
        Player currPlayer = whitePlayer;

        // checks whether there is a draw on the table
        boolean drawOffered = false;

        // create BufferedReader for reading player inputs
        BufferedReader inputReader;

        if (SCAN_DEBUG)
            inputReader = new BufferedReader(
                    new FileReader("docs/moves.txt"));
        else
            inputReader = new BufferedReader(
                    new InputStreamReader(System.in));

        // initial print of starting board
        printBoard(board);

        // start game
        while (true) {

            // white moves
            if (currPlayer.getColor().equals("white")) {
                // enPassent no longer available after one turn
                for (int i = 0; i < board[0].length; i++) {
                    Piece p = board[4][i].getPiece();
                    if (p instanceof Pawn)
                        ((Pawn) p).noEnPassent();
                }
                System.out.print("White's move: ");
            } else {
                // enPassent no longer available after one turn
                for (int i = 0; i < board[0].length; i++) {
                    Piece p = board[3][i].getPiece();
                    if (p instanceof Pawn)
                        ((Pawn) p).noEnPassent();
                }
                System.out.print("Black's move: ");
            }

            String playerMove = inputReader.readLine();
            System.out.println();

            if (drawOffered && playerMove.equals("draw")) {
                System.out.println("draw");
                break;
            }

            if (playerMove.equals("resign")) {
                if (currPlayer.getColor().equals("white")) {
                    System.out.println("Black wins");
                } else {
                    System.out.println("White wins");
                }
                break;
            }

            String[] moveArgs = playerMove.split("\\s");

            // (x1, y1): current position
            // (x2, y2): desired moves
            int x1, y1, x2, y2;
            char letter1, number1, letter2, number2;

            letter1 = moveArgs[0].charAt(0);
            number1 = moveArgs[0].charAt(1);

            x1 = numberToIndex(number1);
            y1 = letterToIndex(letter1);

            letter2 = moveArgs[1].charAt(0);
            number2 = moveArgs[1].charAt(1);

            x2 = numberToIndex(number2);
            y2 = letterToIndex(letter2);

            if (DEBUG)
                System.out.println("x1: " + x1 + ", y1: " + y1 + ", x2: " + x2 + ", y2: " + y2);

            // get square
            Square currentSquare = board[x1][y1];
            // no piece at square
            if (currentSquare.getPiece() == null) {
                if (DEBUG)
                    System.out.println("No piece at current square");
                System.out.println("Illegal move, try again");
                continue;
            }

            // gets piece at current square
            Piece currentPiece = currentSquare.getPiece();
            // checks if player owns piece it's trying to move
            if (!currentPiece.getColor().equals(currPlayer.getColor())) {
                if (DEBUG)
                    System.out.println("Cannot move opponent's piece");
                System.out.println("Illegal move, try again");
                continue;
            }

            // boolean will check if the move is valid before
            if (!currentPiece.isValidMove(currPlayer, board, x2, y2)) {
                if (DEBUG)
                    System.out.println("Move not valid per piece's rules");
                System.out.println("Illegal move, try again");
                continue;
            }

            // physically moves the piece to the destination
            if (!currentPiece.move(currPlayer, board, x2, y2)) {
                if (DEBUG)
                    System.out.println("Move causes/maintains check status");
                System.out.println("Illegal move, try again");
                continue;
            }

            // no argument: promotes pawn to queen
            if ((currentPiece instanceof Pawn) && ((currentPiece.getX() == 0) || (currentPiece.getX() == 7))) {
                Piece q = new Queen(currentPiece.getColor(), x2, y2);
                board[x2][y2].setPiece(q);
            }

            // special command after the move
            if (moveArgs.length == 3) {
                if (moveArgs[2].equals("draw?")) {
                    drawOffered = true;

                    // switches to other player
                    if (currPlayer.getColor().equals("white"))
                        currPlayer = blackPlayer;
                    else
                        currPlayer = whitePlayer;

                    continue;
                } else if (currentPiece instanceof Pawn) {
                    // promotion case
                    switch (moveArgs[2]) {
                        case "R":
                            Piece r = new Rook(currentPiece.getColor(), x2, y2);
                            board[x2][y2].setPiece(r);
                            break;
                        case "B":
                            Piece b = new Bishop(currentPiece.getColor(), x2, y2);
                            board[x2][y2].setPiece(b);
                            break;
                        case "N":
                            Piece n = new Knight(currentPiece.getColor(), x2, y2);
                            board[x2][y2].setPiece(n);
                            break;
                        default:
                            // pawn promoted to queen
                            Piece q = new Queen(currentPiece.getColor(), x2, y2);
                            board[x2][y2].setPiece(q);
                            break;
                    }
                }
            }

            // print the board
            printBoard(board);

            // check kingInCheck on enemy player's king position
            if (currPlayer.getColor().equals("white")) {
                if (Check.kingInCheck(board, "black", blackPlayer.kx, blackPlayer.ky)) {
                    blackPlayer.inCheck();

                    // check if checkmate
                    if (Check.isCheckmate(board, blackPlayer)) {
                        System.out.println("Checkmate");
                        System.out.println("White wins");
                        break;
                    }

                    System.out.println("Check");
                }
            } else {
                if (Check.kingInCheck(board, "white", whitePlayer.kx, whitePlayer.ky)) {
                    whitePlayer.inCheck();

                    // check if checkmate
                    if (Check.isCheckmate(board, whitePlayer)) {
                        System.out.println("Checkmate");
                        System.out.println("Black wins");
                        break;
                    }

                    System.out.println("Check");
                }
            }

            // switches to other player
            if (currPlayer.getColor().equals("white"))
                currPlayer = blackPlayer;
            else
                currPlayer = whitePlayer;
        }
        if (SCAN_DEBUG)
            inputReader.close();
    }
}