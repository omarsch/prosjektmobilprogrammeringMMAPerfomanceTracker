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
import com.example.mmaperfomancetracker.comparators.SortByMinutes;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class StatsPageFragment extends Fragment {
    private  ListView statsListView;
    private com.google.android.material.textview.MaterialTextView noDataMessage;

    @Override
    public void onStart() {
        super.onStart();
        unselectAllItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_statspage,container,false);
        getActivity().setTitle(R.string.stats_button);
        //Building database
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        //Creating arraylist to containt stats
        final ArrayList<StatsLog> statsLogs=new ArrayList<StatsLog>();

        //Adding all stats from statsdatabse
        statsLogs.addAll(db.sportDao().sortTechniquesIndividual());
        //Sorting it by decreasing by minutes
        Collections.sort(statsLogs, new SortByMinutes());

        //Initializing variables
        statsListView = view.findViewById(R.id.statsListView);
        noDataMessage= view.findViewById(R.id.statsTextView);

        //Show message if there is no data in the database
        if(statsLogs.isEmpty()){
            noDataMessage.setText("No trainings added");
        }

        //Add all data to statsListView via an adapter
        StatsAdapter adapter= new StatsAdapter(getContext(),statsLogs);
        statsListView.setAdapter(adapter);



        return view;
    }

    //Unselect all items from bottom navigation.
    public void unselectAllItems(){
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,true,false);
        for(int i=0;i<3;i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0,true,true);
    }

}
