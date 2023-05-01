package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.chessapp.Game.*;
import com.example.chessapp.Pieces.*;

public class ChessActivity extends AppCompatActivity {
    private Square[][] board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
    }

    public void squareClicked(AdapterView<?> parent, View view, int position, long id) {
        String square = parent.getItemAtPosition(position).toString();


        //String rank = id.substring(0, 1);
        //String file = id.substring(1, 2);;

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
            builder.setMessage(R.string.Resign);
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