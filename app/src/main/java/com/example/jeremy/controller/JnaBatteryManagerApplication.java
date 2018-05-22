package com.example.jeremy.controller;

import android.app.Application;
import android.content.Context;

import com.example.jeremy.controller.modules.AppModule;
import com.example.jeremy.controller.modules.PersistencyModule;

public class JnaBatteryManagerApplication extends Application {
    private static JnaBatteryManagerApplication mInstance;
    private JnaBatteryManagerComponent mComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        /**
        mComponent = DaggerJnaBatteryManagerComponent.builder()
                .appModule(new AppModule(this)
                        .persistencyModule(new PersistencyModule(this))
                .build();
         **/
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

}
