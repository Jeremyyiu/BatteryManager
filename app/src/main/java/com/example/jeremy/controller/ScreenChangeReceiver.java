package com.example.jeremy.controller;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

public class ScreenChangeReceiver extends Service {
    private static BroadcastReceiver mScreenOnOffBroadcastReceiver;

    final static String SCREEN_ON_ACTION = "SCREEN_ON";
    final static String SCREEN_OFF_ACTION = "SCREEN_OFF";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (mScreenOnOffBroadcastReceiver == null) {
            IntentFilter screenOnOffFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            screenOnOffFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenOnOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
            mScreenOnOffBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                        sendBroadcast(new Intent(context, Receiver.class).setAction(SCREEN_OFF_ACTION));
                    } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction()) &&
                            !((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE))
                                    .inKeyguardRestrictedInputMode()) {
                        // SCREEN_ON is only sent if there is no lockscreen active! Otherwise the Receiver will get USER_PRESENT
                        sendBroadcast(new Intent(context, Receiver.class).setAction(SCREEN_ON_ACTION));
                    }
                }

            };
            registerReceiver(mScreenOnOffBroadcastReceiver, screenOnOffFilter);
        }
        if (intent == null) { //service restarted
            // is display already off?
            if ((Build.VERSION.SDK_INT < 20 &&
                    !((PowerManager) getSystemService(POWER_SERVICE)).isScreenOn()) ||
                    (Build.VERSION.SDK_INT >= 20) && !APILevel20Wrapper.isScreenOn(this)) {
                sendBroadcast(new Intent(this, Receiver.class).setAction(SCREEN_OFF_ACTION));
            }
        }
        return Service.START_STICKY;
        /* If the phone runs out of memory and kills the service before it finishes executing,
         *  START_STICKY will tell the OS to recreate the service after it has enough memory and
         *  call onStartCommand() again with a null intent.
         * */
    }

    /**
     * Unregisters the receiver
     */
    @Override
    public void onDestroy() {
        if (mScreenOnOffBroadcastReceiver != null) {
            unregisterReceiver(mScreenOnOffBroadcastReceiver);
            mScreenOnOffBroadcastReceiver = null;
        }
        super.onDestroy();
    }
}
