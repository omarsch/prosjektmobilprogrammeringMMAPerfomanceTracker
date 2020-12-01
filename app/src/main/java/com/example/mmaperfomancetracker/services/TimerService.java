package com.example.mmaperfomancetracker.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mmaperfomancetracker.MainActivity;
import com.example.mmaperfomancetracker.R;

import static com.example.mmaperfomancetracker.AppNotification.CHANNEL_ID;

public class TimerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long pauseOffset= intent.getLongExtra("pauseOffset",0);


        Intent notifIntent= new Intent(this, MainActivity.class);
        notifIntent.putExtra("notification",true);
        notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,
                100,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);

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
