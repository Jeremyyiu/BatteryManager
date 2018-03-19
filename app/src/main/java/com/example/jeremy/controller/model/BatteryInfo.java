package com.example.jeremy.controller.model;

/**
 * Created by liangze on 28/2/18.
 */

public class BatteryInfo {

    private int battLevel;
    private int battStatus;

    public BatteryInfo() {
    }

    public int getBattLevel() {
        return battLevel;
    }

    public void setBattLevel(int battLevel) {
        this.battLevel = battLevel;
    }

    public int getBattStatus() {
        return battStatus;
    }

    public void setBattStatus(int battStatus) {
        this.battStatus = battStatus;
    }

    @Override
    public String toString() {
        return "BatteryInfo{" +
                "battLevel=" + battLevel +
                ", battStatus=" + battStatus +
                '}';
    }
}
