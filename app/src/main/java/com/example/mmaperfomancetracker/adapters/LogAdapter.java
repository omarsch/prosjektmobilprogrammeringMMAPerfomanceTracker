package com.example.mmaperfomancetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends ArrayAdapter<TrainingLog> {

    public LogAdapter(Context context, List<TrainingLog> trainingLogs){
        super(context,0, trainingLogs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TrainingLog trainingLog=getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_adapter_view_layout, parent,false);
        }

        com.google.android.material.textview.MaterialTextView sportName,sportTechnique,time;

        sportName= convertView.findViewById(R.id.textView1);
        sportTechnique= convertView.findViewById(R.id.textView2);
        time= convertView.findViewById(R.id.textView3);


        sportName.setText(trainingLog.sportName);
        sportTechnique.setText(("Teknikk: "+String.valueOf(trainingLog.techniqueName)));
        time.setText("Timer: "+ trainingLog.hours+" Minutter: "+ trainingLog.minutes);

        return convertView;

    }

}
