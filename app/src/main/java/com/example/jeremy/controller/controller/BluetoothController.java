package com.example.jeremy.controller.controller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class BluetoothController {
    private BluetoothAdapter btAdapter;
    private Context context;

    private static BluetoothController mInstance;

    protected BluetoothController(Context context) {
        this.context = context;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BluetoothController(context);
        }
        return mInstance;
    }

    public boolean isBluetoothOn() {
        int status = btAdapter.getState();
        return status == btAdapter.STATE_ON;
        //else if (status == btAdapter.STATE_OFF) return 0
        //returns -1, as it is either STATE_TURNING_ON or STATE_TURNING_OFF
    }

    public void bluetoothSettings() {
        Intent bluetooth = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(bluetooth);
    }

    /**
     * toggleBluetooth: switch the status of bluetooth (External port)
     * toggleToothTask: switch the status of bluetooth in another thread (Main implements)
     */
    public void toggleBluetooth(boolean value) {
        toggleToothTask task = new toggleToothTask();
        task.execute(value);
    }

    public class toggleToothTask extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                btAdapter.enable();
            } else {
                btAdapter.disable();
            }
        }
    }
}
