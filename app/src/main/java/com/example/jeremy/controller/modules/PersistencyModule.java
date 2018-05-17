package com.example.jeremy.controller.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import com.example.jeremy.controller.LocativeApplication;
import com.example.jeremy.controller.persistent.Storage;

@Module
public class PersistencyModule {

    private LocativeApplication mApp;

    public PersistencyModule(LocativeApplication app) {
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
