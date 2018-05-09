package com.example.jeremy.controller;

import android.os.BatteryManager;

public class DataReader {

    private int plugged;
    private int status;
    private int health;
    private int level;
    private int scale;
    private boolean present;
    private String technology;
    private int temperature;
    private int voltage;



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
}
