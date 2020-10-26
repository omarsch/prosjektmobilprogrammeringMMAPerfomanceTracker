package com.example.mmaperfomancetracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton extendedFloatingActionButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomePageFragment()).commit();

        extendedFloatingActionButton= findViewById(R.id.floating_action_button);

        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddTrainingFragment()).commit();
            }
        });

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
                        case R.id.nav_log:
                            selectedFragment= new LogPageFragment();
                            break;
                        case R.id.nav_stats:
                            selectedFragment= new StatsPageFragment();
                            break;
                        case R.id.nav_notif:
                            selectedFragment= new NotificationsPageFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    }
