package com.example.jeremy.controller;

import android.app.Application;
import android.content.Context;

import com.example.jeremy.controller.modules.AppModule;
import com.example.jeremy.controller.modules.PersistencyModule;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.service.ReceiveTransitionsIntentService;
import com.example.jeremy.controller.service.TransitionService;
import com.example.jeremy.controller.service.TriggerManager;

public class JnaBatteryManagerApplication extends Application {
    private static JnaBatteryManagerApplication mInstance;
    private JnaBatteryManagerComponent mComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mComponent = DaggerJnaBatteryManagerComponent.builder()
                .appModule(new AppModule(this))
                .persistencyModule(new PersistencyModule(this))
                .build();

        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public JnaBatteryManagerComponent getComponent() {
        return mComponent;
    }

    public static JnaBatteryManagerApplication getApplication() {
        return mInstance;
    }

    public void inject(ReceiveTransitionsIntentService object) {
        mComponent.inject(object);
    }

    public void inject(TriggerManager object) {
        mComponent.inject(object);
    }

    public void inject(TransitionService object) {
        mComponent.inject(object);
    }

    public void inject(NotificationManager object) {
        mComponent.inject(object);
    }

    public void inject(BatteryLevelReceiver object) { mComponent.inject(object); }


}
