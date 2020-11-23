package com.example.mmaperfomancetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableRow;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.example.mmaperfomancetracker.db.tables.SportWithTechniques;
import com.example.mmaperfomancetracker.db.tables.Technique;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;
import com.google.android.material.snackbar.Snackbar;

import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AddTrainingFragment extends Fragment {


    private com.google.android.material.textview.MaterialTextView techniqueText,durationText,showAddedTraining;
    private com.google.android.material.textfield.TextInputLayout selectedSport, selectedTechnique,hours,minutes;

    private Button addTrainingBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_addtraining, container, false);
        getActivity().setTitle(R.string.add_button);


        final ArrayList<Technique> techniqueArrayList=new ArrayList<Technique>();

        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        if(db.sportDao().getAllSports().isEmpty()&&db.sportDao().getAllTechniques().isEmpty()){
            db.sportDao().addAllSports(Sport.populateData());
            db.sportDao().addAllTechniques(Technique.populateData());
        }


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
                String selectedTechniqueString=selectedTechnique.getEditText().getText().toString();

                if(hours.getEditText().getText().toString().isEmpty()){
                    hours.getEditText().setText("0");
                }
                else if(minutes.getEditText().getText().toString().isEmpty()){
                    minutes.getEditText().setText("0");
                }

                long selectedTimeHours= Integer.valueOf(hours.getEditText().getText().toString());
                long selectedTimeMinutes= Integer.valueOf(minutes.getEditText().getText().toString());

                ArrayList<String> techniqueArrayListString=new ArrayList<String>();

                for(int i=0;i<techniqueArrayList.size();i++){
                    techniqueArrayListString.add(techniqueArrayList.get(i).toString());
                }

                if(!techniqueArrayListString.contains(selectedTechniqueString)){
                    Snackbar mySnackbar = Snackbar.make(v,R.string.error_add_technique
                            ,
                            3000);
                    mySnackbar.show();
                }
                else if(hours.getEditText().getText().toString().equals("0") && minutes.getEditText().getText().toString().equals("0")){

                    Snackbar mySnackbar = Snackbar.make(v,R.string.error_add_time
                            ,
                            3000);
                    mySnackbar.show();
                }
                else {



                            Calendar calendar= Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                            String dateAndTime= simpleDateFormat.format(calendar.getTime());
                            TrainingLog trainingLog=new TrainingLog(selectedSportString,selectedTechniqueString,selectedTimeHours, selectedTimeMinutes, dateAndTime);

                            db.sportDao().addLog(trainingLog);

                    Snackbar mySnackbar = Snackbar.make(v,"You have added your training"
                            ,
                            3000);
                    mySnackbar.show();

                    hideKeyboard(view);

                    Fragment fragment= new HomePageFragment();
                    replaceFragment(fragment);

                }



            }
        });


        return view;
    }
    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
