package com.example.jeremy.controller.modules;

import android.content.Context;

import com.example.jeremy.controller.JnaBatteryManagerApplication;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.service.TriggerManager;
import com.example.jeremy.controller.utils.ResourceUtils;
import com.squareup.otto.Bus;

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
