package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RecordsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_view);

        // get the intent that started this activity and extract the sortType
        Intent intent = getIntent();
        String sortType = intent.getStringExtra("sortType");



    }
}