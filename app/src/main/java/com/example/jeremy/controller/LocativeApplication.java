package com.example.jeremy.controller;

import android.app.Activity;
import android.os.Bundle;

import com.example.jeremy.controller.modules.AppModule;
import com.example.jeremy.controller.modules.PersistencyModule;
import com.example.jeremy.controller.notification.NotificationManager;
import com.example.jeremy.controller.service.ReceiveTransitionsIntentService;
import com.example.jeremy.controller.service.TransitionService;
import com.example.jeremy.controller.service.TriggerManager;

public class LocativeApplication extends Activity {

    private static LocativeApplication mInstance;
    private LocativeComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_home);

        mInstance = this;

        mComponent = DaggerLocativeComponent.builder()
                .appModule(new AppModule(this))
                .persistencyModule(new PersistencyModule(this))
                .build();
        //mComponent.inject(this);

        super.onCreate(savedInstanceState);
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

    public LocativeComponent getComponent() {
        return mComponent;
    }

    public static LocativeApplication getActivity() {
        return mInstance;
    }
}