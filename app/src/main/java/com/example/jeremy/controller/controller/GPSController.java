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
    private Activity activity;
    Context mContext;
    private LocationManager locationManager;

    public GPSController(Activity activity, Context context) {
        this.activity = activity;
        this.mContext = context;
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void initGpsItems() {
        initGpsSwitch();
        initGpsItemClick();
    }

    private void initGpsSwitch() {
        if (isGPSOn()) {
            SwitchCompat gpsSwitch = activity.findViewById(R.id.gpsSwitch);
            gpsSwitch.setChecked(true);
        }
    }

    private void initGpsItemClick() {
        final TextView gpsText = (TextView) activity.findViewById(R.id.gpsText);
        gpsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gprs = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                activity.startActivity(gprs);
            }
        });
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
