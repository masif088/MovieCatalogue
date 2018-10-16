package com.himasif.myf.moviecatalogue;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.himasif.myf.moviecatalogue.Models.Movie;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Dul mu'in on 14/10/2018.
 */

public class ReleaseNotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 912;
    private int notifId = 0;
    private int delay = 0;

    public ReleaseNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int id = intent.getIntExtra("id", 0);
        showAlarmNotification(context, title, text, id);
        Log.d(TAG, "onReceive : Notification : called " + title);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                0);

        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmRingtone);
        Log.d(TAG, "Release : Notification : called");
        notificationManager.notify(notifId, builder.build());
    }

    public void setAlarm(Context context, Movie movie) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("text", "Is Released Now!!!");
        intent.putExtra("id", notifId);
//        movie.setDateRaw("2018-10-15");
        Log.d(TAG, "setAlarm: intent title "  + movie.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0);
        Log.d(TAG, "setAlarm: date : " + movie.getReleaseDate());
        String dates = movie.getDateRaw();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            String dateNow = simpleDateFormat.format(date);
            Log.d(TAG, "setAlarm: date : " + dateNow + " : " + dates);
            if(dateNow.equals(dates)){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0 + delay);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(calendar.getTimeInMillis() + delay);
                Log.d(TAG, "setAlarm: notifid setted with delay " + delay + " and notifid " + notifId + " at " + new SimpleDateFormat("hh:mm:ss").format(c.getTime()));
                notifId++;
                delay += 5;
            }
        } catch (Exception e) {
            Log.e(TAG, "setAlarm: notifid " + e );
        }
        Log.d(TAG, "setRepeatingAlarm: Movie : " + movie.getTitle());
    }

    public void cancelAlarm(Context context) {
        Log.d(TAG, "cancelAlarm: Alarm Release Canceled");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
