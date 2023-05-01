package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

        Path records = Paths.get("/data/user/0/com.example.chessapp/files/records.txt");
        try {
            if(!Files.exists(records)){
                System.out.println("creating file");
                Files.createFile(records);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileInputStream in;
        try {
            in = openFileInput("records.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(line);
        }
        try {
            inputStreamReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}