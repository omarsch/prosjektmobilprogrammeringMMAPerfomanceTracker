package com.example.mmaperfomancetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.mmaperfomancetracker.db.Sport;
import com.example.mmaperfomancetracker.db.SportDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddTrainingFragment extends Fragment {


    com.google.android.material.textview.MaterialTextView techniqueText;
    com.google.android.material.textview.MaterialTextView durationText;
    com.google.android.material.textview.MaterialTextView showAddedTraining;
    com.google.android.material.textfield.TextInputLayout selectedSport;
    com.google.android.material.textfield.TextInputLayout selectedTechnique;
    com.google.android.material.textfield.TextInputLayout hours;
    com.google.android.material.textfield.TextInputLayout minutes;


    Button addTrainingBtn;

    private static final List<String> KICKBOXING = new ArrayList<String>();
    private static final List<String> BOXING = new ArrayList<String>();
    private static final List<String> GRAPPLING = new ArrayList<String>();
    private static final List<String> BRYTING = new ArrayList<String>();
    private static final List<String> selectedSportList=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_addtraining, container, false);
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, "sportdatabase").allowMainThreadQueries().build();



        db.sportDao().getAllSports().clear();

        KICKBOXING.clear();
        BOXING.clear();
        GRAPPLING.clear();
        BRYTING.clear();

        Collections.addAll(KICKBOXING,"k1","k2","k3","k4");
        Collections.addAll(BOXING,"b1","b2","b3","b4");
        Collections.addAll(GRAPPLING,"g1","g2","g3","g4");
        Collections.addAll(BRYTING,"br1","br2","br3","br4");



        final AutoCompleteTextView editTextSport= view.findViewById(R.id.filled_exposed_dropdown_sport);
        final ArrayAdapter<String> adapterSport= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, db.sportDao().getAllSports());
        editTextSport.setAdapter(adapterSport);


        final AutoCompleteTextView editTextTechnique= view.findViewById(R.id.filled_exposed_dropdown_technique);
        ArrayAdapter<String> adapterTechnique= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, selectedSportList);
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


                if (selectedSportString.equals("Kickboxing")){
                    selectedSportList.clear();
                    selectedSportList.addAll(KICKBOXING);
                    techniqueText.setVisibility(View.VISIBLE);
                    editTextTechnique.setVisibility(View.VISIBLE);
                    selectedTechnique.setVisibility(View.VISIBLE);

                }
                else if(selectedSportString.equals("Boxing")){
                    selectedSportList.clear();
                    selectedSportList.addAll(BOXING);
                    techniqueText.setVisibility(View.VISIBLE);
                    editTextTechnique.setVisibility(View.VISIBLE);
                    selectedTechnique.setVisibility(View.VISIBLE);
                }
                else if(selectedSportString.equals("Grappling")){
                    selectedSportList.clear();
                    selectedSportList.addAll(GRAPPLING);
                    techniqueText.setVisibility(View.VISIBLE);
                    editTextTechnique.setVisibility(View.VISIBLE);
                    selectedTechnique.setVisibility(View.VISIBLE);
                }
                else if(selectedSportString.equals("Bryting")){
                    selectedSportList.clear();
                    selectedSportList.addAll(BRYTING);
                    techniqueText.setVisibility(View.VISIBLE);
                    editTextTechnique.setVisibility(View.VISIBLE);
                    selectedTechnique.setVisibility(View.VISIBLE);
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
                    showAddedTraining.setText("Du har lagt til Sport:"+ selectedSportString+" Teknikk: "+
                            selectedTechnique.getEditText().getText().toString()+
                            " Tid: "+hours.getEditText().getText().toString()+" timer og "+
                            minutes.getEditText().getText()+" minutter " + db.sportDao().getAllSportsId().toString());
                }



            }
        });

        return view;
    }



}
