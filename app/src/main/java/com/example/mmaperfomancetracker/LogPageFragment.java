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

import com.example.mmaperfomancetracker.adapters.LogAdapter;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logpage,container,false);
        getActivity().setTitle(R.string.logg_button);


        SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        listView= view.findViewById(R.id.logListView);
        noDataMessage= view.findViewById(R.id.logTextView);

        if(db.sportDao().getAllTrainingLogs().isEmpty()){
            noDataMessage.setText("No trainings added");
        }

        LogAdapter adapter= new LogAdapter(getContext(), db.sportDao().getAllTrainingLogs());
        listView.setAdapter(adapter);



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


