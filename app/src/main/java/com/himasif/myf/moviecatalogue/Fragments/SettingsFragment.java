package com.himasif.myf.moviecatalogue.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himasif.myf.moviecatalogue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();

        SwitchPreference dailyReminderSwitch = (SwitchPreference) findPreference(getResources().getString(R.string.pref_switch_daily_reminder));

        SwitchPreference releaseReminderSwitch = (SwitchPreference) findPreference(getResources().getString(R.string.pref_switch_release_reminder));

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
}
