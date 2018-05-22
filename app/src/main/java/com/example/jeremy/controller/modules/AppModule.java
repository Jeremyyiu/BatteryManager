package com.example.jeremy.controller.modules;

import android.content.Context;

import com.example.jeremy.controller.JnaBatteryManagerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private JnaBatteryManagerApplication mApp;

    public AppModule(JnaBatteryManagerApplication application) { mApp = application; }

    @Provides
    Context getApplicationContext() {
        return mApp;
    }

}
