package com.example.mmaperfomancetracker.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Technique {

    @PrimaryKey
    public long techniqueID;

    public long sportTechniqueId;

    public String techniqueName;

    @Override
    public String toString(){
        return techniqueName;
    }
}
