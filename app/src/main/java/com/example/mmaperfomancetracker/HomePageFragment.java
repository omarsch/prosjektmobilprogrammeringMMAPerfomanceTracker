package com.example.mmaperfomancetracker;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;


import com.example.mmaperfomancetracker.comparators.SortByTime;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;


public class HomePageFragment extends Fragment {

    private com.google.android.material.card.MaterialCardView logCard, statsCard, notifCard;
    private ProgressBar progressBar1,progressBar2,progressBar3;
    private com.google.android.material.textview.MaterialTextView textViewprogressBar1,textViewprogressBar2,textViewprogressBar3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        getActivity().setTitle(R.string.home_button);


        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class,  String.valueOf(R.string.database_name)).allowMainThreadQueries().build();


        final ArrayList<StatsLog> statsLogs=new ArrayList<StatsLog>();

        statsLogs.addAll(db.sportDao().sortTechniquesIndividual());
        Collections.sort(statsLogs, new SortByTime());

        logCard= view.findViewById(R.id.loggCard);
        statsCard= view.findViewById(R.id.statsCard);
        notifCard= view.findViewById(R.id.notifCard);

        progressBar1= view.findViewById(R.id.progressBar1);
        progressBar2= view.findViewById(R.id.progressBar2);
        progressBar3= view.findViewById(R.id.progressBar3);

        textViewprogressBar1= view.findViewById(R.id.textViewProgressBar1);
        textViewprogressBar2= view.findViewById(R.id.textViewProgressBar2);
        textViewprogressBar3= view.findViewById(R.id.textViewProgressBar3);

        long totalMinutesOfAll = 0;
        for(int i=0; i<statsLogs.size();i++){

            totalMinutesOfAll +=  statsLogs.get(i).getTotalMinutes();
        }


        if(statsLogs.isEmpty()){

            textViewprogressBar1.setText("No data");
            textViewprogressBar2.setText("No data");
            textViewprogressBar3.setText("No data");
        }

        else if(statsLogs.size()==1){
            double progressOnBar3= Math.round(((double)statsLogs.get(0).getTotalMinutes()/(double)totalMinutesOfAll)*100);

            progressBar3.setProgress((int) progressOnBar3);

            textViewprogressBar3.setText(String.valueOf(statsLogs.get(0).techniqueName));

        }
        else if(statsLogs.size()==2){
            double progressOnBar3= Math.round(((double)statsLogs.get(0).getTotalMinutes()/(double)totalMinutesOfAll)*100);
            double progressOnBar2= Math.round(((double)statsLogs.get(1).getTotalMinutes()/(double)totalMinutesOfAll)*100);

            progressBar2.setProgress((int) progressOnBar2);
            progressBar3.setProgress((int) progressOnBar3);

            textViewprogressBar2.setText(String.valueOf(statsLogs.get(1).techniqueName));
            textViewprogressBar3.setText(String.valueOf(statsLogs.get(0).techniqueName));

        }
        else {
            double progressOnBar3= Math.round(((double)statsLogs.get(0).getTotalMinutes()/(double)totalMinutesOfAll)*100);
            double progressOnBar2= Math.round(((double)statsLogs.get(1).getTotalMinutes()/(double)totalMinutesOfAll)*100);
            double progressOnBar1= Math.round(((double)statsLogs.get(2).getTotalMinutes()/(double)totalMinutesOfAll)*100);

            progressBar1.setProgress((int) progressOnBar1);
            progressBar2.setProgress((int) progressOnBar2);
            progressBar3.setProgress((int) progressOnBar3);

            textViewprogressBar1.setText(String.valueOf(statsLogs.get(2).techniqueName));
            textViewprogressBar2.setText(String.valueOf(statsLogs.get(1).techniqueName));
            textViewprogressBar3.setText(String.valueOf(statsLogs.get(0).techniqueName));
        }












        logCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new LogPageFragment();
                replaceFragment(fragment);
            }
        });

        statsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new StatsPageFragment();
                replaceFragment(fragment);

            }
        });

        notifCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new TimerPageFragment();
                replaceFragment(fragment);

            }
        });


        return view;


    }


    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}