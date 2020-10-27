package com.example.mmaperfomancetracker.db.tables;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrainingLog {

    @PrimaryKey(autoGenerate = true)
    public long LogID;

    public String sportName;
    public String techniqueName;
    public long hours;
    public long minutes;

    public TrainingLog(String sportName, String techniqueName, long hours, long minutes) {
        this.sportName = sportName;
        this.techniqueName = techniqueName;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public String toString(){
        return  sportName+" "+techniqueName+" "+hours+" "+minutes;
    }
}
