package com.example.mmaperfomancetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddTrainingFragment extends Fragment {


    com.google.android.material.textview.MaterialTextView techniqueText,durationText,showAddedTraining;
    com.google.android.material.textfield.TextInputLayout selectedSport, selectedTechnique,hours,minutes;

    Button addTrainingBtn;
    private static final ArrayList<Technique> techniqueArrayList=new ArrayList<Technique>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_addtraining, container, false);
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, "sportAndTechniqueDB").allowMainThreadQueries().build();



        final AutoCompleteTextView editTextSport= view.findViewById(R.id.filled_exposed_dropdown_sport);
        final ArrayAdapter<Sport> adapterSport= new ArrayAdapter<Sport>(getContext(), android.R.layout.simple_list_item_1, db.sportDao().getAllSports());
        editTextSport.setAdapter(adapterSport);


        final AutoCompleteTextView editTextTechnique= view.findViewById(R.id.filled_exposed_dropdown_technique);
        ArrayAdapter<Technique> adapterTechnique= new ArrayAdapter<Technique>(getContext(), android.R.layout.simple_list_item_1, techniqueArrayList);
        editTextTechnique.setAdapter(adapterTechnique);


        techniqueText= view.findViewById(R.id.choseTechnique);
        selectedSport = view.findViewById(R.id.textInputLayout);
        selectedTechnique= view.findViewById(R.id.selectedTechnique);

        durationText= view.findViewById(R.id.durationText);
        hours=view.findViewById(R.id.hours);
        minutes=view.findViewById(R.id.minutes);

        addTrainingBtn= view.findViewById(R.id.addTraining);

        showAddedTraining= view.findViewById(R.id.showAddedTraining);
        

        editTextSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSportString=selectedSport.getEditText().getText().toString();
                for(int i=0;i<2;i++) {
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

                durationText.setVisibility(View.VISIBLE);
                hours.setVisibility(View.VISIBLE);
                minutes.setVisibility(View.VISIBLE);
                addTrainingBtn.setVisibility(View.VISIBLE);


            }
        });

        addTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String selectedSportString=selectedSport.getEditText().getText().toString();


                if(hours.getEditText().getText().toString().isEmpty() && minutes.getEditText().getText().toString().isEmpty()){

                    Snackbar mySnackbar = Snackbar.make(v,"Du må legge til timer eller minutter"
                            ,
                            1000);
                    mySnackbar.show();
                }
                else {
                        showAddedTraining.setText(selectedSportString+" "+selectedTechnique.getEditText().getText().toString());
                }



            }
        });

        return view;
    }



}
