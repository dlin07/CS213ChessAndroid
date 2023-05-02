package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chessapp.Game.Record;

public class PlaybackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        // get the intent that started this activity and extract the record
        Intent intent = getIntent();
        Record record = (Record) intent.getSerializableExtra("record");

        //print all the moves
        for(String move : record.getMoves()){
            System.out.println(move);
        }
    }
}