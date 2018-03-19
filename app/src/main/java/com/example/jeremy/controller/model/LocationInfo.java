package com.example.jeremy.controller.model;

/**
 * Created by liangze on 28/2/18.
 */

public class LocationInfo {

    // 0 home, 1 others
    private int placeType;
    // longitude and latitude might be none
    private double longitude;
    private double latitude;
    private double accuracy;

    public LocationInfo() {
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "placeType=" + placeType +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", accuracy=" + accuracy +
                '}';
    }
}
