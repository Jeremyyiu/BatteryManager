package com.example.jeremy.controller.utils;

public class Preferences {
    //Geofencing Settings
    public static String NOTIFICATION_SUCCESS = "notificationSuccess";
    public static String NOTIFICATION_FAIL = "notificationFailure";
    public static String NOTIFICATION_SOUND = "notificationSound";
    public static String NOTIFICATION_SHOW_ONLY_LATEST = "showOnlyLastNotification";
    public static String TRIGGER_THRESHOLD_ENABLED = "triggerThresholdEnabled";
    public static String TRIGGER_THRESHOLD_VALUE = "triggerThresholdValue";

    //Low battery Toggle triggers
    public static String WIFI_LOW_BAT_TRIGGER = "wifiLowBatTriggerEnabled";
    public static String BLUETOOTH_LOW_BAT_TRIGGER = "bluetoothLowBatTriggerEnabled";
    public static String SILENT_LOW_BAT_TRIGGER = "silentLowBatTriggerEnabled";

    //Geofence Toggles
    public static String WIFI_TOGGLE_GEOFENCE = "wifiGeofenceToggleEnabled";
    public static String BLUETOOTH_TOGGLE_GEOFENCE = "bluetoothGeofenceToggleEnabled";

    public static boolean LOW_BATTERY_TRIGGERED = false;

    // Default Value
    public static final int TRIGGER_THRESHOLD_VALUE_DEFAULT = 8000;
}