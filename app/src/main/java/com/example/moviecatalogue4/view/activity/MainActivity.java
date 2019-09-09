package com.example.moviecatalogue4.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogue4.R;
import com.example.moviecatalogue4.view.fragment.FragmentFavorite;
import com.example.moviecatalogue4.view.fragment.FragmentMain;
import com.example.moviecatalogue4.view.fragment.FragmentSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment movie;
    private Fragment tv;
    private Fragment setting;
    private Fragment favorite;
    private androidx.fragment.app.Fragment fragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.movie:
                    fragment = movie;
                    if (fragment == null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("TYPE", "MOVIE");
                        movie = new FragmentMain();
                        movie.setArguments(bundle);
                        fragment = movie;
                    }
                    break;
                case R.id.tv:
                    fragment = tv;
                    if (fragment == null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("TYPE", "TV");
                        tv = new FragmentMain();
                        tv.setArguments(bundle);
                        fragment = tv;
                    }
                    break;
                case R.id.favorite:
                    fragment = favorite;
                    if (fragment == null) {
                        favorite = new FragmentFavorite();
                        fragment = favorite;
                    }
                    break;
                case R.id.setting:
                    fragment = setting;
                    if (fragment == null) {
                        setting = new FragmentSetting();
                        fragment = setting;
                    }
                    break;
            }
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
            return fragment != null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(listener);

        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.movie);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (navigationView.getSelectedItemId() == R.id.favorite) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.seacrh_view, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null){
//            searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setQueryHint(getResources().getString(R.string.search_hint));
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FragmentFavorite()).commit();
//                    Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT).show();
//                    searchView.clearFocus();
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    return false;
//                }
//            });
//
//        }
//        return super.onCreateOptionsMenu(menu);
//    }
}
