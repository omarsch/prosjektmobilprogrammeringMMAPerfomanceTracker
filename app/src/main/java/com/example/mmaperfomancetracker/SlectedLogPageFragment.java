package com.example.mmaperfomancetracker;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_selectedlog,container,false);

        final SportDatabase db = Room.databaseBuilder(getActivity(), SportDatabase.class, String.valueOf(R.string.database_name)).allowMainThreadQueries().build();


        date= view.findViewById(R.id.selectedLogDate);
        name= view.findViewById(R.id.selectedLogSportAndTechnique);
        duration= view.findViewById(R.id.selectedLogTime);
        delete= view.findViewById(R.id.deleteLog);

        final Bundle extras= getActivity().getIntent().getExtras();

        date.setText(extras.getString("DATE"));
        name.setText(extras.getString("TECHNIQUE"));
        hours=extras.getLong("HOURS");
        minutes=extras.getLong("MINUTES");

        totalDuration=(hours*60)+minutes;

        duration.setText(String.valueOf(totalDuration)+ " Minutes");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.sportDao().deleteThisFromTrainingLog(extras.getLong("LOG_ID"));
                Fragment fragment= new HomePageFragment();
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
