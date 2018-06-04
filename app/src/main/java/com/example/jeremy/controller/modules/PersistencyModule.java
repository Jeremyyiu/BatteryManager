package com.example.jeremy.controller.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jeremy.controller.JnaBatteryManagerApplication;
import com.example.jeremy.controller.persistent.Storage;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistencyModule {

    private JnaBatteryManagerApplication mApp;

    public PersistencyModule(JnaBatteryManagerApplication app) {
        mApp = app;
    }

    @SuppressWarnings("unused")
    @Provides
    Storage provideStorage(Context context) {
        return new Storage(context);
    }

    @SuppressWarnings("unused")
    @Provides
    SharedPreferences providePreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
