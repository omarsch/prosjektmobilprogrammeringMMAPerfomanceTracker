package com.example.mmaperfomancetracker;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TimerPageFragment extends Fragment {

    private Chronometer timer;
    private boolean running;
    private long pauseOffset;
    private long timeAdded;
    private com.google.android.material.button.MaterialButton start,stop,pause;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_timerpage,container,false);
        getActivity().setTitle(R.string.timer);

        timer= view.findViewById(R.id.timer);
        start= view.findViewById(R.id.startTimer);
        stop= view.findViewById(R.id.stopTimer);
        pause= view.findViewById(R.id.pauseTimer);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(v);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer(v);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer(v);
            }
        });

        return view;

    }


    public void startTimer(View v){
        if(!running){
            timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            timer.start();
            running= true;
        }
    }

    public void pauseTimer(View v){
        if(running){
            timer.stop();
            pauseOffset=SystemClock.elapsedRealtime()-timer.getBase();
            running=false;
        }
    }


    public void stopTimer(View v){
        if(running){
            timer.stop();
            pauseOffset=SystemClock.elapsedRealtime()-timer.getBase();
            running=false;
        }

        timeAdded=pauseOffset/1000;
        Snackbar mySnackbar = Snackbar.make(v,"You have added "+timeAdded
                ,
                3000);
        mySnackbar.show();
    }
}
