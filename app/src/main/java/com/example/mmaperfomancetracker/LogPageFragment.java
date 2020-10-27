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

import java.util.ArrayList;

public class LogPageFragment extends Fragment {

    com.google.android.material.textview.MaterialTextView sportId,sport1,sport2;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logpage,container,false);


        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, "sportLoggerDatabase").allowMainThreadQueries().build();



        sportId= view.findViewById(R.id.textView1);
        sport1= view.findViewById(R.id.textView2);
        sport2= view.findViewById(R.id.textView3);
        listView= view.findViewById(R.id.logListView);


        Sport sport=new Sport(1,"Kickboxing");
        Sport sport1=new Sport(2,"Grappling");


        LogAdapter adapter= new LogAdapter(getContext(), db.sportDao().getAllTrainingLogs());
        listView.setAdapter(adapter);


        return view;

    }

}


