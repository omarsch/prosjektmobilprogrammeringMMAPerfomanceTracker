package com.example.mmaperfomancetracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

public class TimerService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*Toast toast = Toast.makeText(this, "Service started", Toast.LENGTH_SHORT);
        toast.show();*/

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*Toast toast = Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT);
        toast.show();*/
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
