package com.elanic.pulkit.moviesearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pulkit on 26/3/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new GridViewFragment();
            case 1:
                return new SimpleListFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "GRID";
            case 1:
                return "LIST";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
