package com.example.mmaperfomancetracker;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mmaperfomancetracker.adapters.SportAdapter;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.Technique;

import java.util.ArrayList;
import java.util.List;

public class LogPageFragment extends Fragment {

    com.google.android.material.textview.MaterialTextView sportId,sport1,sport2;
    ListView listView;
    private static final ArrayList<Sport> sportArrayList=new ArrayList<Sport>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logpage,container,false);

        sportId= view.findViewById(R.id.textView1);
        sport1= view.findViewById(R.id.textView2);
        sport2= view.findViewById(R.id.textView3);
        listView= view.findViewById(R.id.logListView);


        Sport sport=new Sport(1,"Kickboxing");
        Sport sport1=new Sport(2,"Grappling");


        SportAdapter adapter= new SportAdapter(getContext(), sportArrayList);
        listView.setAdapter(adapter);

        adapter.add(sport);
        adapter.add(sport1);


        return view;

    }

}


