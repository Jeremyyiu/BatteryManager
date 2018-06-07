package com.example.jeremy.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;

import com.example.jeremy.controller.utils.Constants;

import static com.example.jeremy.controller.utils.Constants.NORMAL_MODE;
import static com.example.jeremy.controller.utils.Constants.SILENT_MODE;
import static com.example.jeremy.controller.utils.Constants.VIBRATE_MODE;

public class AudioController {

    private Context mContext;
    private AudioManager audioManager;

    private static AudioController mInstance;

    protected AudioController(Context context) {
        this.mContext = context;
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    public static AudioController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AudioController(context);
        }
        return mInstance;
    }

    /**
     * Sets phone to silent without vibrate
     */
    public void mutePhone(int modeSetting) {
        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            mContext.startActivity(intent);
        } else {
            switch (modeSetting) {
                case Constants.NORMAL_MODE:
                    setRingerToNormal();
                    break;
                case Constants.VIBRATE_MODE:
                    setRingerToVibrate();
                    break;
                case SILENT_MODE:
                    setRingerToSilent();
                    break;
                default:
                    break;
            }
        }
        //https://stackoverflow.com/questions/11599406/using-notificationmanager-into-an-android-service
    }

    public void setRingerToNormal() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public void setRingerToVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public void setRingerToSilent() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public int getRingerMode() {
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                return SILENT_MODE;
            case AudioManager.RINGER_MODE_VIBRATE:
                return VIBRATE_MODE;
            case AudioManager.RINGER_MODE_NORMAL:
                return NORMAL_MODE;
            default:
                return 0;
        }
    }
}