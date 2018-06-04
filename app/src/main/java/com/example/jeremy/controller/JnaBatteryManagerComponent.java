package com.example.jeremy.controller;

import com.example.jeremy.controller.modules.AppModule;
import com.example.jeremy.controller.modules.PersistencyModule;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.service.ReceiveTransitionsIntentService;
import com.example.jeremy.controller.service.TransitionService;
import com.example.jeremy.controller.service.TriggerManager;
import com.example.jeremy.controller.view.AddEditGeofenceActivity;
import com.example.jeremy.controller.view.BaseActivity;
import com.example.jeremy.controller.view.GeofencesActivity;
import com.example.jeremy.controller.view.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PersistencyModule.class})
public interface JnaBatteryManagerComponent {
    void inject(ReceiveTransitionsIntentService object);

    void inject(GeofencesActivity object);

    void inject(SettingsActivity object);

    void inject(BaseActivity object);

    void inject(AddEditGeofenceActivity object);

    void inject(TriggerManager object);

    void inject(TransitionService object);

    void inject(NotificationManager object);

    void inject(HomeActivity object);
}
