package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
    }

    public void pieceClicked() {
        //TODO: add implementation for when a piece is clicked
    }

    public void onDraw() {
        GridLayout grid = new GridLayout(this);

        // draws the 8x8 chess board

    }
}