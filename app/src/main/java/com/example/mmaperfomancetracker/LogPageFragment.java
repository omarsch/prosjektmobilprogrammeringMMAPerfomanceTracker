package com.example.mmaperfomancetracker;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.mmaperfomancetracker.adapters.LogAdapter;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class LogPageFragment extends Fragment {

    private ListView logListView;
    private com.google.android.material.textview.MaterialTextView noDataMessage;

    @Override
    public void onStart() {
        super.onStart();
        unselectAllItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logpage,container,false);
        getActivity().setTitle(R.string.logg_button);

        //Building database
        SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        //Initializing variables
        logListView = view.findViewById(R.id.logListView);
        noDataMessage= view.findViewById(R.id.logTextView);
        //Creating an arraylist to contain all training logs from database
        final ArrayList<TrainingLog> trainingLog=new ArrayList<TrainingLog>();

        //Check if the training log database i empty
        if(db.sportDao().getAllTrainingLogs().isEmpty()){
            noDataMessage.setText("No trainings added");
        }

        //Add all data from traininglog database into the the trainingLog arraylist
        trainingLog.addAll(db.sportDao().getAllTrainingLogs());
        //Reverse data to show the last added log on top
        Collections.reverse(trainingLog);

        //Create an adapter for listView
        final LogAdapter adapter= new LogAdapter(getContext(), trainingLog);
        logListView.setAdapter(adapter);


        //If one item in the list view is clicked for long time. Send the data of that item to an
        //intent and redirect the user to SelectedLogPage fragment
        logListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("DATE",adapter.getItem(position).dateAndTime);
                intent.putExtra("TECHNIQUE",adapter.getItem(position).techniqueName);
                intent.putExtra("HOURS",adapter.getItem(position).hours);
                intent.putExtra("MINUTES",adapter.getItem(position).minutes);
                intent.putExtra("LOG_ID",adapter.getItem(position).LogID);
                intent.putExtra("SPORT",adapter.getItem(position).sportName);
                //Telling the main activity that the intent is coming from the LogPage. So the activity
                //can change the fragment to SelectedLogPage fragment
                intent.putExtra("LOG",true);

                getActivity().startActivity(intent);


                return false;
            }
        });

        return view;

    }

    //Method for unselecting items from bottom navigation
    public void unselectAllItems(){
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,true,false);
        for(int i=0;i<3;i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0,true,true);
    }
}


