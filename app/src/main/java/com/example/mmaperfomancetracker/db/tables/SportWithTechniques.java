package com.example.mmaperfomancetracker.db.tables;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;


public class SportWithTechniques {

    @Embedded public Sport sport;
    @Relation(
            parentColumn = "sportId",
            entityColumn = "sportTechniqueId"
    )
    public List<Technique> techniques;

    @Override
    public String toString(){
        return sport+ " " + techniques.toString();
    }
}
