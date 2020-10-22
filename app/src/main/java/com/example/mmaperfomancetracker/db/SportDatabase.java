package com.example.mmaperfomancetracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Sport.class}, version = 1)
 public abstract class SportDatabase extends RoomDatabase {

    public abstract SportDao sportDao();


}
