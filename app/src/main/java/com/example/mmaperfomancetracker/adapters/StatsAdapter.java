package com.example.mmaperfomancetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.List;

public class StatsAdapter extends ArrayAdapter<TrainingLog> {

    public StatsAdapter(Context context, List<TrainingLog> trainingLogs){
        super(context,0, trainingLogs);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TrainingLog trainingLog=getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_adapter_view_layout, parent,false);
        }

        com.google.android.material.textview.MaterialTextView sportTechnique,duration;
        ProgressBar progressBar;

        sportTechnique= convertView.findViewById(R.id.textView1Stats);
        duration= convertView.findViewById(R.id.textView2Stats);
        progressBar= convertView.findViewById(R.id.techniqueProgress);

        long totalMinutes= trainingLog.minutes+(trainingLog.hours*60);

        sportTechnique.setText(trainingLog.techniqueName);
        duration.setText("Minutes: "+totalMinutes);
        progressBar.setProgress(50);

        return convertView;

    }

}
