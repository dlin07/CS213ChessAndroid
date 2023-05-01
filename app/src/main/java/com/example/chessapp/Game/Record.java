package com.example.chessapp.Game;

import java.io.Serializable;
import java.util.Comparator;

public class Record implements Serializable {
    private String name;
    private long time;



    public Record(){
        // get epoch millis
        this.time = System.currentTimeMillis();
    }

    public void setName(String name) {
        this.name = name;
    }

    //override toString method to print name and time
    @Override
    public String toString(){
        return this.name + " \\u2014 " + this.time;
    }

    public String getName() {
        return this.name;
    }

    public long getTime() {
        return this.time;
    }
}
