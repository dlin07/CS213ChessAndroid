package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
    }

    // go to RecordsViewActivity when either button is clicked

    //sorts listview in RecordsViewActivity by date
    public void sortByDateClicked(View view) {
        Intent intent = new Intent(this, RecordsViewActivity.class);
        intent.putExtra("sortType", "date");
        startActivity(intent);
    }

    //sorts listview in RecordsViewActivity by name
    public void sortByNameClicked(View view) {
        Intent intent = new Intent(this, RecordsViewActivity.class);
        intent.putExtra("sortType", "name");
        startActivity(intent);
    }
}