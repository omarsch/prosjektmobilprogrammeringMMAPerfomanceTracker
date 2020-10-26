package com.example.mmaperfomancetracker.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.Technique;

import java.util.List;

@Dao
public interface SportDao {

    //Sport Table Queries
    @Query("SELECT SportName FROM Sport")
    List<String> getAllSports();

    @Query("SELECT sportId FROM Sport")
    List<Integer> getAllSportsId();

    @Query("DELETE FROM Sport")
    void deleteSports();

    @Insert
    void addSport(Sport sport);

    //Technique Queries Table
    @Insert
    void addTechnique(Technique technique);

    @Query("DELETE FROM Technique")
    void deleteTechnique();

    @Query("SELECT techniqueName FROM Technique")
    List<String> getAllTechniques();

    //SportWithTechniques Queries Table
    @Transaction
    @Query("SELECT * FROM Sport")
    public List<SportWithTechniques> getSportsWithTechniques();


}
