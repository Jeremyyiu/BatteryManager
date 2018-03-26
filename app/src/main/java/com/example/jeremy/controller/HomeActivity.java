package com.example.jeremy.controller;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    // UI
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private ViewPager mViewpager;
    private int mSelectedItem;

    //Fragments
    private BatteryFragment batteryFragment;
    private ControllerFragment controllerFragment;
    private GraphsFragment graphsFragment;

    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme_TranslucentNavigation);
        setContentView(R.layout.activity_home);

        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_battery:
                                mViewpager.setCurrentItem(0);
                                break;
                            case R.id.menu_controller:
                                mViewpager.setCurrentItem(1);
                                break;
                            case R.id.menu_graphs:
                                mViewpager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mBottomNav.getMenu().getItem(0).setChecked(false);
                }
                mBottomNav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomNav.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         MenuItem selectedItem;
         if (savedInstanceState != null) {
         mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
         selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
         } else {
         selectedItem = mBottomNav.getMenu().getItem(0);
         }
         selectFragment(selectedItem);

         **/


        setupViewPager(mViewpager);
    }

    /**
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }
**/
    /**
    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_battery:
                frag = BatteryFragment.newInstance(getString(R.string.battery),
                        getColorFromRes(R.color.cardview_light_background));
                break;
            case R.id.menu_controller:
                frag = ControllerFragment.newInstance(getString(R.string.controller),
                        getColorFromRes(R.color.cardview_light_background));
                break;
            case R.id.menu_graphs:
                frag = GraphsFragment.newInstance(getString(R.string.graphs),
                        getColorFromRes(R.color.cardview_light_background));
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragContainer, frag, frag.getTag());
            ft.addToBackStack(null);
            ft.commit();
        }

    }
     **/

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        batteryFragment = new BatteryFragment();
        controllerFragment = new ControllerFragment();
        graphsFragment = new GraphsFragment();
        adapter.addFragment(batteryFragment);
        adapter.addFragment(controllerFragment);
        adapter.addFragment(graphsFragment);
        viewPager.setAdapter(adapter);
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }
}
