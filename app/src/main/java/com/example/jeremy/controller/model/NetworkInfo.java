package com.example.jeremy.controller.model;

/**
 * Created by liangze on 1/3/18.
 */

public class NetworkInfo {
    // 0 off, 1 on
    private int wifiState;
    private int mobileDataState;
    private int bluetoothState;
    private int gpsState;

    public NetworkInfo() {
    }

    public int getWifiState() {
        return wifiState;
    }

    public void setWifiState(int wifiState) {
        this.wifiState = wifiState;
    }

    public int getMobileDataState() {
        return mobileDataState;
    }

    public void setMobileDataState(int mobileDataState) {
        this.mobileDataState = mobileDataState;
    }

    public int getBluetoothState() {
        return bluetoothState;
    }

    public void setBluetoothState(int bluetoothState) {
        this.bluetoothState = bluetoothState;
    }

    public int getGpsState() {
        return gpsState;
    }

    public void setGpsState(int gpsState) {
        this.gpsState = gpsState;
    }

    @Override
    public String toString() {
        return "NetworkInfo{" +
                "wifiState=" + wifiState +
                ", mobileDataState=" + mobileDataState +
                ", bluetoothState=" + bluetoothState +
                ", gpsState=" + gpsState +
                '}';
    }
}
