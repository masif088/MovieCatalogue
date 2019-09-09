package com.example.moviecatalogue4.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviecatalogue4.adapter.PageAdapter;
import com.example.moviecatalogue4.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentFavorite extends Fragment {
    public FragmentFavorite() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.favorite);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        PageAdapter pagerAdapter = new PageAdapter(getChildFragmentManager());

        FragmentList mov = new FragmentList();
        Bundle bundleMovie = new Bundle();
        bundleMovie.putString("TYPE", "MOVIEPAGER");
        pagerAdapter.addFragment(mov, getResources().getStringArray(R.array.view_pager)[0]);
        mov.setArguments(bundleMovie);

        FragmentList tv = new FragmentList();
        Bundle bundleTv = new Bundle();
        bundleTv.putString("TYPE", "TVPAGER");
        pagerAdapter.addFragment(tv, getResources().getStringArray(R.array.view_pager)[1]);
        tv.setArguments(bundleTv);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
    }
}