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

    boolean wifiTrigger;
    boolean bluetoothTrigger;
    boolean ringerTrigger;

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

        checkWiFiTrigger();
        checkBluetoothTrigger();
        checkRingerTrigger();
    }

    private void checkWiFiTrigger() {
        wifiTrigger = preferences.getBoolean("wifiLowBatTriggerEnabled", false);

        if (wifiTrigger) {
            networkController.toggleWiFi(false);
        }
    }

    private void checkBluetoothTrigger() {
        bluetoothTrigger = preferences.getBoolean("bluetoothLowBatTriggerEnabled", false);

        if (bluetoothTrigger) {
            bluetoothController.toggleBluetooth(false);
        }
    }

    private void checkRingerTrigger() {
        ringerTrigger = preferences.getBoolean("silentLowBatTriggerEnabled", false);

        if (ringerTrigger) {
            audioController.setRingerToSilent();
        }
    }
}