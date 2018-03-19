package com.example.jeremy.controller.controller;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Jeremy on 18/03/2018.
 */

public class DisplayController {
    private Context context;

    public DisplayController(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        //setup display monitor
    }

    /**
     * Stores initial brightness values
     */
    private void setupInitialBrightness() {
        int initialBrightness = getCurrentBrightness();
        boolean isInitialAutoBrightness = checkIfAutoBrightness() == 1;
    }

    private int checkIfAutoBrightness() {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
    }

    /**
     * Get current system brightness value as an integer
     */
    private int getCurrentBrightness() {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
    }

    private void setCurrentBrightness(int screenBrightnessValue) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightnessValue);
    }


    /**
     * Turn automatic brightness mode on - set manual mode off
     */
    private void setBrightnessToAuto() {
        //int mode = displayMonitor.getDisplayInfo().getBrightnessMode();
        //if not auto already set manual
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * Turn automatic brightness mode off - set manual mode on
     */
    private void setBrightnessToManual() {
        //int mode = displayMonitor.getDisplayInfo().getBrightnessMode();
        //if not manual already set auto
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }
}
