package com.example.chessapp.Pieces;

import com.example.chessapp.Game.Record;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RecordsList implements Serializable {
    ArrayList<Record> records;

    public RecordsList(){
        records = new ArrayList<>();
    }

    public void writeRecords(String recordsData) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(recordsData));
        oos.writeObject(this.records);
        oos.close();
    }

    public void loadRecords(String recordsData) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(recordsData));
        @SuppressWarnings("unchecked")
        ArrayList<Record> records = (ArrayList<Record>) ois.readObject();
        ois.close();

        this.records = records;
    }

    public void addRecord(Record record){
        records.add(record);
    }

    public ArrayList<Record> getRecords(){
        return records;
    }
}
