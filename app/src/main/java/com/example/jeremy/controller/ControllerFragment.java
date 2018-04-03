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
import android.widget.TextView;

import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.DisplayController;
import com.example.jeremy.controller.controller.GPSController;
import com.example.jeremy.controller.controller.NetworkController;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


/**
 * Fragment class for controller
 */
@RuntimePermissions
public class ControllerFragment extends Fragment {

    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final int CONTACTS_REQUEST = INITIAL_REQUEST + 2;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;

    GPSController gpsController = null;
    NetworkController networkController = null;
    BluetoothController bluetoothController = null;
    DisplayController displayController = null;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mContext = getActivity();
        gpsController = new GPSController(mContext);
        networkController = new NetworkController(mContext);
        bluetoothController = new BluetoothController();
        displayController = new DisplayController(mContext);

        //initControllerItems();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controller, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControllerItems();
        initControllerItemTextClick();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void initControllerItems() {

        if (networkController.isWifiConnected()) {
            SwitchCompat wifiSwitch = getActivity().findViewById(R.id.wifiSwitch);
            wifiSwitch.setChecked(true);
        }

        if (networkController.isMobileNetworkConnected()) {
            SwitchCompat mobileDataSwitch = getActivity().findViewById(R.id.mobileDataSwitch);
            mobileDataSwitch.setChecked(true);
        }

        if (bluetoothController.isBluetoothOn()) {
            SwitchCompat bluetoothSwitch = getActivity().findViewById(R.id.bluetoothSwitch);
            bluetoothSwitch.setChecked(true);
        }

        if (networkController.isAirplaneModeOn(mContext)) {
            SwitchCompat flightModeSwitch = getActivity().findViewById(R.id.flightModeSwitch);
            flightModeSwitch.setChecked(true);
        }


        //hotspot

        if (gpsController.isGPSOn()) {
            SwitchCompat gpsSwitch = getActivity().findViewById(R.id.gpsSwitch);
            gpsSwitch.setChecked(true);
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void initControllerItemTextClick() {
        final TextView wifiText = (TextView) getActivity().findViewById(R.id.wifiText);
        wifiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(wifi);
            }
        });

        final TextView mobileDataText = (TextView) getActivity().findViewById(R.id.mobile_data_text);
        mobileDataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mobileData = new Intent();
                mobileData.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(mobileData);
            }
        });

        final TextView gpsText = (TextView) getActivity().findViewById(R.id.gpsText);
        gpsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gprs = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(gprs);
            }
        });


        final TextView bluetoothText = (TextView) getActivity().findViewById(R.id.bluetoothText);
        bluetoothText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetooth = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(bluetooth);
            }
        });

        final TextView flightModeText = (TextView) getActivity().findViewById(R.id.flightModeText);
        flightModeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plane = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                startActivity(plane);
            }
        });

        final TextView hotspotText = (TextView) getActivity().findViewById(R.id.hotspotText);
        hotspotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spot = new Intent();
                spot.setClassName("com.android.settings", "com.android.settings.TetherSettings");
                mContext.startActivity(spot);
            }
        });

        final TextView dataUsageText = (TextView) getActivity().findViewById(R.id.dataUsageText);
        dataUsageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dataUsage = new Intent();
                dataUsage.setComponent(new ComponentName(
                        "com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"
                ));
                startActivity(dataUsage);
            }
        });
    }

/**
 * @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * // NOTE: delegate the permission handling to generated method
 * ControllerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
 * }
 **/


}
