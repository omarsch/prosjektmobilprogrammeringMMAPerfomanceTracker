package com.example.mmaperfomancetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.mmaperfomancetracker.adapters.StatsAdapter;
import com.example.mmaperfomancetracker.comparators.SortByTime;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.Technique;

import java.util.ArrayList;
import java.util.Collections;

public class StatsPageFragment extends Fragment {
    ListView listView;
    com.google.android.material.textview.MaterialTextView noDataMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_statspage,container,false);
        getActivity().setTitle(R.string.stats_button);
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        final ArrayList<StatsLog> statsLogs=new ArrayList<StatsLog>();

        statsLogs.addAll(db.sportDao().sortTechniquesIndividual());
        Collections.sort(statsLogs, new SortByTime());

        listView= view.findViewById(R.id.statsListView);
        noDataMessage= view.findViewById(R.id.statsTextView);

        if(statsLogs.isEmpty()){
            noDataMessage.setText("No trainings added");
        }


        StatsAdapter adapter= new StatsAdapter(getContext(),statsLogs);
        listView.setAdapter(adapter);



        return view;
    }

}
