package com.example.jeremy.controller.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Switch;

/**
 * Created by Jeremy on 18/03/2018.
 */

public class DisplayController {
    private Context context;
    public static final String ACCESSIBILITY_DISPLAY_DALTONIZER = "accessibility_display_daltonizer";
    public static final String ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";

    public DisplayController(Context context) {
        this.context = context;
    }

    private void init() {
        //setup display monitor
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

    public static void toggleMonochrome(int value, ContentResolver contentResolver) {
        Settings.Secure.putInt(contentResolver, ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, value);
        if (value == 0) {
            Settings.Secure.putInt(contentResolver, ACCESSIBILITY_DISPLAY_DALTONIZER, -1);
        } else if (value == 1) {
            Settings.Secure.putInt(contentResolver, ACCESSIBILITY_DISPLAY_DALTONIZER, 0);
        }
    }

    public void showRootWorkaroundInstructions(final Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.context);
        builder.setMessage("Since your phone is not rooted, you must manually grant the permission " +
                "'android.permission.WRITE_SECURE_SETTINGS' by going to adb and inputting the following command" +
                " adb -d shell pm grant com.example.jeremy.controller android.permission.WRITE_SECURE_SETTINGS")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Copy the command", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setClipboard(context, "adb -d shell pm grant com.example.jeremy.monochrometoggler android.permission.WRITE_SECURE_SETTINGS");
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * @param context
     * @param text    - Copy the text passed in the parameters onto the clipboard
     */
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
