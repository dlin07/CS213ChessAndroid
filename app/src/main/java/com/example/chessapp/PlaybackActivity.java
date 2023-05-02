package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.chessapp.Game.Chess;
import com.example.chessapp.Game.Record;

import java.util.HashMap;

public class PlaybackActivity extends AppCompatActivity {

    private Record record;

    private int moveIndex;

    private Chess game;

    private GridLayout boardView;

    private HashMap<String, Integer> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        // get the intent that started this activity and extract the record
        Intent intent = getIntent();
        record = (Record) intent.getSerializableExtra("record");

        //print all the moves
        for (String move : record.getMoves()) {
            System.out.println(move);
        }


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

        boardView = findViewById(R.id.glPlaybackBoard);
        game = new Chess();

        moveIndex = 0;
    }

    public void playbackNextMove(View view) {
        // send a move using the current moveIndex
        String move = record.getMoves().get(moveIndex);

        System.out.println(move);

        String result = game.playMove(move);

        System.out.println(result);


        // updates board view with correct pieces in correct places using toString method
        // boardView.removeAllViews();

        for (int i = 0; i < 64; i++) {
            int j = i * 3;

            ImageView image = (ImageView) boardView.getChildAt(i);
            String test = game.printBoard(game.getBoard());
            image.setImageResource(pieces.get(game.printBoard(game.getBoard()).substring(j, j + 2)));

        }

        // increment the moveIndex
        moveIndex++;
    }
}