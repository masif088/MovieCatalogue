package com.example.moviecatalogue4.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogue4.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {
    private Fragment fragment;
    private Bundle bundle;

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Objects.equals(Objects.requireNonNull(getArguments()).getString("TYPE"), "MOVIE")) {
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", "MOVIE");
            fragment = new FragmentList();
            fragment.setArguments(bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", "TV");
            fragment = new FragmentList();
            fragment.setArguments(bundle);
        }
        if (fragment != null)
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmenta, fragment).commit();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                assert getArguments() != null;
                switch (Objects.requireNonNull(getArguments().getString("TYPE"))) {
                    case "MOVIE":
                        bundle = new Bundle();
                        bundle.putString("TYPE", "MOVIESEARCH");
                        bundle.putString("URL", s);
                        fragment = new FragmentList();
                        fragment.setArguments(bundle);
                        break;
                    case "TV":
                        bundle = new Bundle();
                        bundle.putString("TYPE", "TVSEARCH");
                        bundle.putString("URL", s);
                        fragment = new FragmentList();
                        fragment.setArguments(bundle);
                        break;
                }
                if (fragment != null)
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmenta, fragment).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                assert getArguments() != null;
                switch (Objects.requireNonNull(getArguments().getString("TYPE"))) {
                    case "MOVIE":
                        bundle = new Bundle();
                        bundle.putString("TYPE", "MOVIESEARCH");
                        bundle.putString("URL", s);
                        fragment = new FragmentList();
                        fragment.setArguments(bundle);
                        break;
                    case "TV":
                        bundle = new Bundle();
                        bundle.putString("TYPE", "TVSEARCH");
                        bundle.putString("URL", s);
                        fragment = new FragmentList();
                        fragment.setArguments(bundle);
                        break;
                }
                if (fragment != null)
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmenta, fragment).commit();

                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            if (Objects.equals(Objects.requireNonNull(getArguments()).getString("TYPE"), "MOVIE")) {
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "MOVIE");
                fragment = new FragmentList();
                fragment.setArguments(bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "TV");
                fragment = new FragmentList();
                fragment.setArguments(bundle);
            }
            if (fragment != null)
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmenta, fragment).commit();

            return false;
        });

        return view;
    }

}
