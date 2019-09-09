package com.example.moviecatalogue4.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.notif.Reminder;
import com.example.moviecatalogue4.utils.CustomOnItemSelectedListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSetting extends Fragment {
    public static String LAYOUT = "List Layout";
    private static String SHARED_PREF = "";
    private static String RELEASE_SETTINGS = "";
    private static boolean RELEASE = false;
    private static String DAILY_SETTINGS = "";
    private static boolean DAILY = false;
    @BindView(R.id.btn_setup)
    Button btnSetup;
    @BindView(R.id.sp_setup)
    Spinner spSetup;
    @BindView(R.id.daily)
    Switch daily;
    @BindView(R.id.release)
    Switch release;
    private Reminder reminder = new Reminder();

    public FragmentSetting() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        load();
        btnSetup.setOnClickListener(View -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spSetup.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @OnClick({R.id.daily, R.id.release})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.daily:
                DAILY = daily.isChecked();
                save();
                if (DAILY) {
                    reminder.setAlarm(getActivity(), Reminder.ID_DAILY_REMINDER, 7);
                } else {
                    reminder.cancelAlarm(getActivity(), Reminder.ID_DAILY_REMINDER);
                }
                break;
            case R.id.release:
                RELEASE = release.isChecked();
                save();
                if (RELEASE) {
                    reminder.setAlarm(getActivity(), Reminder.ID_DAILY_RELEASE, 8);
                } else {
                    reminder.cancelAlarm(getActivity(), Reminder.ID_DAILY_RELEASE);
                }
                break;
        }
    }

    private void save() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY_SETTINGS, DAILY);
        editor.putBoolean(RELEASE_SETTINGS, RELEASE);
        editor.apply();
    }

    private void load() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        daily.setChecked(sharedPreferences.getBoolean(DAILY_SETTINGS, false));
        release.setChecked(sharedPreferences.getBoolean(RELEASE_SETTINGS, false));
    }
}