package com.example.jeremy.controller;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class ControllerService extends Service {
    // String for change action of gps and hotspot
    final String GPS_CHANGE_ACTION = "android.location.PROVIDERS_CHANGED";
    final String HOT_CHANGE_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";

    // WIFI_AP STATE
    final int HOTSPOT_ENABLED = 13;
    final int HOTSPOT_DISABLED = 11;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Broadcast receiver to get multiple events and send it as a single broadcast.
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        private static final String tag = "Debug Info";

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent broadcast_intent = new Intent();
            int wifiState, bluetoothState;

            //if a broadcast was sent from the WiFi module
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d(tag, "Wifi Disabled");
                        broadcast_intent.putExtra("wifi_state", -1);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Log.d(tag, "Wifi Disconnecting");
                        broadcast_intent.putExtra("wifi_state", 0);
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d(tag, "Wifi Connected");
                        broadcast_intent.putExtra("wifi_state", 1);
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.d(tag, "Wifi Enabling");
                        broadcast_intent.putExtra("wifi_state", 0);
                        break;
                    default:
                        broadcast_intent.putExtra("wifi_state", 0);
                        break;
                }
            }
            // If a broadcast was sent from mobile data
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (NetworkInfo.State.CONNECTED == mMobile.getState()) {
                    Log.d(tag, "Mobile Data Connected");
                    broadcast_intent.putExtra("data_state", 1);
                } else {
                    Log.d(tag, "Mobile Data Disconnected");
                    broadcast_intent.putExtra("data_state", -1);
                }
            }
            // If a broadcast was sent from flight mode
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
                int modeIdx = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
                boolean isEnabled = (modeIdx == 1);
                if (isEnabled) {
                    Log.d(tag, "Flight Mode On");
                    broadcast_intent.putExtra("plane_state", 1);
                } else {
                    Log.d(tag, "Flight Mode Off");
                    broadcast_intent.putExtra("plane_state", -1);
                }
            }
            // If a broadcast was sent from the GPS module
            if (intent.getAction().matches(GPS_CHANGE_ACTION)) {
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS) {
                    Log.d(tag, "GPS On");
                    broadcast_intent.putExtra("gps_state", 1);
                } else {
                    Log.d(tag, "GPS Off");
                    broadcast_intent.putExtra("gps_state", -1);
                }
            }
            // If a broadcast was sent from the bluetooth module
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (bluetoothState) {
                    case BluetoothAdapter.STATE_ON:
                        Log.d(tag, "Bluetooth On");
                        broadcast_intent.putExtra("bluetooth_state", 1);
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(tag, "Bluetooth Off");
                        broadcast_intent.putExtra("bluetooth_state", -1);
                        break;
                    default:
                        broadcast_intent.putExtra("bluetooth_state", 0);
                        break;
                }
            }

            // If a broadcast was sent from the hotspot module
            if (intent.getAction().matches(HOT_CHANGE_ACTION)) {
                int state = intent.getIntExtra("wifi_state", 0);
                switch (state) {
                    case HOTSPOT_DISABLED:
                        broadcast_intent.putExtra("hotspot_state", -1);
                        break;
                    case HOTSPOT_ENABLED:
                        broadcast_intent.putExtra("hotspot_state", 1);
                        break;
                    default:
                        broadcast_intent.putExtra("hotspot_state", 0);
                        break;
                }
            }
            broadcast_intent.setAction("com.example.jeremy.controller.controllerservice");
            sendBroadcast(broadcast_intent);
        }
    };

    /**
     * Create filter and select the actions to be received and filters all other actions out.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        //Select actions to be received
        mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mFilter.addAction(GPS_CHANGE_ACTION);
        mFilter.addAction(HOT_CHANGE_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    /**
     * Unregisters the receiver
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
