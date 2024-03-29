package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chessapp.Game.*;
import com.example.chessapp.Pieces.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ChessActivity extends AppCompatActivity {
    private Chess game;
    private GridLayout boardView;
    private TextView text;
    private Button undoButton, resignButton, drawButton, aiButton;
    private int r1, c1, r2, c2;
    private String move;
    private boolean undo;
    private HashMap<String, Integer> pieces;

    private RecordsList records;

    private Record record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        // load the serialized records
        records = new RecordsList();

        // checks if the users.dat file exists
        Path recordsData = Paths.get(getFilesDir().getPath() + "records.dat");

        if (Files.exists(recordsData)) {
            // loads the users from the .dat file
            try {
                records.loadRecords(recordsData.toString());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // starts recording the game
        record = new Record();

        // hashmap containing pieces and their id's
        pieces = new HashMap<>();
        pieces.put("wp", R.drawable.wpawn);
        pieces.put("wR", R.drawable.wrook);
        pieces.put("wN", R.drawable.wknight);
        pieces.put("wB", R.drawable.wbishop);
        pieces.put("wQ", R.drawable.wqueen);
        pieces.put("wK", R.drawable.wking);
        pieces.put("bp", R.drawable.bpawn);
        pieces.put("bR", R.drawable.brook);
        pieces.put("bN", R.drawable.bknight);
        pieces.put("bB", R.drawable.bbishop);
        pieces.put("bQ", R.drawable.bqueen);
        pieces.put("bK", R.drawable.bking);
        pieces.put("ee", R.drawable.transparent);

        // initialize the board, text, and buttons
        boardView = findViewById(R.id.board);
        text = findViewById(R.id.textView);
        undoButton = findViewById(R.id.undo);
        resignButton = findViewById(R.id.resign);
        drawButton = findViewById(R.id.draw);
        aiButton = findViewById(R.id.ai);

        r1 = c1 = r2 = c2 = -1;
        undo = false;
        if (!undo) {
            undoButton.setEnabled(false);
        }

        game = new Chess();
    }

    public void squareClicked(View v) {
        if (r1 == -1) {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i) == v) {
                    r1 = 8 - (int) (i / 8);
                    c1 = i % 8;
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i) == v) {
                    r2 = 8 - (int) (i / 8);
                    c2 = i % 8;
                }
            }

            move = "" + game.indexToLetter(c1) + r1 + " " + game.indexToLetter(c2) + r2;
            String result = game.playMove(move);
            text.setText(result);

            if (result.equals("Illegal move, try again")) {
                // does not record the move
            } else {
                // records the move into the moves arraylist
                record.addMove(move);
                undo = true;
                undoButton.setEnabled(true);
            }



            // updates board view with correct pieces in correct places using toString method
            // boardView.removeAllViews();

            for (int i = 0; i < 64; i++) {
                int j = i * 3;

                ImageView image = (ImageView) boardView.getChildAt(i);
                String test = game.printBoard(game.getBoard());
                image.setImageResource(pieces.get(game.printBoard(game.getBoard()).substring(j, j + 2)));

            }

            r1 = c1 = r2 = c2 = -1;
        }
    }

    // undoes last move when button is clicked
    public void handleUndo(View view) {
        if (!undo) {
            return;
        }

        game = new Chess();

        for (int i = 0; i < record.getMoves().size() - 1; i++) {
            game.playMove(record.getMoves().get(i));
        }

        for (int i = 0; i < 64; i++) {
            int j = i * 3;

            ImageView image = (ImageView) boardView.getChildAt(i);
            String test = game.printBoard(game.getBoard());
            image.setImageResource(pieces.get(game.printBoard(game.getBoard()).substring(j, j + 2)));
        }

        record.removeLastMove();
        undo = false;
        undoButton.setEnabled(false);
    }

    // plays random move when button is clicked
    public void handleAIMove(View view) {
        String result = game.playRandomMove();
        String[] temp = result.split("brogdon");
        move = temp[1];
        result = temp[0];

        // uses the same logic as the squareClicked method
        text.setText(result);

        if (result.equals("Illegal move, try again")) {
            // does not record the move
        } else {
            // records the move into the moves arraylist
            record.addMove(move);
            undo = true;
            undoButton.setEnabled(true);
        }

        // updates board view with correct pieces in correct places using toString method
        // boardView.removeAllViews();

        for (int i = 0; i < 64; i++) {
            int j = i * 3;

            ImageView image = (ImageView) boardView.getChildAt(i);
            String test = game.printBoard(game.getBoard());
            image.setImageResource(pieces.get(game.printBoard(game.getBoard()).substring(j, j + 2)));
        }

        r1 = c1 = r2 = c2 = -1;
    }

    // draws game when button is clicked
    public void handleDraw(View view) {
        //TODO: implement actual draw game functionality

        String empty = game.playMove("draw");
        pauseGame(view);

        // popup window appears stating that game is a draw
        // if ok is clicked, popup window disappears and new activity for saving game appears

        AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
        builder.setMessage(R.string.Draw);
        // Add the buttons
        builder.setPositiveButton(R.string.Ok, (dialog, id) -> {
            // User clicked OK button

            // create popup for saving game with two buttons: save and cancel
            // add user input for game name
            AlertDialog.Builder builder2 = new AlertDialog.Builder(ChessActivity.this);
            // user text input
            final EditText input = new EditText(ChessActivity.this);
            builder2.setView(input);

            // Add save and cancel buttons
            builder2.setPositiveButton(R.string.Save, (dialog2, id2) -> {
                // User clicked save button
                // saves game and goes back to main activity

                // gets user input for game name
                String gameName = input.getText().toString();

                // set record name as gameName and adds record to records
                record.setName(gameName);
                records.addRecord(record);

                // serializes records to .dat file
                try {
                    records.writeRecords(getFilesDir().getPath() + "records.dat");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Intent intent = new Intent(ChessActivity.this, MainActivity.class);
                startActivity(intent);
            });
            builder2.setNegativeButton(R.string.Cancel, (dialog2, id2) -> {
                // User cancelled the dialog
                // goes back to main activity

                Intent intent = new Intent(ChessActivity.this, MainActivity.class);
                startActivity(intent);
            });

            // Create the AlertDialog
            AlertDialog dialog2 = builder2.create();
            dialog2.show();
        });
        builder.setNegativeButton(R.string.Cancel, (dialog, id) -> {
            // User cancelled the dialog
            // goes back to main activity

            Intent intent = new Intent(ChessActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // resigns game when button is clicked
    public void handleResign(View view) {
        //TODO: implement actual resign game functionality

        // popup window appears stating that game is a resign
        // if ok is clicked, popup window disappears and new activity for saving game appears

        AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
        builder.setMessage(R.string.Resign);
        // Add the buttons
        builder.setPositiveButton(R.string.Ok, (dialog, id) -> {
            // User clicked OK button

            // create popup for saving game with two buttons: save and cancel
            // add user input for game name
            AlertDialog.Builder builder2 = new AlertDialog.Builder(ChessActivity.this);
            // user text input
            final EditText input = new EditText(ChessActivity.this);
            builder2.setView(input);

            // Add save and cancel buttons
            builder2.setPositiveButton(R.string.Save, (dialog2, id2) -> {
                // User clicked save button
                // saves game and goes back to main activity

                // gets user input for game name
                String gameName = input.getText().toString();

                // set record name as gameName and adds record to records
                record.setName(gameName);
                records.addRecord(record);

                // serializes records to .dat file
                try {
                    records.writeRecords(getFilesDir().getPath() + "records.dat");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Intent intent = new Intent(ChessActivity.this, MainActivity.class);
                startActivity(intent);
            });
            builder2.setNegativeButton(R.string.Cancel, (dialog2, id2) -> {
                // User cancelled the dialog
                // goes back to main activity

                Intent intent = new Intent(ChessActivity.this, MainActivity.class);
                startActivity(intent);
            });

            // Create the AlertDialog
            AlertDialog dialog2 = builder2.create();
            dialog2.show();
        });
        builder.setNegativeButton(R.string.Cancel, (dialog, id) -> {
            // User cancelled the dialog
            // goes back to main activity

            Intent intent = new Intent(ChessActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void pauseGame(View view) {
        // disables buttons and board view
        boardView.setEnabled(false);
        resignButton.setEnabled(false);
        drawButton.setEnabled(false);
        undoButton.setEnabled(false);
        aiButton.setEnabled(false);
    }
}