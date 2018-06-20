package com.example.jeremy.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.jeremy.controller.controller.AudioController;
import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.NetworkController;

import static com.example.jeremy.controller.JnaBatteryManagerApplication.getAppContext;
import static com.example.jeremy.controller.JnaBatteryManagerApplication.getApplication;
import static com.example.jeremy.controller.geo.StartupBroadCastReceiver.TAG;


public class BatteryLevelReceiver extends BroadcastReceiver {

    boolean wifiTrigger = false;
    boolean bluetoothTrigger = false;
    boolean ringerTrigger = false;

    NetworkController networkController = NetworkController.getInstance(getAppContext());
    BluetoothController bluetoothController = BluetoothController.getInstance(getAppContext());
    AudioController audioController = AudioController.getInstance(getAppContext());

    SharedPreferences preferences =
            PreferenceManager.getDefaultSharedPreferences(getAppContext());

    public BatteryLevelReceiver() {
        ((JnaBatteryManagerApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Low Battery broadcast received.");


        wifiTrigger = preferences.getBoolean("wifiLowBatTriggerEnabled", false);
        bluetoothTrigger = preferences.getBoolean("bluetoothLowBatTriggerEnabled", false);
        ringerTrigger = preferences.getBoolean("silentLowBatTriggerEnabled", false);

        if (wifiTrigger) {
            networkController.toggleWiFi(false);
        }

        if (bluetoothTrigger) {
            bluetoothController.toggleBluetooth(false);
        }

        if (ringerTrigger) {
            audioController.setRingerToSilent();
        }
    }
}