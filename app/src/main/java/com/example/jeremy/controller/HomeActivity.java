package com.example.jeremy.controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.jeremy.controller.geo.LocativeGeocoder;
import com.example.jeremy.controller.model.Geofences;
import com.example.jeremy.controller.persistent.GeofenceProvider;
import com.example.jeremy.controller.persistent.Storage;
import com.example.jeremy.controller.service.LocativeService;
import com.example.jeremy.controller.utils.Dialog;
import com.example.jeremy.controller.view.AddEditGeofenceActivity;
import com.example.jeremy.controller.view.BaseActivity;
import com.example.jeremy.controller.view.GeofenceFragment;
import com.example.jeremy.controller.view.GeofencesActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    // UI
    private BottomNavigationView mBottomNav;
    private ViewPager mViewpager;
    private FrameLayout mMainFrame;


    /**
     * @BindView(R.id.container) FrameLayout mContentFrame;
     **/

    @BindView(R.id.add_geofence)
    FloatingActionButton mFabButton;

    /**
     * @BindView(R.id.toolbar_actionbar) Toolbar mToolbar;
     **/

    @Inject
    Storage mStorage;
    //Fragments
    private BatteryFragment batteryFragment;
    private ControllerFragment controllerFragment;
    private GraphsFragment graphsFragment;
    //private GeofenceFragment mGeofenceFragment;

    private String fragmentTag = GeofenceFragment.TAG;
    private static final String FRAGMENTTAG = "current.fragment";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(FRAGMENTTAG)) {
                fragmentTag = savedInstanceState.getString(FRAGMENTTAG);
            }
        }

        super.onCreate(savedInstanceState);
        ((JnaBatteryManagerApplication) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_home);

        final Activity activity = this;

        mMainFrame = (FrameLayout) findViewById(R.id.main_container);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mContext = getApplicationContext();
        batteryFragment = new BatteryFragment();
        controllerFragment = new ControllerFragment();
        graphsFragment = new GraphsFragment();

        //Sets the initial fragment upon startup.
        setFragment(batteryFragment);
        updateToolbarText("Battery");

        setTheme(R.style.AppTheme_TranslucentNavigation);

        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_battery:
                                setFragment(batteryFragment);
                                updateToolbarText("Battery");
                                refresh();
                                return true;
                            case R.id.menu_controller:
                                setFragment(controllerFragment);
                                updateToolbarText("Controller");
                                refresh();
                                return true;
                            case R.id.menu_graphs:
                                updateToolbarText("Geofencing");
                                Intent i = new Intent(activity, GeofencesActivity.class);
                                startActivity(i);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    private void refresh() {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrolly);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, (findViewById(R.id.activity_main)).getTop());
            }
        });
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FRAGMENTTAG, fragmentTag);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setFragment(batteryFragment);
        updateToolbarText("Battery");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}