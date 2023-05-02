package com.example.chessapp;

import static java.lang.Thread.sleep;
import static java.util.Comparator.comparing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chessapp.Game.Record;
import com.example.chessapp.Pieces.RecordsList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RecordsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_view);

        // get the intent that started this activity and extract the sortType
        Intent intent = getIntent();
        String sortType = intent.getStringExtra("sortType");

        RecordsList records = new RecordsList();
        //TODO: serialize records after game ends

        // checks if the users.dat file exists
        Path recordsData = Paths.get(getFilesDir().getPath()+ "records.dat");

        if (Files.exists(recordsData)) {
            // loads the users from the .dat file
            try {
                records.loadRecords(recordsData.toString());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        //sort records based on sortType
        if(sortType.equals("name")){
            records.getRecords().sort(comparing(Record::getName));
        } else if(sortType.equals("date")){
            records.getRecords().sort(comparing(Record::getDate));
        }

        ListView lvGameRecords = findViewById(R.id.lvGameRecords);

        // create the adapter to convert the array to views
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, records.getRecords());

        // attach the adapter to a ListView
        lvGameRecords.setAdapter(adapter);

        // create listener for each item in the list
        lvGameRecords.setOnItemClickListener((parent, view, position, id) -> {
            // get the selected item text from ListView
            Record selectedItem = (Record) parent.getItemAtPosition(position);

            // create intent to start the PlaybackActivity
            Intent playbackIntent = new Intent(this, PlaybackActivity.class);

            // pass the selected item to the PlaybackActivity
            playbackIntent.putExtra("record", selectedItem);

            // start the PlaybackActivity
            startActivity(playbackIntent);
        });
    }
}