package com.example.moviecatalogue4.notif;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalogue4.BuildConfig;
import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.model.ModelMovie;
import com.example.moviecatalogue4.view.activity.Detail;
import com.example.moviecatalogue4.view.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Reminder extends BroadcastReceiver {
    public static final String ALARM_ID = "type";

    public static final int ID_DAILY_REMINDER = 100;
    public static final int ID_DAILY_RELEASE = 101;

    public Reminder() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onReceive(Context context, Intent intent) {
        int alarm = intent.getIntExtra(ALARM_ID, ID_DAILY_RELEASE);
        if (alarm == ID_DAILY_RELEASE) {
            Date c = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(c);
            AsyncHttpClient client = new AsyncHttpClient();
            ArrayList<ModelMovie> listItem = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.TMDB_API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
            client.get(url, new JsonHttpResponseHandler() {
                @SuppressLint("ResourceType")
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray list = response.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject movie = list.getJSONObject(i);
                            ModelMovie movieItem = new ModelMovie(movie);
                            listItem.add(movieItem);
                            showAlarmNotification(context, context.getResources().getString(R.string.release__reminder_title), listItem.get(i).getTitle()+context.getResources().getString(R.string.release__reminder_message), alarm, movieItem);
                        }
                        if (list.length() == 0) {
                            showAlarmNotification(context, context.getResources().getString(R.string.release__reminder_title), context.getResources().getString(R.string.release__reminder_bad_message), alarm, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            showAlarmNotification(context, context.getResources().getString(R.string.daily_reminder_title), context.getResources().getString(R.string.daily_reminder_message), alarm, null);
        }
    }


    public void setAlarm(Context context, int alarm, int hour) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, Reminder.class);
        intent.putExtra(ALARM_ID, alarm);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, Reminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId, @Nullable ModelMovie movieId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_film)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        PendingIntent pendingIntent = getPendingIntent(context, notifId, movieId);

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    private PendingIntent getPendingIntent(Context context, int notificationId, ModelMovie movieId) {
        Intent intent;
        if (notificationId == ID_DAILY_REMINDER) {
            intent = new Intent(context, MainActivity.class);
        } else if (notificationId >= ID_DAILY_RELEASE) {
            intent = new Intent(context, Detail.class);
            intent.putExtra(Detail.DATA, movieId);
        } else {
            return null;
        }
        return PendingIntent.getActivity(context, notificationId, intent, 0);
    }
}
