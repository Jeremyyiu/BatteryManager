package com.example.jeremy.controller.controller;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Jeremy on 14/03/2018.
 */

public class BluetoothController {
    private BluetoothAdapter btAdapter = null;

    public void BluetoothController() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public int checkBluetoothStatus() {
        int status = btAdapter.getState();
        if (status == btAdapter.STATE_ON) {
            return 1;
        } else if (status == btAdapter.STATE_OFF) {
            return 0;
        }
        //returns -1, as it is either STATE_TURNING_ON or STATE_TURNING_OFF
        return -1;
    }

    public void toggleBluetooth() {
        //toggle stuff
    }

    private void bluetoothDisable() {
        btAdapter.disable();
    }

    private void bluetoothEnable() {
        btAdapter.enable();
    }
}
