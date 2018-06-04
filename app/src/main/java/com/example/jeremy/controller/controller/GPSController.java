package com.example.jeremy.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class GPSController implements LocationListener {
    Context mContext;
    private LocationManager locationManager;

    private static GPSController mInstance;

    protected GPSController(Context context) {
        this.mContext = context;
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static GPSController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GPSController(context);
        }
        return mInstance;
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
