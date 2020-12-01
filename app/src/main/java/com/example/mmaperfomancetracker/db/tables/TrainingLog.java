package com.example.mmaperfomancetracker.db.tables;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class TrainingLog {

    @PrimaryKey(autoGenerate = true)
    public long LogID;

    public String sportName;
    public String techniqueName;
    public long hours;
    public long minutes;
    public String dateAndTime;

    public TrainingLog(String sportName, String techniqueName, long hours, long minutes, String dateAndTime) {
        this.sportName = sportName;
        this.techniqueName = techniqueName;
        this.hours = hours;
        this.minutes = minutes;
        this.dateAndTime = dateAndTime;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public long getLogID() {
        return LogID;
    }

    @Override
    public String toString(){
        return  sportName+" "+techniqueName+" "+hours+" "+minutes + " "+ dateAndTime;
    }
}
