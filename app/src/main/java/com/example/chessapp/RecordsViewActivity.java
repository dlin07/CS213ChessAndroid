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
        Path recordsData = Paths.get("/data/user/0/com.example.chessapp/files/records.dat");

        if (Files.exists(recordsData)) {
            // loads the users from the .dat file
            try {
                records.loadRecords(recordsData.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Record t2 = new Record();
        t2.setName("test2");

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Record t1 = new Record();
        t1.setName("test1");

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Record t3 = new Record();
        t3.setName("test3");

        records.addRecord(t1);
        records.addRecord(t2);
        records.addRecord(t3);

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

       /* Path records = Paths.get("/data/user/0/com.example.chessapp/files/records.txt");
        try {
            if(!Files.exists(records)){
                Files.createFile(records);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner;
        try {
            scanner = new Scanner(records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }

        scanner.close();*/
    }
}