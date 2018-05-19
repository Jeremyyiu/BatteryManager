package com.example.jeremy.controller.utils;

public class Constants {

    public static String LOG = "com.example.jeremy.controller";
    public static String API_ORIGIN = android.os.Build.MODEL;

    public enum TriggerType {
        ARRIVAL,
        DEPARTURE
    };

    public enum HttpMethod {
        POST,
        GET;
    };

    public static final int NORMAL_MODE = 0;
    public static final int VIBRATE_MODE = 1;
    public static final int SILENT_MODE = 2;
}
