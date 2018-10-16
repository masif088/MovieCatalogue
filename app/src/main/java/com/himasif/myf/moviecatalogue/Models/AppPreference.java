package com.himasif.myf.moviecatalogue.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.himasif.myf.moviecatalogue.R;

/**
 * Created by Dul mu'in on 17/10/2018.
 */

public class AppPreference {
    SharedPreferences preferences;
    Context context;

    public AppPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyNotification(Boolean input){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.pref_switch_daily_reminder);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getDailyNotification(){
        String key = context.getResources().getString(R.string.pref_switch_daily_reminder);
        return preferences.getBoolean(key, true);
    }

    public void setReleaseyNotification(Boolean input){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.pref_switch_release_reminder);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getReleaseNotification(){
        String key = context.getResources().getString(R.string.pref_switch_release_reminder);
        return preferences.getBoolean(key, true);
    }

}
