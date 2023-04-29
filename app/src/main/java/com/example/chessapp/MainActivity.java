package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // go to ChessActivity when start button is clicked
    public void startGameClicked(View view) {
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }
}