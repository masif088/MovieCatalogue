package com.himasif.myf.moviecatalogue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.himasif.myf.moviecatalogue.Fragments.FavouriteFragment;
import com.himasif.myf.moviecatalogue.Fragments.MainFragment;
import com.himasif.myf.moviecatalogue.Fragments.SearchFragment;
import com.himasif.myf.moviecatalogue.Models.AppPreference;
import com.himasif.myf.moviecatalogue.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mainFragment;
    private AppPreference preference;
    private DailyNotificationReceiver dailyNotificationReceiver;
    private ReleaseNotificationReceiver releaseNotificationReceiver;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainFragment = new MainFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.d("Activity", "onCreate: " + (savedInstanceState != null));
        if(savedInstanceState == null){
            fragmentTransaction.replace(R.id.fragment_container, mainFragment);
        }
        fragmentTransaction.commit();
        preference = new AppPreference(this);
        notificationSettings();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void notificationSettings(){
        // Daily
        dailyNotificationReceiver = new DailyNotificationReceiver();
        Log.d(TAG, "notificationSettings: AppPreference Main : D " + preference.getDailyNotification());
        Log.d(TAG, "notificationSettings: AppPreference Main : D " + preference.getReleaseNotification());
        if(preference.getDailyNotification()){
            dailyNotificationReceiver.setRepeatingAlarm(this);
        } else {
            dailyNotificationReceiver.cancelAlarm(this);
        }

        // Release
        releaseNotificationReceiver = new ReleaseNotificationReceiver();
        if(preference.getReleaseNotification()){
            getMoviesNowPlaying(this);
        } else {
            releaseNotificationReceiver.cancelAlarm(this);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_home) {
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.fragment_container, mainFragment);
        } else if (id == R.id.nav_fav) {
            FavouriteFragment favouriteFragment = new FavouriteFragment();
            fragmentTransaction.replace(R.id.fragment_container, favouriteFragment);
        } else if (id == R.id.nav_search) {
            SearchFragment searchFragment = new SearchFragment();
            fragmentTransaction.replace(R.id.fragment_container, searchFragment);
        } else if (id == R.id.nav_setting){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

            startActivityForResult(intent, 100);
            return true;
        }

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMoviesNowPlaying(final Context context){
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
                        releaseNotificationReceiver.setAlarm(context, movie);
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
