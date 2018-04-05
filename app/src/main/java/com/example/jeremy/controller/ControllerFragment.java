package com.example.jeremy.controller;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.DisplayController;
import com.example.jeremy.controller.controller.GPSController;
import com.example.jeremy.controller.controller.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


/**
 * Fragment class for controller
 */
@RuntimePermissions
public class ControllerFragment extends Fragment {
    //Network
    @BindView(R.id.wifiSwitch)
    SwitchCompat wifiSwitch;
    @BindView(R.id.wifiText)
    TextView wifiText;

    @BindView(R.id.mobileDataSwitch)
    SwitchCompat mobileDataSwitch;
    @BindView(R.id.mobile_data_text)
    TextView mobileDataText;

    @BindView(R.id.flightModeSwitch)
    SwitchCompat flightModeSwitch;
    @BindView(R.id.flightModeText)
    TextView flightModeText;

    @BindView(R.id.hotspotSwitch)
    SwitchCompat hotspotSwitch;
    @BindView(R.id.hotspotText)
    TextView hotspotText;

    @BindView(R.id.dataUsageText)
    TextView dataUsageText;

    //GPS
    @BindView(R.id.gpsSwitch)
    SwitchCompat gpsSwitch;
    @BindView(R.id.gpsText)
    TextView gpsText;

    //Bluetooth
    @BindView(R.id.bluetoothSwitch)
    SwitchCompat bluetoothSwitch;
    @BindView(R.id.bluetoothText)
    TextView bluetoothText;

    //Display
    @BindView(R.id.brightnessSlider)
    SeekBar brightnessSlider;
    @BindView(R.id.brightnessText)
    TextView brightnessText;
    @BindView(R.id.autoBrightnessSwitch)
    SwitchCompat autoBrightnessSwitch;

    GPSController gpsController = null;
    NetworkController networkController = null;
    BluetoothController bluetoothController = null;
    DisplayController displayController = null;

    private Context mContext;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_controller, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        gpsController = new GPSController(mContext);
        networkController = new NetworkController(mContext);
        bluetoothController = new BluetoothController();
        displayController = new DisplayController(mContext);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControllerItems();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void initControllerItems() {
        initNetworkItems();
        initBluetoothItems();
        initGpsItems();
        initDisplayItems();
    }

    public void initNetworkItems() {
        initNetworkSwitch();
    }

    private void initNetworkSwitch() {
        wifiSwitch.setChecked(networkController.isWifiConnected());
        mobileDataSwitch.setChecked(networkController.isMobileNetworkConnected());
        flightModeSwitch.setChecked(networkController.isAirplaneModeOn(mContext));
        //TODO: hotspot
    }

    @OnClick ({R.id.wifiText, R.id.wifi_icon})
    public void wifiSettings(View view) {
        Intent wifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
        getActivity().startActivity(wifi);
    }

    @OnClick({R.id.flightModeText, R.id.flightMode_icon})
    public void flightModeSettings(View view) {
        Intent plane = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        getActivity().startActivity(plane);
    }

    @OnClick({R.id.hotspotText, R.id.hotspot_icon})
    public void hotspotSettings(View view) {
        Intent spot = new Intent();
        spot.setClassName("com.android.settings", "com.android.settings.TetherSettings");
        getActivity().startActivity(spot);
    }

    @OnClick({R.id.dataUsageText, R.id.dataUsage_Icon, R.id.mobile_data_text, R.id.mobile_data_icon})
    public void dataUsageSettings(View view) {
        Intent dataUsage = new Intent();
        dataUsage.setComponent(new ComponentName(
                "com.android.settings",
                "com.android.settings.Settings$DataUsageSummaryActivity"
        ));
        getActivity().startActivity(dataUsage);

    }

    private void initBluetoothItems() {
        initBluetoothSwitch();
    }

    public void initBluetoothSwitch() {
        if (bluetoothController.isBluetoothOn()) {
            bluetoothSwitch.setChecked(true);
        }
    }

    @OnClick({R.id.bluetoothText, R.id.bluetooth_icon})
    public void bluetoothSettings(View view) {
        Intent bluetooth = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        getActivity().startActivity(bluetooth);
    }

    public void initDisplayItems() {
        if (displayController.checkIfAutoBrightness()) {
            autoBrightnessSwitch.setChecked(true);
            brightnessSlider.setProgress(0);
        } else {
            int currentBrightness = displayController.getCurrentBrightness();
            brightnessSlider.setProgress(currentBrightness);
        }
    }

    public void initGpsItems() {
        initGpsSwitch();
    }

    private void initGpsSwitch() {
        boolean gpsStatus = gpsController.isGPSOn();
        gpsSwitch.setChecked(gpsStatus);
    }

    @OnClick({R.id.gpsText, R.id.gps_icon})
    public void dataRoamingSettings(View view) {
        Intent gprs = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        getActivity().startActivity(gprs);
    }

/**
 * @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * // NOTE: delegate the permission handling to generated method
 * ControllerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
 * }
 **/


}
