package com.example.mmaperfomancetracker.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sport {

    @PrimaryKey()
    public long sportId;

    public String sportName;

    @Override
    public String toString(){
        return  sportName;
    }

}
