package com.example.jeremy.controller.model;

/**
 * Created by liangze on 1/3/18.
 */

public class DisplayInfo {

    private int brightness;
    private int brightnessMode;
    private int screenState;

    public static final int BRIGHTNESS_MODE_MANUAL = 1;
    public static final int BRIGHTNESS_MODE_AUTO = 0;
    public static final int SCREEN_ON = 1;
    public static final int SCREEN_OFF = 0;

    public DisplayInfo() {

    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getBrightnessMode() {
        return brightnessMode;
    }

    public void setBrightnessMode(int brightnessMode) {
        this.brightnessMode = brightnessMode;
    }

    public int getScreenState() {
        return screenState;
    }

    public void setScreenState(int screenState) {
        this.screenState = screenState;
    }

    @Override
    public String toString() {
        return "DisplayInfo{" +
                "brightness=" + brightness +
                ", brightnessMode=" + brightnessMode +
                ", screenState=" + screenState +
                '}';
    }
}
