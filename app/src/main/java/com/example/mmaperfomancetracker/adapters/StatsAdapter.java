package com.example.mmaperfomancetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.List;

public class StatsAdapter extends ArrayAdapter<StatsLog> {

    public StatsAdapter(Context context, List<StatsLog> statsLog){
        super(context,0, statsLog);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        StatsLog statsLog=getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_adapter_view_layout, parent,false);
        }

        com.google.android.material.textview.MaterialTextView sportTechnique,duration;
        ProgressBar progressBar;

        sportTechnique= convertView.findViewById(R.id.textView1Stats);
        duration= convertView.findViewById(R.id.textView2Stats);
        progressBar= convertView.findViewById(R.id.techniqueProgress);

        long totalMinutes= statsLog.minutes+(statsLog.hours*60);

        sportTechnique.setText(statsLog.techniqueName);
        duration.setText("Minutes: "+totalMinutes);
        progressBar.setProgress(50);

        return convertView;

    }

}
