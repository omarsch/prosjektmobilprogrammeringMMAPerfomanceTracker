package com.example.mmaperfomancetracker.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mmaperfomancetracker.AddTrainingFragment;
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
        long pauseOffset= intent.getLongExtra("pauseOffset",0);

        Fragment fragment= new TimerPageFragment();
        Intent notifIntent= new Intent(this, fragment.getClass());
        PendingIntent pendingIntent= PendingIntent.getActivity(this,
                0,notifIntent,0);

        RemoteViews chronometerView= new RemoteViews(getPackageName(),R.layout.notfication);
        chronometerView.setChronometer(R.id.timerNotification, SystemClock.elapsedRealtime() -
                pauseOffset,null,true);

        Notification notification= new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.timer_24px)
                .setContentIntent(pendingIntent)
                .setCustomContentView(chronometerView)
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
