package com.example.mmaperfomancetracker;


import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {



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
                            selectedFragment).addToBackStack(null).commit();
                    return true;
                }
            };

    }
