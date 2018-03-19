package com.example.jeremy.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class GPSController {
    Context context;
    private LocationManager locationManager;

    public void GPSController(Context context) {
        this.context = context;
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean gpsStatus() {
        boolean GPSstatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //https://stackoverflow.com/questions/16748300/locationmanager-isproviderenabledlocationmanager-network-provider-is-not-relia
        return GPSstatus;
    }

    /**
     * Because toggling the GPS/Location service cannot be done on the app, the user has to go into settings to change.
     */
    public void askGPSPermission() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
        //TODO: Maybe add an alternative if the phone is rooted
    }

}
