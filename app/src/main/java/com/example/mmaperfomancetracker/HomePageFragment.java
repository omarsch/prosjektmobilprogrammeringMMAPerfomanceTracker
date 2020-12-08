package com.example.mmaperfomancetracker;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import com.example.mmaperfomancetracker.comparators.SortByMinutes;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.StatsLog;
import java.util.ArrayList;
import java.util.Collections;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageFragment extends Fragment {

    private com.google.android.material.card.MaterialCardView logCard, statsCard, timeCard, mapsCard;
    private ProgressBar progressBar1,progressBar2,progressBar3;
    private com.google.android.material.textview.MaterialTextView textViewprogressBar1,textViewprogressBar2,textViewprogressBar3;
    BottomNavigationView bottomNavigationView;

    @Override
    public void onStart() {
        super.onStart();
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        getActivity().setTitle(R.string.home_button);

        //Building the database
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class,  String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        //Creating an arrayList to for stats
        final ArrayList<StatsLog> statsLogs=new ArrayList<StatsLog>();

        //Adding all stats from the database into the statsLog arrayList
        statsLogs.addAll(db.sportDao().sortTechniquesIndividual());
        //Sorting it to decreasing by minutes
        Collections.sort(statsLogs, new SortByMinutes());

        //Initiating variables
        logCard= view.findViewById(R.id.loggCard);
        statsCard= view.findViewById(R.id.statsCard);
        timeCard= view.findViewById(R.id.timeCard);
        mapsCard= view.findViewById(R.id.mapsCard);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);

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

        //If there is no data in stats database
        if(statsLogs.isEmpty()){
            //Tell user there is no data
            textViewprogressBar1.setText("No data");
            textViewprogressBar2.setText("No data");
            textViewprogressBar3.setText("No data");
        }
        //If there is one data in stats database
        else if(statsLogs.size()==1){
            //Display the one data on the progressbar
            double progressOnBar3= Math.round(((double)statsLogs.get(0).getTotalMinutes()/(double)totalMinutesOfAll)*100);
            progressBar3.setProgress((int) progressOnBar3);
            textViewprogressBar3.setText(String.valueOf(statsLogs.get(0).techniqueName));

        }
        //if there is 2 data in the database
        else if(statsLogs.size()==2){
            //Display the 2 data in the database
            double progressOnBar3= Math.round(((double)statsLogs.get(0).getTotalMinutes()/(double)totalMinutesOfAll)*100);
            double progressOnBar2= Math.round(((double)statsLogs.get(1).getTotalMinutes()/(double)totalMinutesOfAll)*100);

            progressBar2.setProgress((int) progressOnBar2);
            progressBar3.setProgress((int) progressOnBar3);

            textViewprogressBar2.setText(String.valueOf(statsLogs.get(1).techniqueName));
            textViewprogressBar3.setText(String.valueOf(statsLogs.get(0).techniqueName));

        }
        //If there is more than 2 data in the database show the top 3 data in the database
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

        //Changing fragments
        logCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new LogPageFragment();
                replaceFragment(fragment,false);
                unselectAllItems();
            }
        });
        //Changing fragments
        statsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new StatsPageFragment();
                replaceFragment(fragment,false);
                unselectAllItems();
            }
        });
        //Changing fragments
        timeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new TimerPageFragment();
                replaceFragment(fragment,false);
                unselectAllItems();

            }
        });
        //Redirecting to google maps
        mapsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=MMA"));
                startActivity(intent);

            }
        });


        return view;


    }

    //Method to replace fragments
    public void replaceFragment(Fragment fragment, boolean backstack){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        if(backstack){
            fragmentTransaction.addToBackStack(null);
        }
    }

    //Method to unselect items from bottom navigation
    public void unselectAllItems(){
        bottomNavigationView.getMenu().setGroupCheckable(0,true,false);
        for(int i=0;i<3;i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0,true,true);
    }

}