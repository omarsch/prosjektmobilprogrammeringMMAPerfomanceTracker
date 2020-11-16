package com.example.mmaperfomancetracker.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mmaperfomancetracker.MainActivity;
import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.TimerPageFragment;
import com.google.android.material.snackbar.Snackbar;

import static com.example.mmaperfomancetracker.App.CHANNEL_ID;

public class TimerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input= intent.getStringExtra("inputExtra");

        Intent notifIntent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,
                0,notifIntent,0);

        Notification notification= new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.timer_24px)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
