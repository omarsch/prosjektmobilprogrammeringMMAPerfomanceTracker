package com.example.mmaperfomancetracker.db.tables;

import androidx.room.Entity;

@Entity
public class StatsLog {

    public String techniqueName;

    public long hours;
    public long minutes;

    public long getTotalMinutes() {
        long totalMinutes=minutes+(hours*60);
        return totalMinutes;
    }



    @Override
    public String toString() {
        return "techniqueName='" + techniqueName + '\'' +
                ", hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }


}
