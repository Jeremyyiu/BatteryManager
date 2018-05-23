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
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class HomeActivity  extends BaseActivity implements GeofenceFragment.OnFragmentInteractionListener,
        android.app.LoaderManager.LoaderCallbacks<Cursor>  {

    // UI
    private BottomNavigationView mBottomNav;
    private ViewPager mViewpager;
    private FrameLayout mMainFrame;


    /**
    @BindView(R.id.container)
    FrameLayout mContentFrame;
**/

    @BindView(R.id.add_geofence)
    FloatingActionButton mFabButton;

    /**
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;
     **/

    @Inject
    Storage mStorage;
    //Fragments
    private BatteryFragment batteryFragment;
    private ControllerFragment controllerFragment;
    private GraphsFragment graphsFragment;
    private GeofenceFragment mGeofenceFragment = null;

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

        mMainFrame = (FrameLayout) findViewById(R.id.main_container);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mContext = getApplicationContext();
        batteryFragment = new BatteryFragment();
        controllerFragment = new ControllerFragment();
        graphsFragment = new GraphsFragment();

        //Sets the initial fragment upon startup.
        //setFragment(batteryFragment);
        //updateToolbarText("Battery");

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
                                //setFragment(mGeofenceFragment);
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

    @Override public void onBackPressed() {
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

        //setContentView(R.layout.activity_home);
        updateToolbarText("Geofencing");
        FragmentManager fragman = getFragmentManager();
        switch (fragmentTag) {
            case GeofenceFragment.TAG: {
                android.app.Fragment f = fragman.getFragment(new Bundle(), GeofenceFragment.TAG);
                if (mGeofenceFragment == null)
                    mGeofenceFragment = f != null ? (GeofenceFragment) f : GeofenceFragment.newInstance("str1", "str2");
                fragman.beginTransaction().replace(R.id.main_container, mGeofenceFragment, GeofenceFragment.TAG).commit();
                if (Geofences.ITEMS.size() == 0) {
                    load();
                }
                break;
            }
        }
    }

    public void load() {
        if (getLoaderManager().getLoader(0) == null)
            getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public void onFragmentInteraction(String id) {

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
    protected int getLayoutResourceId() {
        return R.layout.activity_geofences;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected int getMenuResourceId() {
        return 0;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.add_geofence)
    public void addGeofenceClick() {
        createGeofence();
    }

    private void createGeofence() {
        Intent addEditGeofencesIntent = new Intent(HomeActivity.this, AddEditGeofenceActivity.class);
        HomeActivity.this.startActivity(addEditGeofencesIntent);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse("content://" + "com.example.jeremy.controller" + "/geofences");
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mGeofenceFragment.geofences.clear();
        ArrayList<Geofences.Geofence> items = new ArrayList<>();
        while (data.moveToNext()) {
            Geofences.Geofence item = GeofenceProvider.fromCursor(data);
            mGeofenceFragment.geofences.addItem(item);
            items.add(item);
        }
        mGeofenceFragment.refresh();

        updateGeofencingService(items);
    }

    private void updateGeofencingService(ArrayList<Geofences.Geofence> items) {
        Intent geofencingService = new Intent(this, LocativeService.class);
        geofencingService.putExtra(LocativeService.EXTRA_ACTION, LocativeService.Action.ADD);
        geofencingService.putExtra(LocativeService.EXTRA_GEOFENCE, items);
        this.startService(geofencingService);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    public SharedPreferences getPrefs() {
        return this.getPreferences(MODE_PRIVATE);
    }

    /**
     private LocativeApplication getApp() {
     return (LocativeApplication) getApplication();
     }
     **/

    public void onGeofenceImportSelection(final Geofences.Geofence fence) {
        if (!mStorage.fenceExistsWithCustomId(fence)) {
            insertGeofence(fence);
            return;
        }

        new AlertDialog.Builder(this)
                .setMessage("A Geofence with the same custom ID already exists. Would you like to overwrite the existing Geofence?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insertGeofence(fence);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void insertGeofence(final Geofences.Geofence fence) {
        final Activity self = this;
        final ProgressDialog dialog = Dialog.getIndeterminateProgressDialog(this, "Importing Geofence…");
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Address address = new LocativeGeocoder().getFromLatLong(fence.latitude, fence.longitude, self);
                if (address != null) {
                    fence.name = address.getAddressLine(0);
                }
                mStorage.insertOrUpdateFence(fence);
                final FragmentManager fragmentManager = getFragmentManager();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                        Geofences.ITEMS.add(fence);
                        transaction.replace(R.id.main_container, mGeofenceFragment, GeofenceFragment.TAG).commit();
                        setTitle("Geofences1");
                        mFabButton.show();
                    }
                });
            }
        }).run();

    }

}