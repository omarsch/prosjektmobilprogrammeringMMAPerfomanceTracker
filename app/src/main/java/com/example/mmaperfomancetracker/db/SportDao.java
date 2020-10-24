package com.example.mmaperfomancetracker.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SportDao {

    @Query("SELECT Sport FROM Sport")
    List<String> getAll();

    @Query("DELETE FROM Sport")
    void deleteSports();

    @Insert
    void addSport(Sport sport);

}
