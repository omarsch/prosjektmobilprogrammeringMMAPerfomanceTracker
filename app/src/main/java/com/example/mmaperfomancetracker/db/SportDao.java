package com.example.mmaperfomancetracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.List;

@Dao
public interface SportDao {

    //Sport Table Queries
    @Query("SELECT * FROM Sport")
    List<Sport> getAllSports();

    @Query("SELECT sportId FROM Sport")
    List<Integer> getAllSportsId();

    @Query("DELETE FROM Sport")
    void deleteSports();

    @Insert
    void addSport(Sport sport);

    @Insert
    void addAllSports(Sport... sport);


    //Technique Queries Table
    @Insert
    void addTechnique(Technique technique);


    @Insert
    void addAllTechniques(Technique... techniques);

    @Query("DELETE FROM Technique")
    void deleteTechnique();

    @Query("SELECT techniqueName FROM Technique")
    List<String> getAllTechniques();

    //SportWithTechniques Queries Table
    @Transaction
    @Query("SELECT * FROM Sport")
    public List<SportWithTechniques> getSportsWithTechniques();

    //TrainingLog Queries Table
    @Insert
    void addLog(TrainingLog trainingLog);

    @Query("SELECT * FROM TrainingLog")
    List<TrainingLog> getAllTrainingLogs();

    @Query("DELETE FROM TrainingLog")
    void deleteFromTrainingLog();

    @Query("SELECT techniqueName,hours,minutes," +
            "sum(hours) as hours, sum(minutes) as minutes" +
            " FROM TrainingLog GROUP BY techniqueName")
    List<StatsLog> sortTechniquesIndividual();



}
