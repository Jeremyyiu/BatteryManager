package com.example.jeremy.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.provider.Settings;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class NetworkController {
    private Context context;
    private ConnectivityManager connectivityManager;

    private final int HOTSPOT_ENABLED = 13;

    //https://stackoverflow.com/questions/18735370/connectivitymanager-null-pointer
    private boolean isWifiConnected() {
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            return wifiInfo.isConnected();
        }
        return false;
    }

    private boolean isMobileNetworkConnected() {
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null) {
            return mobileNetworkInfo.isConnected();
        }
        return false;
    }

    /** https://stackoverflow.com/questions/4319212/how-can-one-detect-airplane-mode-on-android */
    /**
     * Flight mode still allows you to connect to wifi but not mobile networks
     */
    public static boolean isAirplaneModeOn(Context context) {
        if (VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            /* API 17 and above */
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            /* below */
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    /** https://stackoverflow.com/questions/45984345/how-to-turn-on-off-wifi-hotspot-programmatically-in-android-8-0-oreo?noredirect=1&lq=1 */
    /** https://stackoverflow.com/questions/46278186/how-to-turn-on-wifi-hotspot-programmatically-on-android-7-1-including-sharin  */
    /**
     * https://stackoverflow.com/questions/14680978/monitoring-the-hotspot-state-in-android?noredirect=1&lq=1
     */

    private void hotspotTogglePermission() {
        Intent spot = new Intent();
        spot.setClassName("com.android.settings", "com.android.settings.TetherSettings");
        context.startActivity(spot);
    }

    private void airplaneModeTogglePermission() {
        Intent apModeToggle = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        context.startActivity(apModeToggle);
    }

    /** Todo: check if hotspot is active, let the user turn on hotspot and flight mode via intent. */

    /** TODO: Add flight mode listener */

    /**
     * TODO: Mobile hotspot, grps - type of data connection
     **/
}
