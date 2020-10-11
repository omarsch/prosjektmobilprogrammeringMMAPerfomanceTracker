package com.example.mmaperfomancetracker;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;


public class HomePageFragment extends Fragment {

    com.google.android.material.card.MaterialCardView logCard, statsCard, notifCard;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        logCard= view.findViewById(R.id.loggCard);
        statsCard= view.findViewById(R.id.statsCard);
        notifCard= view.findViewById(R.id.notifCard);

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

                Fragment fragment= new NotificationsPageFragment();
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