package com.example.mmaperfomancetracker.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sport {

    @PrimaryKey()
    public long sportId;

    public String sportName;

    public Sport(long sportId, String sportName) {
        this.sportId = sportId;
        this.sportName = sportName;
    }

    public static Sport[] populateData(){
        return new Sport[]{
                new Sport(1,"Kickboxing"),
                new Sport(2,"Grappling")
        };
    }

    @Override
    public String toString(){
        return  sportName;
    }

}
