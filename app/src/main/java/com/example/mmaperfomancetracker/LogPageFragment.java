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

    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logpage,container,false);
        getActivity().setTitle(R.string.logg_button);


        SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, "sportLoggerDBv1").allowMainThreadQueries().build();

        listView= view.findViewById(R.id.logListView);

        LogAdapter adapter= new LogAdapter(getContext(), db.sportDao().getAllTrainingLogs());
        listView.setAdapter(adapter);


        return view;

    }

}


