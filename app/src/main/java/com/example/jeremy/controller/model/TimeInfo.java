package com.example.jeremy.controller.model;

/**
 * Created by liangze on 28/2/18.
 */

public class TimeInfo {

    private int dayOfWeek;
    private int timeInMins;

    public TimeInfo() {
    }

    public TimeInfo(int dayOfWeek, int timeInMins) {
        this.dayOfWeek = dayOfWeek;
        this.timeInMins = timeInMins;
    }

    public int getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeInMins() {
        return timeInMins;
    }

    public void setTimeInMins(int timeInMins) {
        this.timeInMins = timeInMins;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "dayOfWeek=" + dayOfWeek +
                ", timeInMins=" + timeInMins +
                '}';
    }
}
