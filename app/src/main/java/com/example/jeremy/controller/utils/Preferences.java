package com.example.jeremy.controller.utils;

public class Preferences {
    public static String NOTIFICATION_SUCCESS = "notificationSuccess";
    public static String NOTIFICATION_FAIL = "notificationFailure";
    public static String NOTIFICATION_SOUND = "notificationSound";
    public static String NOTIFICATION_SHOW_ONLY_LATEST = "showOnlyLastNotification";
    public static String SESSION_ID = "sessionId";
    public static String ACCOUNT = "account";
    public static String TRIGGER_THRESHOLD_ENABLED = "triggerThresholdEnabled";
    public static String TRIGGER_THRESHOLD_VALUE = "triggerThresholdValue";
    public static boolean WIFI_LOW_BATTERY_TRIGGER = false;
    public static boolean BLUETOOTH_LOW_BATTERY_TRIGGER = false;
    public static boolean SILENT_LOW_BATTERY_TRIGGER = false;


    // Default Value
    public static final int TRIGGER_THRESHOLD_VALUE_DEFAULT = 8000;
}