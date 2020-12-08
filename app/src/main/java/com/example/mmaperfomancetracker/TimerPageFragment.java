package com.example.mmaperfomancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;
import com.example.mmaperfomancetracker.services.TimerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimerPageFragment extends Fragment{

    private Chronometer timer;
    private boolean running, trainingStatus;
    private long timePausedOffset,timeAbsent,appClosedCurrentTime,appOpenedCurrentTime;
    private long timerStoppedMillis, timerStoppedDuration;
    private int hours=0,minutes=0;
    private String chosenSport,chosenTechnique;
    private com.google.android.material.button.MaterialButton start,stop,add;
    private com.google.android.material.textview.MaterialTextView techniqueText;
    private com.google.android.material.textfield.TextInputLayout selectedSport, selectedTechnique;

    //When the fragment starts
    @Override
    public void onStart() {
        super.onStart();

        unselectAllItems();

        //Getting all saved variables in fragment
        SharedPreferences pref= this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);

        running= pref.getBoolean("running",false);
        trainingStatus= pref.getBoolean("trainingStatus",false);
        chosenSport=pref.getString("chosenSport",null);
        chosenTechnique=pref.getString("chosenTechnique",null);
        timePausedOffset =pref.getLong("pauseOffset",0);
        appClosedCurrentTime=pref.getLong("appClosedCurrentTime",0);
        timerStoppedMillis=pref.getLong("timerStoppedMillis",0);
        timerStoppedDuration=pref.getLong("timerStoppedDuration",0);
        AutoCompleteTextView editTextSportView= getView().findViewById(R.id.filled_exposed_dropdown_technique_timer);


        //Calculating how long the fragment was closed
        appOpenedCurrentTime=System.currentTimeMillis();
        //Calculating how long the fragment was closed
        timeAbsent=appOpenedCurrentTime-appClosedCurrentTime;
        //Calculating how long the timer was stopped
        timerStoppedDuration=appClosedCurrentTime-timerStoppedMillis;


        //If the the user has started a training session
        if(trainingStatus) {
            //If the timer is running
            if (running) {
                //Start the timer and add the time fragment was closed when the timer was running.
                //And add the pauseOffset time
                timer.setBase((SystemClock.elapsedRealtime() - timePausedOffset) - timeAbsent);
                timer.start();
                stop.setEnabled(true);
                start.setEnabled(false);

            } else {
                //Add the pause offset time and add subtract the duration timer was stopped
                timer.setBase((SystemClock.elapsedRealtime() - timePausedOffset)+timerStoppedDuration);
                stop.setEnabled(false);
                start.setEnabled(true);
                timerStoppedMillis=System.currentTimeMillis();
            }

            add.setEnabled(true);
            selectedSport.getEditText().setText(chosenSport);
            selectedTechnique.getEditText().setText(chosenTechnique);
            techniqueText.setVisibility(View.VISIBLE);
            selectedTechnique.setVisibility(View.VISIBLE);
            editTextSportView.setVisibility(View.VISIBLE);
            selectedSport.setEnabled(false);
            selectedTechnique.setEnabled(false);
        }
        //If the training session is not started
        else {
            //Set the timer to 0
            timer.setBase(SystemClock.elapsedRealtime());
            trainingStatus=false;
        }

    }

    //When the fragment stops
    @Override
    public void onStop() {
        super.onStop();

        //If training session is over
        if(!trainingStatus){
            //Reset all values
            timePausedOffset =0;
            timeAbsent=0;
            running=false;
            timer.setBase(SystemClock.elapsedRealtime());
            timer.stop();
            appClosedCurrentTime=0;
            timerStoppedDuration=0;

        }
        else {
            //define the pause Offset
            timePausedOffset =SystemClock.elapsedRealtime()-timer.getBase();
        }


        //Define when the app was closed
        appClosedCurrentTime=System.currentTimeMillis();

        //Save values from fragment when the fragment stops/ is destroyed
        SharedPreferences pref= this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();

        editor.putBoolean("running",running);
        editor.putBoolean("trainingStatus",trainingStatus);
        editor.putString("chosenSport",chosenSport);
        editor.putString("chosenTechnique",chosenTechnique);
        editor.putLong("pauseOffset", timePausedOffset);
        editor.putLong("appClosedCurrentTime",appClosedCurrentTime);
        editor.putLong("timerStoppedMillis",timerStoppedMillis);
        editor.putLong("timerStoppedDuration",timerStoppedDuration);

        editor.apply();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_timerpage,container,false);
        getActivity().setTitle(R.string.timer);

        //Initializing variables
        timer= view.findViewById(R.id.timer);
        start= view.findViewById(R.id.startTimer);
        stop= view.findViewById(R.id.stopTimer);
        add= view.findViewById(R.id.addTimer);




        // Creating array to contain techniques
        final ArrayList<Technique> techniqueArrayList=new ArrayList<Technique>();

        //Building database
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        //If the database is empty, populate preset data
        if(db.sportDao().getAllSports().isEmpty()&&db.sportDao().getAllTechniques().isEmpty()){
            db.sportDao().addAllSports(Sport.populateData());
            db.sportDao().addAllTechniques(Technique.populateData());
        }

        //Adapter for sport dropdown menu
        final AutoCompleteTextView editTextSport= view.findViewById(R.id.filled_exposed_dropdown_sport_timer);
        final ArrayAdapter<Sport> adapterSport= new ArrayAdapter<Sport>(getContext(), android.R.layout.simple_list_item_1, db.sportDao().getAllSports());
        editTextSport.setAdapter(adapterSport);

        //Adapter for technique dropdown menu
        final AutoCompleteTextView editTextTechnique= view.findViewById(R.id.filled_exposed_dropdown_technique_timer);
        ArrayAdapter<Technique> adapterTechnique= new ArrayAdapter<Technique>(getContext(), android.R.layout.simple_list_item_1, techniqueArrayList);
        editTextTechnique.setAdapter(adapterTechnique);

        //Initializing variables
        techniqueText= view.findViewById(R.id.choseTechniqueTimer);
        selectedSport = view.findViewById(R.id.textInputLayoutTimer);
        selectedTechnique= view.findViewById(R.id.selectedTechniqueTimer);

        //Sport dropdown pressed on action
        editTextSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSportString=selectedSport.getEditText().getText().toString();

                for(int i=0;i<db.sportDao().getAllSports().size();i++) {
                    if (selectedSportString.equals(db.sportDao().getAllSports().get(i).sportName)) {

                        techniqueArrayList.clear();
                        techniqueArrayList.addAll(db.sportDao().getSportsWithTechniques().get(i).techniques);
                        techniqueText.setVisibility(View.VISIBLE);
                        editTextTechnique.setVisibility(View.VISIBLE);
                        selectedTechnique.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

        //Technique dropdown pressed action
        editTextTechnique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start.setEnabled(true);
                add.setEnabled(true);

            }
        });

        //Start timer button pressed
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the training session is started
                if(trainingStatus){
                    //If the timer is not running
                    if(!running){
                        //add pauseOffset to the timer and subtract the time timer was stopped
                        timer.setBase((SystemClock.elapsedRealtime() - timePausedOffset)+timerStoppedDuration);
                        timer.start();
                        running= true;
                        trainingStatus=true;
                        stop.setEnabled(true);
                        selectedSport.setEnabled(false);
                        selectedTechnique.setEnabled(false);

                    }
                }
                else {
                    if(!running){
                        //Only add pauseOffset to the timer.
                        timer.setBase((SystemClock.elapsedRealtime() - timePausedOffset));
                        timer.start();
                        running= true;
                        trainingStatus=true;
                        stop.setEnabled(true);
                        selectedSport.setEnabled(false);
                        selectedTechnique.setEnabled(false);

                    }
                }


                start.setEnabled(false);
                //Start service when the timer starts.
                Intent serviceIntent= new Intent(getContext(),TimerService.class);
                serviceIntent.putExtra("pauseOffset", timePausedOffset);
                getActivity().startService(serviceIntent);

                chosenSport= selectedSport.getEditText().getText().toString();
                chosenTechnique= selectedTechnique.getEditText().getText().toString();

            }
        });

        //When timer stops
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the timer is running
                if(running){
                    timer.stop();
                    //Set the timer paused offset
                    timePausedOffset =SystemClock.elapsedRealtime()-timer.getBase();
                    running=false;
                    trainingStatus=true;
                    timerStoppedDuration=0;
                    timerStoppedMillis=System.currentTimeMillis();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                }
                //Stop the service
                Intent serviceIntent= new Intent(getContext(),TimerService.class);
                getActivity().stopService(serviceIntent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get date and time for log
                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                String dateAndTime= simpleDateFormat.format(calendar.getTime());

                //Set timePauseOffset
                timePausedOffset =SystemClock.elapsedRealtime()-timer.getBase();
                //Calculate minutes and hours
                minutes= (int) (timePausedOffset /(1000*60))%60;
                hours= (int) ((timePausedOffset / (1000*60*60)) % 24);

                //If there is only seconds has gone on the timer
                if(minutes==0&&hours==0){
                    //Set the default to 1 minute and add the data to database.
                    minutes=1;
                    TrainingLog trainingLog=new TrainingLog(chosenSport,chosenTechnique,hours, minutes, dateAndTime);
                    db.sportDao().addLog(trainingLog);
                }
                //if there has gone more than 1 minute
                else{
                    //Add the data to database with the time passed.
                    TrainingLog trainingLog=new TrainingLog(chosenSport,chosenTechnique,hours, minutes, dateAndTime);
                    db.sportDao().addLog(trainingLog);
                }

                //Redirect the user to HomePage and show a confirmation message
                trainingStatus=false;
                Fragment fragment= new HomePageFragment();
                replaceFragment(fragment);

                Snackbar mySnackbar = Snackbar.make(v,"You have added your training "
                        ,
                        3000);
                mySnackbar.show();

                Intent serviceIntent= new Intent(getContext(),TimerService.class);
                getActivity().stopService(serviceIntent);
            }
        });




        return view;

    }

    //Method to replace fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    //Method for unselecting all items from bottom navigation
    public void unselectAllItems(){
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,true,false);
        for(int i=0;i<3;i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0,true,true);
    }
}
