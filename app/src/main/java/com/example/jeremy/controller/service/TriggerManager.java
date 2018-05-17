package com.example.jeremy.controller.service;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jeremy.controller.notification.NotificationManager;
import com.google.android.gms.location.Geofence;

import java.util.Random;

import javax.inject.Inject;

import com.example.jeremy.controller.LocativeApplication;
import com.example.jeremy.controller.model.EventType;
import com.example.jeremy.controller.model.Geofences;
import com.example.jeremy.controller.utils.Constants;
import com.example.jeremy.controller.utils.Preferences;

import static com.example.jeremy.controller.LocativeApplication.getActivity;

public class TriggerManager {

    @Inject
    SharedPreferences mPreferences;

    @Inject
    NotificationManager mNotificationManager;

    public TriggerManager() {
        ((LocativeApplication) getActivity()).inject(this);
    }

    public void triggerTransition(Geofences.Geofence fence, int transitionType) {
        // not global url is set, bail out and show classic notification
        Log.d(Constants.LOG, "Presenting classic notification for " + fence.uuid);
        if (mPreferences.getBoolean(Preferences.NOTIFICATION_SUCCESS, false)) {
            mNotificationManager.showNotification(
                    fence.getRelevantId(),
                    new Random().nextInt(),
                    transitionType
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
