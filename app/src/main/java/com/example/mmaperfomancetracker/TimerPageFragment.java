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
import androidx.room.Room;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.services.TimerService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TimerPageFragment extends Fragment{

    private Chronometer timer;
    private boolean running=false;
    private long pauseOffset;
    private int timeAdded;
    private String chosenSport;
    private String chosenTechnique;
    private com.google.android.material.button.MaterialButton start,stop,add;
    private com.google.android.material.textview.MaterialTextView techniqueText;
    private com.google.android.material.textfield.TextInputLayout selectedSport, selectedTechnique;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_timerpage,container,false);
        getActivity().setTitle(R.string.timer);

        timer= view.findViewById(R.id.timer);
        start= view.findViewById(R.id.startTimer);
        stop= view.findViewById(R.id.stopTimer);
        add= view.findViewById(R.id.addTimer);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                    timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    timer.start();
                    running= true;
                    stop.setEnabled(true);
                }


                Intent serviceIntent= new Intent(getContext(),TimerService.class);
                serviceIntent.putExtra("pauseOffset", pauseOffset);
                getActivity().startService(serviceIntent);

                chosenSport= selectedSport.getEditText().getText().toString();
                chosenTechnique= selectedTechnique.getEditText().getText().toString();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    timer.stop();
                    pauseOffset=SystemClock.elapsedRealtime()-timer.getBase();
                    running=false;
                }

                Intent serviceIntent= new Intent(getContext(),TimerService.class);
                getActivity().stopService(serviceIntent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        final ArrayList<Technique> techniqueArrayList=new ArrayList<Technique>();

        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        if(db.sportDao().getAllSports().isEmpty()&&db.sportDao().getAllTechniques().isEmpty()){
            db.sportDao().addAllSports(Sport.populateData());
            db.sportDao().addAllTechniques(Technique.populateData());
        }


        final AutoCompleteTextView editTextSport= view.findViewById(R.id.filled_exposed_dropdown_sport_timer);
        final ArrayAdapter<Sport> adapterSport= new ArrayAdapter<Sport>(getContext(), android.R.layout.simple_list_item_1, db.sportDao().getAllSports());
        editTextSport.setAdapter(adapterSport);


        final AutoCompleteTextView editTextTechnique= view.findViewById(R.id.filled_exposed_dropdown_technique_timer);
        ArrayAdapter<Technique> adapterTechnique= new ArrayAdapter<Technique>(getContext(), android.R.layout.simple_list_item_1, techniqueArrayList);
        editTextTechnique.setAdapter(adapterTechnique);


        techniqueText= view.findViewById(R.id.choseTechniqueTimer);
        selectedSport = view.findViewById(R.id.textInputLayoutTimer);
        selectedTechnique= view.findViewById(R.id.selectedTechniqueTimer);


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

        editTextTechnique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start.setEnabled(true);
                add.setEnabled(true);

            }
        });




        return view;

    }


}
