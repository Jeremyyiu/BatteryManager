package com.example.jeremy.controller.controller;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.example.jeremy.controller.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class NetworkController {
    private Context context;
    private Activity activity;
    private ConnectivityManager connectivityManager;
    private WifiManager wifiManager;


    private final int HOTSPOT_ENABLED = 13;

    public NetworkController(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public void initNetworkItems() {
        initNetworkSwitch();
        initNetworkItemText();
    }

    private void initNetworkSwitch() {
        if (isWifiConnected()) {
            SwitchCompat wifiSwitch = activity.findViewById(R.id.wifiSwitch);
            wifiSwitch.setChecked(true);
        }

        if (isMobileNetworkConnected()) {
            SwitchCompat mobileDataSwitch = activity.findViewById(R.id.mobileDataSwitch);
            mobileDataSwitch.setChecked(true);
        }

        if (isAirplaneModeOn(context)) {
            SwitchCompat flightModeSwitch = activity.findViewById(R.id.flightModeSwitch);
            flightModeSwitch.setChecked(true);
        }

        //TODO: hotspot
    }

    private void initNetworkItemText() {
        final TextView wifiText = (TextView) activity.findViewById(R.id.wifiText);
        wifiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
                activity.startActivity(wifi);
            }
        });

        final TextView mobileDataText = (TextView) activity.findViewById(R.id.mobile_data_text);
        mobileDataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobileData = new Intent();
                mobileData.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                activity.startActivity(mobileData);
            }
        });

        final TextView flightModeText = (TextView) activity.findViewById(R.id.flightModeText);
        flightModeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plane = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                activity.startActivity(plane);
            }
        });

        final TextView hotspotText = (TextView) activity.findViewById(R.id.hotspotText);
        hotspotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spot = new Intent();
                spot.setClassName("com.android.settings", "com.android.settings.TetherSettings");
                context.startActivity(spot);
            }
        });

        final TextView dataUsageText = (TextView) activity.findViewById(R.id.dataUsageText);
        dataUsageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dataUsage = new Intent();
                dataUsage.setComponent(new ComponentName(
                        "com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"
                ));
                activity.startActivity(dataUsage);
            }
        });
    }
    //https://stackoverflow.com/questions/18735370/connectivitymanager-null-pointer
    public boolean isWifiConnected() {
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            return wifiInfo.isConnected();
        }
        return false;
    }

    public void toggleWiFi(boolean value){
        toggleWiFiTask task = new toggleWiFiTask();
        task.execute(value);
    }
    public class toggleWiFiTask extends AsyncTask<Boolean,Void,Boolean>
    {
        WifiManager wm;
        @Override
        protected Boolean doInBackground(Boolean... params) {
            wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return params[0];
        }
        @Override
        protected void onPostExecute(Boolean result) {
            wm.setWifiEnabled(result);
        }
    }

    public boolean isMobileNetworkConnected() {
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
    public boolean isAirplaneModeOn(Context context) {
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

    /**
    public boolean isHotspotConnected(Context context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = wifiManager.getDeclaredMethod("getWifiApState");
        method.setAccessible(true);
        int actualState  = (Integer) method.invoke(wifiManager, (Object[]) null);
    }

     **/
    public void hotspotTogglePermission() {
        Intent spot = new Intent();
        spot.setClassName("com.android.settings", "com.android.settings.TetherSettings");
        context.startActivity(spot);
    }

    public void airplaneModeTogglePermission() {
        Intent apModeToggle = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        context.startActivity(apModeToggle);
    }



    /** Todo: check if hotspot is active, let the user turn on hotspot and flight mode via intent. */

    /** TODO: Add flight mode listener */

    public class togglePlaneTask extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean enabled = isAirplaneModeOn(context);
            return !enabled;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            //Settings.System.putInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON,result?1:0);
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON,result?1:0);
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state",result);
            context.sendBroadcast(intent);
        }
    }

    /**
     * TODO: Mobile hotspot, grps - type of data connection
     **/
}
