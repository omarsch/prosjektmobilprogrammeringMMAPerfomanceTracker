package com.example.mmaperfomancetracker.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Technique {


    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "Technique")
    public String sport;
}
