package com.example.jeremy.controller.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.SeekBar;

import com.example.jeremy.controller.R;

/**
 * Created by Jeremy on 18/03/2018.
 */

public class DisplayController {
    private Context context;
    public static final String ACCESSIBILITY_DISPLAY_DALTONIZER = "accessibility_display_daltonizer";
    public static final String ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    private Activity activity;

    public DisplayController(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    private void init() {
        //setup display monitor
    }

    public void initDisplayItems() {
        SeekBar brightnessSlider = activity.findViewById(R.id.brightnessSlider);
        if (checkIfAutoBrightness()) {
            SwitchCompat autoBrightnessSwitch = activity.findViewById(R.id.autoBrightnessSwitch);
            autoBrightnessSwitch.setChecked(true);
            brightnessSlider.setProgress(0);
        } else {
            int currentBrightness = getCurrentBrightness();
            brightnessSlider.setProgress(currentBrightness);
        }
    }

    /**
     * Stores initial brightness values
     */
    private void setupInitialBrightness() {
        int initialBrightness = getCurrentBrightness();
        boolean isInitialAutoBrightness = checkIfAutoBrightness();
    }

    public boolean checkIfAutoBrightness() {
        if (Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0) == 1) {
            return true;
        }
        return false;
    }

    /**
     * Get current system brightness value as an integer
     */
    public int getCurrentBrightness() {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
    }

    public void setCurrentBrightness(int screenBrightnessValue) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightnessValue);
    }


    /**
     * Turn automatic brightness mode on - set manual mode off
     */
    public void setBrightnessToAuto() {
        //int mode = displayMonitor.getDisplayInfo().getBrightnessMode();
        //if not auto already set manual
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * Turn automatic brightness mode off - set manual mode on
     */
    public void setBrightnessToManual() {
        //int mode = displayMonitor.getDisplayInfo().getBrightnessMode();
        //if not manual already set auto
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }
}
