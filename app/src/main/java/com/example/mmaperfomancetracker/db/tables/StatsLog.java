package com.example.mmaperfomancetracker.db.tables;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class StatsLog {


    public String techniqueName;

    public long hours;
    public long minutes;

}
