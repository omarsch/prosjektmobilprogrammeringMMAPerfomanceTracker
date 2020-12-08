package com.example.mmaperfomancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigator;
import androidx.room.Room;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.TrainingLog;
import com.google.android.material.textview.MaterialTextView;

public class SlectedLogPageFragment extends Fragment {


    MaterialTextView date,name,duration;
    Button delete,back;
    long totalDuration;
    long hours;
    long minutes;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_selectedlog,container,false);
        getActivity().setTitle(R.string.logg_button);
        //Building database
        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();

        //Initializing variables
        date= view.findViewById(R.id.selectedLogDate);
        name= view.findViewById(R.id.selectedLogSportAndTechnique);
        duration= view.findViewById(R.id.selectedLogTime);
        delete= view.findViewById(R.id.deleteLog);
        back=view.findViewById(R.id.backToLog);

        final Bundle extras= getActivity().getIntent().getExtras();

        //Getting values from selected item from LogPageFragment
        date.setText(extras.getString("DATE"));
        name.setText(extras.getString("TECHNIQUE"));
        hours=extras.getLong("HOURS");
        minutes=extras.getLong("MINUTES");

        //Showing added data to the user
        totalDuration=(hours*60)+minutes;

        duration.setText(String.valueOf(totalDuration)+ " Minutes");

        //If user wants to delete the selected data from LogPage delete it
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.sportDao().deleteThisFromTrainingLog(extras.getLong("LOG_ID"));

                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("LOG_SELECTED",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);

            }
        });

        //If the user wants to go back to LogPage redirect here
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("LOG_SELECTED_BACK",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
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
}
