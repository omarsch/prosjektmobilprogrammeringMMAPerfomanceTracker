package com.example.mmaperfomancetracker.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.Technique;

@Database(entities = {Sport.class, Technique.class}, version = 1)
 public abstract class SportDatabase extends RoomDatabase {

    public abstract SportDao sportDao();


}
