package com.example.jeremy.controller;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class BatteryService extends Service {

    private int plugged;
    private int status;
    private int health;
    private int level;
    private int scale;
    private boolean present;
    private String technology;
    private int temperature;
    private int voltage;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        private static final String tag = "Debug Info";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null) {
                return;
            }

            if (intent == null) {
                return;
            }

            Intent broadcast_intent = new Intent();

            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Log.d(tag, "Battery Changed");
                present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
                plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                status = intent.getExtras().getInt(BatteryManager.EXTRA_STATUS, 0);
                health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
                scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

                broadcast_intent.putExtra("present", present);
                broadcast_intent.putExtra("plugged", plugged);
                broadcast_intent.putExtra("status", status);
                broadcast_intent.putExtra("health", health);
                broadcast_intent.putExtra("technology", technology);
                broadcast_intent.putExtra("scale", scale);
                broadcast_intent.putExtra("level", level);
                broadcast_intent.putExtra("temperature", temperature);
                broadcast_intent.putExtra("voltage", voltage);
            }

            broadcast_intent.setAction("com.example.jeremy.controller.batteryservice");
            sendBroadcast(broadcast_intent);
        }
    };

    public String getHealth() {
        String batteryStatus = "";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                batteryStatus = "Cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                batteryStatus = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                batteryStatus = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                batteryStatus = "Overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                batteryStatus = "Unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                batteryStatus = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                batteryStatus = "Unspecified Failure";
                break;
            default:
                batteryStatus = "Error - Could not receive";
                break;
        }
        return batteryStatus;
    }

    public int getStatus() {
        return status;
    }

    public int getPlugged() {
        return plugged;
    }

    public int getLevel() {
        return level;
    }

    public boolean isPresent() {
        return present;
    }

    public String getTechnology() {
        return technology;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getVoltage() {
        return voltage;
    }

    /**
     * Create filter and select the actions to be received and filters all other actions out.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        //Select actions to be received
        mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
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
