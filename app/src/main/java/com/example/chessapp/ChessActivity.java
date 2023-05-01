package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.chessapp.Game.*;
import com.example.chessapp.Pieces.*;

public class ChessActivity extends AppCompatActivity {
    private Chess game;
    private GridLayout boardView;
    private TextView text;
    private int r1, c1, r2, c2 = -1;
    private String move;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        boardView = findViewById(R.id.board);
        text = findViewById(R.id.textView);
        game = new Chess();
    }

    public void squareClicked(View v) {
        if (r1 == -1) {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i).getId() == v.getId()) {
                    r1 = 8-(i / 8);
                    c1 = i % 8;
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                if (boardView.getChildAt(i).getId() == v.getId()) {
                    r2 = 8-(i / 8);
                    c2 = i % 8;
                }
            }

            move = "" + game.indexToLetter(c1) + r1 + " " + game.indexToLetter(c2) + r2;
            text.setText(move);
            game.playMove(move);
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