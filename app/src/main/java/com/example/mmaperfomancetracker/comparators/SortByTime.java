package com.example.mmaperfomancetracker.comparators;

import com.example.mmaperfomancetracker.db.tables.StatsLog;

import java.util.Comparator;

public class SortByTime implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        StatsLog s1= (StatsLog) o1;
        StatsLog s2= (StatsLog) o2;

        return (int) (s2.getTotalMinutes()-s1.getTotalMinutes());
    }
}
