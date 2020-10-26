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

    public Technique(long techniqueID, long sportTechniqueId, String techniqueName) {
        this.techniqueID = techniqueID;
        this.sportTechniqueId = sportTechniqueId;
        this.techniqueName = techniqueName;
    }

    @Override
    public String toString(){
        return techniqueName;
    }
}
