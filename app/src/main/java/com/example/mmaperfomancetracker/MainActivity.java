package com.example.mmaperfomancetracker;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mmaperfomancetracker.db.SportDatabase;
import com.example.mmaperfomancetracker.db.tables.Sport;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton extendedFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


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
                        new AddTrainingFragment()).addToBackStack(null).commit();
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
                        case R.id.nav_notif:
                            selectedFragment= new NotificationsPageFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).addToBackStack(null).commit();
                    return true;
                }
            };

    }
