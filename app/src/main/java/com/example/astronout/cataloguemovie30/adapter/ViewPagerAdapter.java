package com.example.astronout.cataloguemovie30.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.astronout.cataloguemovie30.FavoriteFragment;
import com.example.astronout.cataloguemovie30.NowPlayingFragment;
import com.example.astronout.cataloguemovie30.UpcomingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mNoOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;

    }
}