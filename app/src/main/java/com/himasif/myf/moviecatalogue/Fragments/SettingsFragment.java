package com.himasif.myf.moviecatalogue.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.himasif.myf.moviecatalogue.BuildConfig;
import com.himasif.myf.moviecatalogue.DB.MovieHelper;
import com.himasif.myf.moviecatalogue.DailyNotificationReceiver;
import com.himasif.myf.moviecatalogue.MainActivity;
import com.himasif.myf.moviecatalogue.Models.AppPreference;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.himasif.myf.moviecatalogue.R;
import com.himasif.myf.moviecatalogue.ReleaseNotificationReceiver;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceChangeListener {

    private ArrayList<Movie> movieArrayList;
    private ReleaseNotificationReceiver releaseNotificationReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Daily Notification
        SwitchPreference dailyReminderSwitch = (SwitchPreference) findPreference(getResources().getString(R.string.pref_switch_daily_reminder));
        dailyReminderSwitch.setOnPreferenceChangeListener(this);
//        dailyReminderSwitch.setOnPreferenceChangeListener(new android.support.v7.preference.Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(android.support.v7.preference.Preference preference, Object newValue) {
//                SharedPreferences dailyPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//                boolean isActive = !dailyPreferences.getBoolean(getString(R.string.pref_switch_daily_reminder), true);
//                DailyNotificationReceiver dailyNotificationReceiver = new DailyNotificationReceiver();
//
//                if(isActive){
//                    dailyNotificationReceiver.setRepeatingAlarm(getContext());
//                } else {
//                    dailyNotificationReceiver.cancelAlarm(getContext());
//                }
//                Log.d(TAG, "onPreferenceChange: Status : " + isActive);
//                return true;
//            }
//        });

        // Release Notification
        SwitchPreference releaseReminderSwitch = (SwitchPreference) findPreference(getResources().getString(R.string.pref_switch_release_reminder));
        releaseReminderSwitch.setOnPreferenceChangeListener(this);

        // Change Language
        android.support.v7.preference.Preference changeLangBtn = findPreference(getString(R.string.pref_btn_change_lang));
        changeLangBtn.setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceChange(android.support.v7.preference.Preference preference, Object newValue) {
        String pref = preference.getKey();
        boolean isActive = (boolean) newValue;
        Context context = getContext();
        AppPreference appPreference = new AppPreference(context);

        if(pref.equals(getResources().getString(R.string.pref_switch_daily_reminder))){
//            DailyNotificationReceiver dailyNotificationReceiver = new DailyNotificationReceiver();
            if(isActive){
                appPreference.setDailyNotification(true);
//                dailyNotificationReceiver.setRepeatingAlarm(getContext());
            } else {
                appPreference.setDailyNotification(false);
//                dailyNotificationReceiver.cancelAlarm(getContext());
            }
        } else{
            releaseNotificationReceiver = new ReleaseNotificationReceiver();
            if(isActive){
                appPreference.setReleaseyNotification(true);
//                getMoviesNowPlaying();
//                releaseNotificationReceiver.setRepeatingAlarm(getContext(), movieArrayList);
            } else {
                appPreference.setReleaseyNotification(false);
//                releaseNotificationReceiver.cancelAlarm(getContext());
            }
        }
        Log.d(TAG, "onPreferenceChange: AppPreference : D " + appPreference.getDailyNotification());
        Log.d(TAG, "onPreferenceChange: AppPreference : R " + appPreference.getReleaseNotification());
        return true;
    }

    private void getMoviesNowPlaying(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = String.format(getResources().getString(R.string.now_playing_url), BuildConfig.API_KEY);
        Log.d(TAG, "getMoviesNowPlaying: res : url : " + url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray list = jsonObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieResponse = list.getJSONObject(i);
                        Movie movie = new Movie(movieResponse);
                        releaseNotificationReceiver.setAlarm(getContext(), movie);
//                        movieArrayList.add(movie);
                    }
                } catch (Exception e){
                    Log.e(TAG, "onSuccess: " + e);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
}
