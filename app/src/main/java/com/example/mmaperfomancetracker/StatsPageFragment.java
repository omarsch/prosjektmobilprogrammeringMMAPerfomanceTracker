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
import com.example.mmaperfomancetracker.adapters.StatsAdapter;
import com.example.mmaperfomancetracker.db.SportDatabase;

public class StatsPageFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_statspage,container,false);

        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, "sportLoggerDBv1").allowMainThreadQueries().build();

        listView= view.findViewById(R.id.statsListView);

        StatsAdapter adapter= new StatsAdapter(getContext(), db.sportDao().findTechniqueName("Side Kick"));
        listView.setAdapter(adapter);










        return view;
    }

}
