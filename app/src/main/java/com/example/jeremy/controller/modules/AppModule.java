package com.example.jeremy.controller.modules;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.example.jeremy.controller.LocativeApplication;
import com.example.jeremy.controller.service.TriggerManager;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.utils.ResourceUtils;

@Module
public class AppModule {

    private final LocativeApplication mApp;

    public AppModule(LocativeApplication application) {
        mApp = application;
    }


    @Provides
    Context getApplicationContext() {
        return mApp;
    }

    @Provides
    NotificationManager provideNotificationManager(Context context) {
        return new NotificationManager(context);
    }

    @Provides
    TriggerManager provideTriggerManager() {
        return new TriggerManager();
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    ResourceUtils provideResourceUtils() {
        return new ResourceUtils(mApp);
    }
}
