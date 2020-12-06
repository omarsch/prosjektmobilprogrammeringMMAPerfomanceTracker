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
            final boolean fromNotif= extras.getBoolean("notification");
            final boolean fromLog= extras.getBoolean("LOG");
            final boolean fromLogSelected= extras.getBoolean("LOG_SELECTED");
            if(fromNotif){

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TimerPageFragment()).commit();
            }
            if(fromLog){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SlectedLogPageFragment()).commit();
            }
            if(fromLogSelected){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomePageFragment()).commit();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomePageFragment()).commit();



    }



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
