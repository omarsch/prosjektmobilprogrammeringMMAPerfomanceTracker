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


    public static Technique[] populateData(){
        return new Technique[]{
                new Technique(1,1,"Front Kick"),
                new Technique(2,1,"Side Kick"),
                new Technique(3,1,"Roundhouse Kick"),
                new Technique(4,2,"Double Leg Takedown"),
                new Technique(5,2,"Single Leg Takedown"),
                new Technique(6,2,"Triangle Choke"),
        };
    }

    @Override
    public String toString(){
        return techniqueName;
    }
}
