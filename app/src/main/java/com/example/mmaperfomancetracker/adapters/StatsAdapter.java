package com.example.mmaperfomancetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.room.Room;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;

import java.util.ArrayList;
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


        final SportDatabase db = Room.databaseBuilder(getContext(), SportDatabase.class, "sportLoggerDBv1").allowMainThreadQueries().build();

        long totalHours = 0;
        long totalMinutes = 0;

        for (int i=0;i<db.sportDao().sortTechniquesIndividual().size();i++){
            totalHours += db.sportDao().sortTechniquesIndividual().get(i).hours;
            totalMinutes += db.sportDao().sortTechniquesIndividual().get(i).minutes;
        }

        long minutesPlusHoursInMinutesTotal= totalMinutes+(totalHours*60);
        long minutesPlusHoursInMinutesIndividual= statsLog.minutes+(statsLog.hours*60);

        double IndividualPercent= Math.round(((double)minutesPlusHoursInMinutesIndividual /(double)minutesPlusHoursInMinutesTotal)*100);

        com.google.android.material.textview.MaterialTextView sportTechnique,duration;
        ProgressBar progressBar;

        sportTechnique= convertView.findViewById(R.id.textView1Stats);
        duration= convertView.findViewById(R.id.textView2Stats);
        progressBar= convertView.findViewById(R.id.techniqueProgress);


        sportTechnique.setText(statsLog.techniqueName);
        duration.setText("Minutes: "+ minutesPlusHoursInMinutesIndividual);
        progressBar.setProgress((int)IndividualPercent);

        return convertView;

    }

}
