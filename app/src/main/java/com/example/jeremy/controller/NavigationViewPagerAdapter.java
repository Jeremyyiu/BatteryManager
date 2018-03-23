package com.example.jeremy.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 *
 */
public class NavigationViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<NavigationFragment> fragments = new ArrayList<>();
    private NavigationFragment currentFragment;

    public NavigationViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(NavigationFragment.newInstance(0));
        fragments.add(NavigationFragment.newInstance(1));
        fragments.add(NavigationFragment.newInstance(2));
    }

    @Override
    public NavigationFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((NavigationFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Get the current fragment
     */
    public NavigationFragment getCurrentFragment() {
        return currentFragment;
    }
}