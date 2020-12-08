package com.example.mmaperfomancetracker;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras= getIntent().getExtras();
        if(extras !=null){
            //Getting information about witch page opened the main activity
            final boolean fromNotif= extras.getBoolean("notification");
            final boolean fromLog= extras.getBoolean("LOG");
            final boolean fromLogSelected= extras.getBoolean("LOG_SELECTED");
            final boolean fromLogSelectedBack= extras.getBoolean("LOG_SELECTED_BACK");
            //If the activity is opened from foreground notification
            if(fromNotif){
                //Change fragment container to TimerPageFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TimerPageFragment()).commit();
            }
            //If the activity is opened from logpage long clicked
            if(fromLog){
                //Change fragment container to SelectedLogPageFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SlectedLogPageFragment()).commit();
            }
            //If the activity is opened from selectedLogPage
            if(fromLogSelected){
                //Change fragment container to HomePageFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomePageFragment()).commit();
            }
            if(fromLogSelectedBack){
                //Change fragment container to LogPageFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogPageFragment()).commit();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initiating the bottomNavigation variable
       BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        //When the app starts change the fragment to HomePageFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomePageFragment()).commit();



    }


    //Method to change fragments if buttons are pressed on bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment= new HomePageFragment();
                            break;
                        case R.id.nav_notif:
                            selectedFragment= new NotificationsPageFragment();
                            break;
                        case R.id.nav_addTraining:
                            selectedFragment= new AddTrainingFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    }
