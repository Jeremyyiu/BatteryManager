package com.example.jeremy.controller.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jeremy.controller.JnaBatteryManagerApplication;
import com.example.jeremy.controller.controller.AudioController;
import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.NetworkController;
import com.example.jeremy.controller.model.EventType;
import com.example.jeremy.controller.model.Geofences;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.utils.Constants;
import com.example.jeremy.controller.utils.Preferences;
import com.google.android.gms.location.Geofence;

import java.util.Random;

import javax.inject.Inject;

import static com.example.jeremy.controller.JnaBatteryManagerApplication.getAppContext;
import static com.example.jeremy.controller.JnaBatteryManagerApplication.getApplication;

//import com.example.jeremy.controller.LocativeApplication;

//import static com.example.jeremy.controller.LocativeApplication.getApplication;

public class TriggerManager {

    @Inject
    SharedPreferences mPreferences;

    @Inject
    NotificationManager mNotificationManager;

    private boolean wifiGeofenceToggle = false;
    private boolean bluetoothGeofenceToggle = false;
    private SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());

    NetworkController networkController = NetworkController.getInstance(getAppContext());
    BluetoothController bluetoothController = BluetoothController.getInstance(getAppContext());
    AudioController audioController = AudioController.getInstance(getAppContext());

    public TriggerManager() {
        ((JnaBatteryManagerApplication) getApplication()).inject(this);
    }

    public void triggerTransition(Geofences.Geofence fence, int transitionType) {
        String additionalNotification = "";
        bluetoothGeofenceToggle = preferences.getBoolean("bluetoothGeofenceToggleEnabled", false);
        wifiGeofenceToggle = preferences.getBoolean("wifiGeofenceToggleEnabled", false);
        // not global url is set, bail out and show classic notification
        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
            if (bluetoothGeofenceToggle) {
                bluetoothController.toggleBluetooth(true);
                additionalNotification += " , bluetooth turned on";
            }
            if (wifiGeofenceToggle) {
                networkController.toggleWiFi(true);
                additionalNotification += " , wifi turned on";
            }
        } else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
            if (bluetoothGeofenceToggle) {
                bluetoothController.toggleBluetooth(false);
                additionalNotification += ", bluetooth turned off";
            }
            if (wifiGeofenceToggle) {
                networkController.toggleWiFi(false);
                additionalNotification += ", wifi turned off";
            }
        }
        Log.d(Constants.LOG, "Presenting classic notification for " + fence.uuid);
        if (mPreferences.getBoolean(Preferences.NOTIFICATION_SUCCESS, false)) {
            mNotificationManager.showNotification(
                    fence.getRelevantId(),
                    new Random().nextInt(),
                    transitionType, additionalNotification
            );
        }
    }

    @Nullable
    private EventType getEventType(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return EventType.ENTER;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return EventType.EXIT;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return EventType.ENTER;
            default:
                return null;
        }

    }
}
