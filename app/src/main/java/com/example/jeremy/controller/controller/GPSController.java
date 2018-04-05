package com.example.jeremy.controller.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.example.jeremy.controller.R;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class GPSController {
    Context mContext;
    private LocationManager locationManager;

    public GPSController(Context context) {
        this.mContext = context;
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean isGPSOn() {
        boolean GPSstatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //https://stackoverflow.com/questions/16748300/locationmanager-isproviderenabledlocationmanager-network-provider-is-not-relia
        return GPSstatus;
    }

    /**
     * Because toggling the GPS/Location service cannot be done on the app, the user has to go into settings to change.
     */
    public void toggleGPS() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(intent);
        //TODO: Maybe add an alternative if the phone is rooted
    }

}
