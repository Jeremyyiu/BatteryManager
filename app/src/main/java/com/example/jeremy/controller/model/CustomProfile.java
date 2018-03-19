package com.example.jeremy.controller.model;

import java.io.Serializable;

/**
 * Created by liangze on 2/3/18.
 */

public class CustomProfile implements Serializable {
    private ContextInfo contextInfo;

    public class Settings {
        public boolean wifiOn;
        public boolean mobileDataOn;
        public boolean bluetoothOn;
        public boolean brightnessAuto;
        // 0 dark, 1 dim, 2 medium, 3 full
        public int brightnessLevel;
    }
}
