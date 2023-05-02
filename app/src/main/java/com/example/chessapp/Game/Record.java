package com.example.chessapp.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable {
    private String name;
    private long date;

    private ArrayList<String> moves;

    public Record(){
        // get epoch millis
        this.date = System.currentTimeMillis();
        this.moves = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    //override toString method to print name and time
    @Override
    public String toString(){
        return this.name + " \u2014 " + this.date;
    }

    public String getName() {
        return this.name;
    }

    public long getDate() {
        return this.date;
    }

    public void addMove(String move){
        this.moves.add(move);
    }

    public void removeLastMove(){
        this.moves.remove(this.moves.size() - 1);
    }

    public ArrayList<String> getMoves(){
        return this.moves;
    }
}
