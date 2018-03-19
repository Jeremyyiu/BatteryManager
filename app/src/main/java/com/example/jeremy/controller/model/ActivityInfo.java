package com.example.jeremy.controller.model;

/**
 * Created by liangze on 1/3/18.
 */

public class ActivityInfo {

    private int activityType;
    private double confidence;

    public ActivityInfo() {
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ActivityInfo{" +
                "activityType=" + activityType +
                ", confidence=" + confidence +
                '}';
    }
}
