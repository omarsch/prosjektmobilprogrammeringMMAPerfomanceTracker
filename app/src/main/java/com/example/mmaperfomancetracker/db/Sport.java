package com.example.mmaperfomancetracker.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sport {

    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "Sport")
    public String sport;

}
