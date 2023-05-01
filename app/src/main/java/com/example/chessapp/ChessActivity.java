package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chessapp.Game.*;
import com.example.chessapp.Pieces.*;

import java.util.HashMap;

public class ChessActivity extends AppCompatActivity {
    private Chess game;
    private GridLayout boardView;
    private TextView text;
    private int r1, c1, r2, c2;
    private String move;
    private HashMap<String, Integer> pieces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

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

        boardView = findViewById(R.id.board);
        text = findViewById(R.id.textView);
        r1 = c1 = r2 = c2 = -1;
        game = new Chess();
    }

    public void squareClicked(View v) {
        if (r1 == -1) {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i) == v) {
                    r1 = 8-(int)(i / 8);
                    c1 = i % 8;
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i) == v) {
                    r2 = 8-(int)(i / 8);
                    c2 = i % 8;
                }
            }

            move = "" + game.indexToLetter(c1) + r1 + " " + game.indexToLetter(c2) + r2;
            text.setText(move);
            game.playMove(move);

            // updates board view with correct pieces in correct places using toString method
            boardView.removeAllViews();

            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < game.toString().length(); j += 3) {
                    ImageView image = new ImageView(this);
                    image.setImageResource(pieces.get(game.printBoard(game.getBoard()).substring(j, j + 2)));
                    boardView.addView(image);
                }
            }

            r1 = c1 = r2 = c2 = -1;
        }
    }

    // undoes last move when button is clicked
    public void handleUndo(View view) {

    }

    // plays random move when button is clicked
    public void handleAIMove(View view) {

    }

    // draws game when button is clicked
    public void handleDraw(View view) {
        //TODO: implement actual draw game functionality and see why you need to press button twice
        Button draw = findViewById(R.id.draw);
        draw.setOnClickListener(v -> {
            // popup window appears stating that game is a draw
            // if ok is clicked, popup window disappears and new activity for saving game appears

            AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
            builder.setMessage(R.string.Draw);
            // Add the buttons
            builder.setPositiveButton(R.string.Ok, (dialog, id) -> {
                // User clicked OK button
                // new activity for saving game appears

                //Intent intent = new Intent(ChessActivity.this, SaveGameActivity.class);
                //startActivity(intent);
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
        });
    }

    // resigns game when button is clicked
    public void handleResign(View view) {
        //TODO: implement actual resign game functionality
        Button resign = findViewById(R.id.resign);
        resign.setOnClickListener(v -> {
            // popup window appears stating that game is a resign
            // if ok is clicked, popup window disappears and new activity for saving game appears

            AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
            // builder.setMessage(R.string.Resign);
            // Add the buttons
            builder.setPositiveButton(R.string.Ok, (dialog, id) -> {
                // User clicked OK button
                // new activity for saving game appears

                //Intent intent = new Intent(ChessActivity.this, SaveGameActivity.class);
                //startActivity(intent);
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
        });
    }
}