package com.example.jeremy.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.DisplayController;
import com.example.jeremy.controller.controller.GPSController;
import com.example.jeremy.controller.controller.NetworkController;
import com.example.jeremy.controller.persistent.Storage;
import com.example.jeremy.controller.view.GeofenceFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends AppCompatActivity {

    // UI
    private BottomNavigationView mBottomNav;
    private ViewPager mViewpager;
    private FrameLayout mMainFrame;

    @Inject
    Storage mStorage;
    //Fragments
    private BatteryFragment batteryFragment;
    private ControllerFragment controllerFragment;
    private GeofencingFragment geofencingFragment;

    private String fragmentTag = GeofenceFragment.TAG;
    private static final String FRAGMENTTAG = "current.fragment";

    private GPSController gpsController = null;
    private NetworkController networkController = null;
    private BluetoothController bluetoothController = null;
    private DisplayController displayController = null;

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

        mMainFrame = (FrameLayout) findViewById(R.id.main_container);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mContext = getApplicationContext();
        batteryFragment = new BatteryFragment();
        controllerFragment = new ControllerFragment();
        geofencingFragment = new GeofencingFragment();

        gpsController = GPSController.getInstance(mContext);
        networkController = NetworkController.getInstance(mContext);
        bluetoothController = BluetoothController.getInstance(mContext);
        displayController = DisplayController.getInstance(mContext, this);


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
                                setFragment(geofencingFragment);
                                updateToolbarText("Geofencing");
                                refresh();
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
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
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