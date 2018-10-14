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

import java.util.Calendar;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Dul mu'in on 14/10/2018.
 */

public class DailyNotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 911;

    public DailyNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = context.getString(R.string.app_name);
        String text = context.getString(R.string.daily_reminder_text);
        showAlarmNotification(context, title, text, NOTIFICATION_ID);
        Log.d(TAG, "onReceive : Notification : called");
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

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
        Log.d(TAG, "showAlarmNotification: Notification : called");
        notificationManager.notify(notifId, builder.build());
    }

    public void setRepeatingAlarm(Context context) {
        Log.d(TAG, "setRepeatingAlarm: Alarm Daily Setted");
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(context, DailyNotificationReceiver.class);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(Context context) {
        Log.d(TAG, "cancelAlarm: Alarm daily Canceled");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotificationReceiver.class);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
