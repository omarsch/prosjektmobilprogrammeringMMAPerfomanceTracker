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
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class LogPageFragment extends Fragment {

    private ListView listView;
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


        SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        listView= view.findViewById(R.id.logListView);
        noDataMessage= view.findViewById(R.id.logTextView);
        final ArrayList<TrainingLog> trainingLog=new ArrayList<TrainingLog>();

        if(db.sportDao().getAllTrainingLogs().isEmpty()){
            noDataMessage.setText("No trainings added");
        }


        trainingLog.addAll(db.sportDao().getAllTrainingLogs());
        Collections.reverse(trainingLog);
        final LogAdapter adapter= new LogAdapter(getContext(), trainingLog);
        listView.setAdapter(adapter);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("DATE",adapter.getItem(position).dateAndTime);
                intent.putExtra("TECHNIQUE",adapter.getItem(position).techniqueName);
                intent.putExtra("HOURS",adapter.getItem(position).hours);
                intent.putExtra("MINUTES",adapter.getItem(position).minutes);
                intent.putExtra("LOG_ID",adapter.getItem(position).LogID);
                intent.putExtra("SPORT",adapter.getItem(position).sportName);
                intent.putExtra("LOG",true);

                getActivity().startActivity(intent);


                return false;
            }
        });

        return view;

    }

    public void unselectAllItems(){
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,true,false);
        for(int i=0;i<3;i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0,true,true);
    }
}


