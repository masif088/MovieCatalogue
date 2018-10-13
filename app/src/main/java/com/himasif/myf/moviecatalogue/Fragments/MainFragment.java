package com.himasif.myf.moviecatalogue.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himasif.myf.moviecatalogue.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    public static final String EXTRA_MOVIES_LIST = "extra_movies_list";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_container);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.main_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        //Fragment 1
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        mAdapter.addFragment(nowPlayingFragment, getResources().getString(R.string.now_playing));

        //Fragment 2
        UpComingFragment upComingFragment = new UpComingFragment();
        mAdapter.addFragment(upComingFragment, getResources().getString(R.string.upcoming));

        viewPager.setAdapter(mAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentsList = new ArrayList<>();
        private final ArrayList<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentsList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentsList.add(fragment);
            mFragmentTitleList.add(title);
        }


        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
