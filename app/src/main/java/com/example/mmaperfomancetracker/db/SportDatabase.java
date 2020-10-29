package com.example.mmaperfomancetracker.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.concurrent.Executors;

@Database(entities = {Sport.class, Technique.class, TrainingLog.class}, version = 1)
 public abstract class SportDatabase extends RoomDatabase {


    public abstract SportDao sportDao();


}
