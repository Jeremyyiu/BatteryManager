package com.example.jeremy.controller.model;

/**
 * Created by liangze on 28/2/18.
 */

public class ContextInfo {

    private TimeInfo time;
    private LocationInfo location;
    private ActivityInfo activity;
    private BatteryInfo battery;
    private DisplayInfo display;
    private NetworkInfo connectivity;

    public ContextInfo() {
    }

    public TimeInfo getTime() {
        return time;
    }

    public void setTime(TimeInfo time) {
        this.time = time;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public ActivityInfo getActivity() {
        return activity;
    }

    public void setActivity(ActivityInfo activity) {
        this.activity = activity;
    }

    public BatteryInfo getBattery() {
        return battery;
    }

    public void setBattery(BatteryInfo battery) {
        this.battery = battery;
    }

    public DisplayInfo getDisplay() {
        return display;
    }

    public void setDisplay(DisplayInfo display) {
        this.display = display;
    }

    public NetworkInfo getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(NetworkInfo connectivity) {
        this.connectivity = connectivity;
    }

    @Override
    public String toString() {
        return "ContextInfo{" +
                "time=" + time +
                ", location=" + location +
                ", activity=" + activity +
                ", battery=" + battery +
                ", display=" + display +
                ", connectivity=" + connectivity +
                '}';
    }
}
